package states;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

import java.util.ArrayList;

import network.Message;
import data.Service;
import data.Service.Status;
import data.UserEntry;

public class DropOfferState implements State {
	private Service service;

	public DropOfferState() {

	}

	public void executeNet(MediatorNetwork mednet) {
		service.setStatus(Status.INACTIVE);
		service.setUsers(null);

		mednet.removeOffer(service.getName());
		System.out
				.println("[DropOfferState:executeNet()] " + service.getName());

		service.setInactiveState();
		mednet.stopTransfer(service);
		mednet.changeServiceNotify(service);
	}

	public void executeWeb(MediatorWeb medweb) {

	}

	public void setState(Service service) {
		this.service = service;
	}

	public String getName() {
		return "Drop Offer";
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
