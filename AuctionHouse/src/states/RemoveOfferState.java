package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class RemoveOfferState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(RemoveOfferState.class);

	public RemoveOfferState(Service service) {
		this.service = service;
	}

	public RemoveOfferState(RemoveOfferState state) {
		service = state.service;
	}

	@Override
	public void executeNet(NetworkService net) {
		if (service.getUsers() == null) {
			return;
		}

		for (UserEntry userEntry : service.getUsers()) {
			if (userEntry.getOffer() == Offer.TRANSFER_IN_PROGRESS || userEntry.getOffer() == Offer.OFFER_ACCEPTED) {
				/* Cauta in lista de thread-uri ca sa stergi */
				userEntry.setOffer(Offer.OFFER_DROP);
			}
		}
	}

	public void executeWeb(WebService web) {
		System.out.println("[RemoveOfferState:executeWeb()] Begin");
		// TODO
		// web.removeOffer(service.getName());
		System.out.println("[RemoveOfferState:executeWeb()] End");
	}

	public void updateState() {
	}

	public String getName() {
		return "RemoveOfferState";
	}

	@Override
	public ArrayList<Message> asMessages(NetworkService net) {
		System.out.println("[RemoveOfferSettate] asMessages");
		ArrayList<Message> list = null;
		Boolean first = true;

		for (UserEntry user : service.getUsers()) {
			Message message = new Message();
			message.setType(data.Message.MessageType.REFUSE);
			message.setServiceName(service.getName());
			message.setUsername(user.getUsername());

			if (first) {
				list = message.asArrayList();
			} else {
				list.add(message);
			}
		}

		return list;
	}

	@Override
	public RemoveOfferState clone() {
		return new RemoveOfferState(this);
	}
}