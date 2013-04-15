package network;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import data.Message;
import data.QueueThread;
import data.Service;
import data.UserEntry;
import data.Message.MessageType;
import data.UserEntry.Offer;
import data.UserProfile;
import data.UserProfile.UserRole;

/**
 * @author Ghennadi Procopciuc
 */
public class NetworkReceiveEvents extends QueueThread<SelectionKey, Message> {
	private static Logger	logger	= Logger.getLogger(NetworkReceiveEvents.class);

	private NetworkImpl		network;
	private NetworkDriver	driver;

	public NetworkReceiveEvents(NetworkImpl network) {
		super("NetworkReceiveEvents");

		logger.setLevel(Level.OFF);

		this.network = network;
		driver = network.getDriver();
	}

	/**
	 * Process message after receiving one.
	 * 
	 * @param key
	 *            The source of the message
	 * @param message
	 *            Message
	 */
	private void messageProcess(SelectionKey key, Message message) {
		logger.debug("Begin");
		logger.debug("Message type: " + message.getType());

		switch (message.getType()) {
		case GET_USERNAME:
			processGetUsername(key, message);
			break;
		case SEND_USERNAME:
			processSendUsername(key, message);
			break;
		case LAUNCH:
			processLaunch(key, message);
			break;
		case LAUNCH_RESPONSE:
			processLaunchResponse(key, message);
			break;
		case MAKE_OFFER:
			processMakeOffer(key, message);
			break;
		case ACCEPT:
			processAccept(key, message);
			break;
		case REFUSE:
			processRefuse(key, message);
			break;
		case TRANSFER_SIZE:
			processTransferSize(key, message);
			break;
		case TRANSFER_CHUNCK:
			processTransferChunk(key, message);
			break;
		default:
			logger.error("Unknown type of message : " + message.getType());
			break;
		}
		logger.debug("End");
	}

	private void processGetUsername(SelectionKey key, Message message) {
		logger.debug("Begin");
		NetworkDriver driver = network.getDriver();

		/* Message build */
		Message newMessage = new Message();
		newMessage.setType(MessageType.SEND_USERNAME);
		newMessage.setUsername(network.getUserProfile().getUsername());

		driver.sendData(newMessage, key);
		logger.debug("End");
	}

	private void processSendUsername(SelectionKey key, Message message) {
		logger.debug("Begin");
		network.registerConnection(message.getUsername(), (SocketChannel) key.channel());
		logger.debug("End");
	}

	/**
	 * Message payload will have an UserEntry object
	 * 
	 * @param key
	 * @param message
	 */
	private void processLaunch(SelectionKey key, Message message) {
		logger.debug("Start : " + message);

		UserProfile user = network.getUserProfile();
		if (user.getRole() == UserRole.SELLER) {
			logger.debug("SELLER Case");

			// Send new offer
			Service service = network.getService(message.getServiceName());
			if (service == null) {
				logger.fatal("End Unknown service : " + message.getServiceName());
				return;
			}

			Message newMessage = new Message();
			newMessage.setType(MessageType.LAUNCH_RESPONSE);
			newMessage.setPayload(new UserEntry(user.getUsername(), user.getFirstName() + " " + user.getLastName(),
					Offer.NO_OFFER, service.getTime(), service.getPrice()));
			newMessage.setUsername(user.getUsername());

			newMessage.setServiceName(message.getServiceName());

			UserEntry userEntry = (UserEntry) message.getPayload();
			userEntry.setPrice(service.getPrice());

			logger.debug("Send data ...");
			driver.sendData(newMessage, key);

			/* Notify mediator about changes */

			logger.debug("Userfs before " + service.getUsers());

			if (service.getUsers() == null) {
				service.setUsers(new ArrayList<UserEntry>());
			}
			service.getUsers().remove(userEntry);
			service.getUsers().add(userEntry);

			// service.addUserEntry(userEntry);

			logger.debug("Users after " + service.getUsers());
			network.changeServiceNotify(service);
		} else {
			logger.debug("[NetworkReceiveEvents: processLaunch] Buyer Case");
			// An seller make me an new offer
			// Update price from payload

			Service service = network.getService(message.getServiceName());
			if (service == null) {
				logger.fatal("End Unknown service : " + message.getServiceName());
				return;
			}

			Service newService = service.clone();
			UserEntry userEntry = service.getUser(message.getUsername());
			if (userEntry == null) {
				logger.error("User not found: " + message.getUsername());
				logger.debug("Check if webService can help us ...");
				// TODO : userEntry =
				// webService.getUserProfile(userEntry.getUsername)
				// Check if new userEntry is not null
				// Add new userEntry to service
			}

			UserEntry serviceUser = newService.getUser(userEntry.getUsername());
			if (serviceUser == null) {
				newService.addUserEntry(userEntry);
			} else {
				UserEntry messageUserEntry = (UserEntry) message.getPayload();
				serviceUser.setPrice(messageUserEntry.getPrice());
				serviceUser.setTime(messageUserEntry.getTime());
				serviceUser.setOffer(Offer.OFFER_MADE);
			}

			// network.changeServiceNotify(newService);
			// TODO : Send response to launch
			// Message newMessage = new Message();
		}

		logger.debug("End");
	}

	private void processLaunchResponse(SelectionKey key, Message message) {
		logger.debug("Begin");
		logger.debug("Message : " + message);

		Service service = network.getService(message.getServiceName());
		if (service == null) {
			logger.fatal("Unknown service : " + message.getServiceName());
			return;
		}

		Service newService = service.clone();
		UserEntry userEntry = null;

		/* If no users register for this service */
		if (service.getUsers() != null) {
			userEntry = service.getUser(message.getUsername());
		} else {
			newService.setUsers(new ArrayList<UserEntry>());
		}

		userEntry = (UserEntry) message.getPayload();

		// if (userEntry == null) {
		// logger.fatal("User not found : " + userEntry.getUsername());
		// logger.debug("Check if webService can help us ...");
		// // TODO : userEntry =
		// // webService.getUserProfile(userEntry.getUsername)
		// // Check if new userEntry is not null
		// // Add new userEntry to service
		//
		// userEntry = (UserEntry) message.getPayload();
		// }

		logger.debug("Before users : " + newService.getUsers());
		newService.getUsers().remove(userEntry);
		newService.getUsers().add(userEntry);
		logger.debug("After users : " + newService.getUsers());

		// UserEntry serviceUser = newService.getUser(userEntry.getUsername());
		// if (serviceUser == null) {
		// newService.addUserEntry(userEntry);
		// } else {
		// UserEntry messageUserEntry = (UserEntry) message.getPayload();
		// serviceUser.setPrice(messageUserEntry.getPrice());
		// serviceUser.setTime(messageUserEntry.getTime());
		// serviceUser.setOffer(Offer.OFFER_MADE);
		// }

		logger.debug("New Service : " + newService);
		network.changeServiceNotify(newService);

		logger.debug("End");
	}

	/**
	 * Only a buyer can receive this type of message. Payload contains an
	 * <code>UserEntry</code> object
	 * 
	 * @param key
	 * @param message
	 */
	private void processMakeOffer(SelectionKey key, Message message) {
		logger.debug("Begin");

		UserProfile userProfile = network.getUserProfile();
		logger.debug("Message: " + message);

		if (userProfile.getRole() == UserRole.SELLER) {
			logger.fatal("Only a buyer can receive this type of message");
			return;
		}

		Service service = network.getService(message.getServiceName());
		if (service == null) {
			logger.fatal("Unknown service: " + message.getServiceName());
			return;
		}

		logger.debug("Service: " + service);
		logger.debug("Username: " + message.getPayload());

		if (service.getUsers() == null) {
			service.setUsers(new ArrayList<UserEntry>());
		}

		service.getUsers().remove((UserEntry) message.getPayload());
		service.addUserEntry((UserEntry) message.getPayload());

		logger.debug("The new service : " + service);

		network.changeServiceNotify(service);
		logger.debug("End");
	}

	private void processAccept(SelectionKey key, Message message) {
		logger.debug("Begin");
		UserProfile userProfile = network.getUserProfile();

		logger.debug("Message : " + message);
		if (userProfile.getRole() == UserRole.BUYER) {
			logger.fatal("Only a seller can receive this type of message");
			return;
		}

		/* Get actual service */
		Service service = network.getService(message.getServiceName());
		if (service == null) {
			logger.fatal("Unknown service : " + message.getServiceName());
			return;
		}

		logger.debug("Service : " + service);
		logger.debug("Username : " + message.getPayload());
		UserEntry user = service.getUser((String) message.getPayload());
		if (user == null) {
			logger.fatal("User " + message.getUsername() + " not found");
			return;
		}

		user.setOffer(Offer.OFFER_ACCEPTED);
		logger.debug("New service : " + service);

		network.changeServiceNotify(service);
		logger.debug("End");
	}

	private void processRefuse(SelectionKey key, Message message) {
		logger.debug("Begin");
		UserProfile userProfile = network.getUserProfile();

		logger.debug("Message : " + message);

		if (userProfile.getRole() == UserRole.BUYER) {
			logger.fatal("Only a seller can receive this type of message");
			return;
		}

		/* Get actual service */
		Service service = network.getService(message.getServiceName());
		if (service == null) {
			logger.fatal("Unknown service : " + message.getServiceName());
			return;
		}

		logger.debug("Service : " + service);
		logger.debug("Username : " + message.getPayload());
		UserEntry user = service.getUser((String) message.getPayload());
		if (user == null) {
			logger.fatal("User " + message.getUsername() + " not found");
			return;
		}

		/* Remove user from GUI */
		service.getUsers().remove(user);
		if (service.getUsers().isEmpty()) {
			service.setUsers(null);
		}
		logger.debug("New service : " + service);

		network.changeServiceNotify(service);
		logger.debug("End");
	}

	private void processTransferSize(SelectionKey key, Message message) {
		// TODO Auto-generated method stub
		logger.debug("Begin");
		logger.debug("End");
	}

	private void processTransferChunk(SelectionKey key, Message message) {
		// TODO Auto-generated method stub
		logger.debug("Begin");
		logger.debug("End");
	}

	protected synchronized void process() {
		logger.debug("Begin");

		// if (!haveToProcess()) {
		// return;
		// }

		// for (Entry<SelectionKey, ArrayList<Message>> entry : new
		// ArrayList<>(queue.entrySet())) {
		// for (Message message : entry.getValue()) {
		// messageProcess(entry.getKey(), message);
		// }
		// }

		Map.Entry<SelectionKey, Message> job = getJob();
		if (job == null) {
			return;
		}

		messageProcess(job.getKey(), job.getValue());
		logger.debug("End");
	}
}