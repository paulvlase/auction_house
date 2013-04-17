package network;

import java.nio.channels.SelectionKey;
import java.nio.channels.ShutdownChannelGroupException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeSet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import data.Message;
import data.QueueThread;
import data.Service;
import data.Service.Status;
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

		// logger.setLevel(Level.OFF);

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
		case REMOVE:
			processRemove(key, message);
			break;
		case TRANSFER_STARTED:
			processTransferStarted(key, message);
			break;
		case TRANSFER_CHUNCK:
			processTransferChunk(key, message);
			break;
		case TRANSFER_PROGRESS:
			processTransferProgress(key, message);
			break;
		case LOGOUT:
			processLogout(key, message);
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
//		if (user.getRole() == UserRole.SELLER) {
//			logger.debug("SELLER Case");

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
//		} else {
//			logger.debug("[NetworkReceiveEvents: processLaunch] Buyer Case");
//			// An seller make me an new offer
//			// Update price from payload
//
//			Service service = network.getService(message.getServiceName());
//			if (service == null) {
//				logger.fatal("End Unknown service : " + message.getServiceName());
//				return;
//			}
//
//			Service newService = service.clone();
//			UserEntry userEntry = service.getUser(message.getUsername());
//			UserEntry userEntry = (UserEntry) message.getPayload();
//			if (userEntry == null) {
//				logger.error("User not found: " + message.getUsername());
//				logger.debug("Check if webService can help us ...");
//				// TODO : userEntry =
//				// webService.getUserProfile(userEntry.getUsername)
//				// Check if new userEntry is not null
//				// Add new userEntry to service
//				newService.addUserEntry(userEntry);
//			}
//
//			logger.debug("[NetworkReceiveEvents: processLaunch] NewService : " + newService);
//			UserEntry serviceUser = newService.getUser(userEntry.getUsername());
//			if (serviceUser == null) {
//				newService.addUserEntry(userEntry);
//			} else {
//				UserEntry messageUserEntry = (UserEntry) message.getPayload();
//				serviceUser.setPrice(messageUserEntry.getPrice());
//				serviceUser.setTime(messageUserEntry.getTime());
//				serviceUser.setOffer(Offer.NO_OFFER);
//			}
//
//			// TODO : Send response to launch
//			// Message newMessage = new Message();
//			network.changeServiceNotify(newService);
//		}

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

		user.setOffer(Offer.TRANSFER_STARTED);
		logger.debug("New service : " + service);

		network.changeServiceNotify(service);

		/* Make sure that client know about our intentions ... */
		{
			Message startTransfer = new Message();
			startTransfer.setDestination((String) message.getPayload());
			startTransfer.setSource(userProfile.getUsername());
			startTransfer.setServiceName(message.getServiceName());

			/* Set file size as price in KB */
			startTransfer.setPayload(service.getPrice() * 1024);
			startTransfer.setType(MessageType.TRANSFER_STARTED);
			network.getSendEvents().enqueue((SocketChannel) key.channel(), startTransfer);
		}

		/* Send file over network */
		{
			int chunkSize = (int) (service.getPrice() * 1024 / 100);
			Random random = new Random();

			for (int i = 1; i <= 101; i++) {
				Message transferChunk = new Message();
				transferChunk.setServiceName(message.getServiceName());
				transferChunk.setDestination((String) message.getPayload());
				transferChunk.setSource(userProfile.getUsername());
				transferChunk.setType(MessageType.TRANSFER_CHUNCK);
				transferChunk.setProgress(i);

				byte[] chunk = new byte[chunkSize];
				random.nextBytes(chunk);
				transferChunk.setPayload(chunk);
				network.getSendEvents().enqueue((SocketChannel) key.channel(), transferChunk);
			}
		}

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
		// service.getUsers().remove(user);
		// if (service.getUsers().isEmpty()) {
		// service.setUsers(null);
		// }
		user.setOffer(Offer.OFFER_REFUSED);
		logger.debug("New service : " + service);

		network.changeServiceNotify(service);
		logger.debug("End");
	}

	private void processRemove(SelectionKey key, Message message) {
		logger.debug("Begin");
		UserProfile userProfile = network.getUserProfile();

		logger.debug("Message : " + message);

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

	private void processTransferStarted(SelectionKey key, Message message) {
		logger.debug("Begin");
		UserProfile userProfile = network.getUserProfile();

		logger.debug("Message : " + message);

		/* Get actual service */
		Service service = network.getService(message.getServiceName());
		if (service == null) {
			logger.fatal("Unknown service : " + message.getServiceName());
			return;
		}

		logger.debug("Service : " + service);
		logger.debug("Username : " + message.getPayload());
		UserEntry user = service.getUser(message.getSource());
		if (user == null) {
			logger.fatal("User " + message.getUsername() + " not found");
			return;
		}

		user.setProgress(0);
		// user.setOffer(Offer.TRANSFER_STARTED);
		service.setStatus(Status.TRANSFER_STARTED);

		network.changeServiceNotify(service);

		logger.debug("End");
	}

	private void processTransferProgress(SelectionKey key, Message message) {
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
		logger.debug("Username : " + message.getSource());
		UserEntry user = service.getUser((String) message.getSource());
		if (user == null) {
			logger.fatal("User " + message.getSource() + " not found");
			return;
		}

		user.setOffer(Offer.TRANSFER_IN_PROGRESS);
		user.setProgress(message.getProgress());
		logger.debug("New service : " + service);

		network.changeServiceNotify(service);
		logger.debug("End");
	}

	private void processTransferChunk(SelectionKey key, Message message) {
		logger.debug("Begin");
		UserProfile userProfile = network.getUserProfile();

		logger.debug("Message : " + message);
		if (userProfile.getRole() == UserRole.SELLER) {
			logger.fatal("Only a buyer can receive this type of message");
			return;
		}

		/* Get actual service */
		Service service = network.getService(message.getServiceName());
		if (service == null) {
			logger.fatal("Unknown service : " + message.getServiceName());
			return;
		}

		logger.debug("Service : " + service);
		logger.debug("Username : " + message.getSource());
		UserEntry user = service.getUser((String) message.getSource());
		if (user == null) {
			logger.fatal("User " + message.getSource() + " not found");
			return;
		}

		// TODO : Progress = -1

		if (message.getProgress() > 100) {
			service.setStatus(Status.TRANSFER_COMPLETE);
		} else {
			service.setStatus(Status.TRANSFER_IN_PROGRESS);
		}
		service.setProgress(message.getProgress());

		/* Send progress notification to source */
		{
			Message progressMessage = new Message();
			progressMessage.setServiceName(message.getServiceName());
			progressMessage.setDestination(message.getSource());
			progressMessage.setSource(userProfile.getUsername());
			progressMessage.setType(MessageType.TRANSFER_PROGRESS);
			progressMessage.setProgress(message.getProgress());
			network.getSendEvents().enqueue((SocketChannel) key.channel(), progressMessage);
		}

		logger.debug("New service : " + service);
		network.changeServiceNotify(service);

		logger.debug("End");
	}

	private void processLogout(SelectionKey key, Message message) {
		logger.debug("begin");
		logger.debug("Remove dependencies for " + message.getSource());

		network.removeAllDependencies(message.getSource());
		// Message message = new Message();
		// message.setType(MessageType.LOGOUT);
		// message.setDestination(entry.getKey());
		// message.setSource(userProfile.getUsername());
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
			logger.debug("End (job = null)");
			return;
		}

		logger.debug("Valid job");
		messageProcess(job.getKey(), job.getValue());
		logger.debug("End");
	}
}