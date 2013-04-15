package network;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

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
	private NetworkImpl		network;
	private NetworkDriver	driver;

	public NetworkReceiveEvents(NetworkImpl network) {
		super("NetworkReceiveEvents");
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
		System.out.println("[NetworkReceiveEvents, messageProcess] Message " + message.getType());
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
			System.err.println("Unknown type of message : " + message.getType());
			break;
		}
	}

	private void processGetUsername(SelectionKey key, Message message) {
		System.out.println("[NetworkReceiveEvents: processGetUsername] Begin");
		NetworkDriver driver = network.getDriver();

		/* Message build */
		Message newMessage = new Message();
		newMessage.setType(MessageType.SEND_USERNAME);
		newMessage.setUsername(network.getUserProfile().getUsername());

		driver.sendData(newMessage, key);
	}

	private void processSendUsername(SelectionKey key, Message message) {
		System.out.println("[processSendUsername()] Begin");
		network.registerConnection(message.getUsername(), (SocketChannel) key.channel());
	}

	/**
	 * Message payload will have an UserEntry object
	 * 
	 * @param key
	 * @param message
	 */
	private void processLaunch(SelectionKey key, Message message) {
		String serviceName = message.getServiceName();

		System.out.println("[NetworkReceiveEvents: processLaunch] Start : " + message);

		UserProfile user = network.getUserProfile();
		if (user.getRole() == UserRole.SELLER) {
			System.out.println("[NetworkReceiveEvents: processLaunch] SELLER Case");

			// Send new offer
			Service service = network.getService(message.getServiceName());
			if (service == null) {
				System.err.println("[NetworkReceiveEvents: processLaunch] Unknown service : "
						+ message.getServiceName());
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

			System.out.println("[NetworkReceiveEvents: processLaunch] Send data ...");
			driver.sendData(newMessage, key);

			/* Notify mediator about changes */

			System.out.println("[NetworkReceiveEvents: processLaunch] Userfs before " + service.getUsers());

			if (service.getUsers() == null) {
				service.setUsers(new ArrayList<UserEntry>());
			}
			service.getUsers().remove(userEntry);
			service.getUsers().add(userEntry);

			// service.addUserEntry(userEntry);

			System.out.println("[NetworkReceiveEvents: processLaunch] Userfs after " + service.getUsers());
			network.changeServiceNotify(service);
		} else {
			System.out.println("[NetworkReceiveEvents: processLaunch] Buyer Case");
			// An seller make me an new offer
			// Update price from payload

			Service service = network.getService(message.getServiceName());
			if (service == null) {
				System.err.println("Unknown service : " + message.getServiceName());
				return;
			}

			Service newService = service.clone();
			UserEntry userEntry = service.getUser(message.getUsername());
			if (userEntry == null) {
				System.err.println("User not found : " + userEntry.getUsername());
				System.out.println("Check if webService can help us ...");
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
	}

	private void processLaunchResponse(SelectionKey key, Message message) {
		System.out.println("[NetworkReceiveEvent: processLaunchResponse] Message : " + message);

		Service service = network.getService(message.getServiceName());
		if (service == null) {
			System.err.println("Unknown service : " + message.getServiceName());
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
		// System.err.println("User not found : " + userEntry.getUsername());
		// System.out.println("Check if webService can help us ...");
		// // TODO : userEntry =
		// // webService.getUserProfile(userEntry.getUsername)
		// // Check if new userEntry is not null
		// // Add new userEntry to service
		//
		// userEntry = (UserEntry) message.getPayload();
		// }

		System.out.println("[NetworkReceiveEvent: processLaunchResponse] Before users : " + newService.getUsers());
		newService.getUsers().remove(userEntry);
		newService.getUsers().add(userEntry);
		System.out.println("[NetworkReceiveEvent: processLaunchResponse] After users : " + newService.getUsers());

		// UserEntry serviceUser = newService.getUser(userEntry.getUsername());
		// if (serviceUser == null) {
		// newService.addUserEntry(userEntry);
		// } else {
		// UserEntry messageUserEntry = (UserEntry) message.getPayload();
		// serviceUser.setPrice(messageUserEntry.getPrice());
		// serviceUser.setTime(messageUserEntry.getTime());
		// serviceUser.setOffer(Offer.OFFER_MADE);
		// }

		System.out.println("[NetworkReceiveEvent: processLaunchResponse] New Service : " + newService);
		network.changeServiceNotify(newService);
	}

	/**
	 * Only a buyer can receive this type of message. Payload contains an
	 * <code>UserEntry</code> object
	 * 
	 * @param key
	 * @param message
	 */
	private void processMakeOffer(SelectionKey key, Message message) {
		UserProfile userProfile = network.getUserProfile();

		if (userProfile.getRole() == UserRole.SELLER) {
			System.err.println("Only a buyer can receive this type of message");
			return;
		}

		Service service = network.getService(message.getServiceName());
		if (service == null) {
			System.err.println("Unknown service : " + message.getServiceName());
			return;
		}

		Service newService = service.clone();
		UserEntry userEntry = service.getUser(message.getUsername());
		if (userEntry == null) {
			System.err.println("User not found : " + userEntry.getUsername());
			System.out.println("Check if webService can help us ...");
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

		network.changeServiceNotify(newService);
	}

	private void processAccept(SelectionKey key, Message message) {
		UserProfile userProfile = network.getUserProfile();

		System.out.println("[NetworkReceiveEvent: processAccept] Message : " + message);
		if (userProfile.getRole() == UserRole.BUYER) {
			System.err.println("[NetworkReceiveEvent: processAccept] Only a seller can receive this type of message");
			return;
		}

		/* Get actual service */
		Service service = network.getService(message.getServiceName());
		if (service == null) {
			System.err.println("[NetworkReceiveEvent: processAccept] Unknown service : " + message.getServiceName());
			return;
		}

		System.out.println("[NetworkReceiveEvent: processAccept] Service : " + service);
		System.out.println("[NetworkReceiveEvent: processAccept] Username : " + message.getPayload());
		UserEntry user = service.getUser((String)message.getPayload());
		if (user == null) {
			System.err.println("[NetworkReceiveEvent: processAccept] User " + message.getUsername() + " not found");
			return;
		}

		user.setOffer(Offer.OFFER_ACCEPTED);
		System.out.println("[NetworkReceiveEvent: processAccept] New service : " + service);

		network.changeServiceNotify(service);
	}

	private void processRefuse(SelectionKey key, Message message) {
		// TODO Auto-generated method stub
	}

	private void processTransferSize(SelectionKey key, Message message) {
		// TODO Auto-generated method stub

	}

	private void processTransferChunk(SelectionKey key, Message message) {
		// TODO Auto-generated method stub

	}

	protected synchronized void process() {

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
	}
}