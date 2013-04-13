package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class RemoveOfferState implements State {
	private static final long	serialVersionUID	= 1L;
	private Service service;

	public RemoveOfferState() {
		
	}
	
	@Override
	public void executeNet(NetworkService net) {
		if (service.getUsers() == null) {
			return;
		}
		
		for (UserEntry userEntry: service.getUsers()) {
			if (userEntry.getOffer() == Offer.TRANSFER_IN_PROGRESS ||
					userEntry.getOffer() == Offer.OFFER_ACCEPTED) {
				/* Cauta in lista de thread-uri ca sa stergi */
				userEntry.setOffer(Offer.OFFER_DROP);
			}
		}
	}
	
	public void executeWeb(WebService web) {
		System.out.println("[RemoveOfferState:executeWeb()] Begin");
		//TODO
		//web.removeOffer(service.getName());
		System.out.println("[RemoveOfferState:executeWeb()] End");
	}
	
	public void setState(Service service) {
		this.service = service;
	}

	public String getName() {
		return "Inactive";
	}

	@Override
	public ArrayList<Message> asMessages() {
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
}