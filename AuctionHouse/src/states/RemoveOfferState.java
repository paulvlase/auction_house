package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;
import data.UserProfile;

public class RemoveOfferState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(RemoveOfferState.class);

	public RemoveOfferState(Service service) {
		// TODO: logger.setLevel(Level.OFF);
		this.service = service;
	}

	public RemoveOfferState(RemoveOfferState state) {
		//TODO: logger.setLevel(Level.OFF);
		service = state.service;
	}

	@Override
	public void executeNet(NetworkService net) {
		logger.debug("Begin");
//		if (service.getUsers() == null) {
//			return;
//		}
//
//		for (UserEntry userEntry : service.getUsers()) {
//			if (userEntry.getOffer() == Offer.TRANSFER_IN_PROGRESS || userEntry.getOffer() == Offer.OFFER_ACCEPTED) {
//				/* Cauta in lista de thread-uri ca sa stergi */
//				userEntry.setOffer(Offer.OFFER_DROP);
//			}
//		}

		net.cancelTransfer(getService());
		logger.debug("End");
	}

	public void executeWeb(WebService web) {
		logger.debug("Begin");
		// TODO
		// web.removeOffer(service.getName());
		web.notifyNetwork(service);
		logger.debug("End");
	}

	public void updateState() {
	}

	public String getName() {
		return "RemoveOfferState";
	}

	@Override
	public ArrayList<Message> asMessages(NetworkService net) {
		logger.debug("Begin");
		UserProfile userProfile = net.getUserProfile();
		ArrayList<Message> list = null;
		Boolean first = true;

		for (UserEntry user : service.getUsers()) {
			Message message = new Message();
			
			message.setType(data.Message.MessageType.REMOVE);
			message.setServiceName(service.getName());
			message.setUsername(new String(user.getUsername()));
			message.setPayload(userProfile.getUsername());
			message.setDestination(user.getUsername());
			message.setSource(userProfile.getUsername());

			if (first) {
				list = message.asArrayList();
			} else {
				list.add(message);
			}
		}

		logger.debug("End");
		return list;
	}

	@Override
	public RemoveOfferState clone() {
		return new RemoveOfferState(this);
	}
}
