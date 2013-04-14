package network;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Map.Entry;

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
	private NetworkImpl	network;

	public NetworkReceiveEvents(NetworkImpl network) {

		this.network = network;
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
		System.out.println("[processGetUsername()] Begin");
		NetworkDriver driver = network.getDriver();

		/* Message build */
		Message newMessage = new Message();
		newMessage.setType(MessageType.SEND_USERNAME);
		newMessage.setUsername(network.getUserProfile().getUsername());

		driver.sendData(newMessage, key);
	}

	private void processSendUsername(SelectionKey key, Message message) {
		System.out.println("[processSendUsername()] Begin");
		network.registerConnection(message.getUsername(), key);
	}

	/**
	 * Message payload will have an UserEntry object
	 * 
	 * @param key
	 * @param message
	 */
	private void processLaunch(SelectionKey key, Message message) {
		String serviceName = message.getServiceName();

		UserProfile user = network.getUserProfile();
		if (user.getRole() == UserRole.SELLER) {
			// Send new offer
			Service service = network.getService(message.getServiceName());
			if (service == null) {
				System.err.println("Unknown service : " + message.getServiceName());
				return;
			}

			Message newMessage = new Message();
			newMessage.setType(MessageType.LAUNCH_RESPONSE);
			newMessage.setPayload(new UserEntry(user.getUsername(), user.getFirstName() + " " + user.getLastName(),
					Offer.OFFER_MADE, service.getTime(), service.getPrice()));
			newMessage.setUsername(user.getUsername());
		} else {
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

			network.changeServiceNotify(newService);
		}
	}

	private void processLaunchResponse(SelectionKey key, Message message) {
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
		// TODO Auto-generated method stub
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

	protected void process() {
		System.out.println("[WebServiceClientEvents:process()] Begin");

		if (!haveToProcess()) {
			return;
		}

		for (Entry<SelectionKey, ArrayList<Message>> entry : queue.entrySet()) {
			for (Message message : entry.getValue()) {
				messageProcess(entry.getKey(), message);
			}
		}

		System.out.println("[WebServiceClientEvents:process()] Begin");

	}
}