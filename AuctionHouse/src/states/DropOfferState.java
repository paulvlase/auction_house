package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import data.Message;
import data.Service;
import data.Service.Status;
import data.UserEntry;

public class DropOfferState implements State {
	private static final long	serialVersionUID	= 1L;
	private Service service;

	public DropOfferState() {

	}

	public void executeNet(NetworkService net) {
		service.setStatus(Status.INACTIVE);
		service.setUsers(null);

		// TODO
		//net.removeOffer(service.getName());
		System.out
				.println("[DropOfferState:executeNet()] " + service.getName());

		service.setInactiveState();
		// TODO
		//net.stopTransfer(service);
		//net.changeServiceNotify(service);
	}

	public void executeWeb(WebService web) {

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
