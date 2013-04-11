package states;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

import java.util.ArrayList;

import network.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class RemoveOfferState implements State {
	private Service service;

	public RemoveOfferState() {
		
	}
	
	@Override
	public void executeNet(MediatorNetwork mednet) {
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
	
	public void executeWeb(MediatorWeb medweb) {
		System.out.println("[RemoveOfferState:executeWeb()] begin");
		medweb.removeOffer(service.getName());
		System.out.println("[RemoveOfferState:executeWeb()] end");
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
			message.setType(network.Message.MessageType.REFUSE);
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