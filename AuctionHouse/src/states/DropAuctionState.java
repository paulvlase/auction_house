package states;

import java.util.ArrayList;

import network.Message;
import network.Message_Deprecated;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class DropAuctionState implements State {
	private Service service;

	public DropAuctionState() {
	}

	@Override
	public void executeNet(MediatorNetwork mednet) {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			for (UserEntry user : users) {
				user.setOffer(Offer.OFFER_REFUSED);

				mednet.putOffer(service);

				System.out.println("[WebServiceClient:dropAuction()] Aici: "
						+ users);
			}

			/* Remove all users */
			service.setUsers(null);
			mednet.changeServiceNotify(service);
		}
	}

	public void executeWeb(MediatorWeb medweb) {

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