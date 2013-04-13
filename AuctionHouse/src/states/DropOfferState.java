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
		System.out.println("[DropOfferState] asMessages");
		ArrayList<Message> list = new ArrayList<Message>();

		if (service.getUsers() == null) {
			return list;
		}
		
		for (UserEntry user : service.getUsers()) {
			Message message = new Message();
			message.setType(data.Message.MessageType.REFUSE);
			message.setServiceName(service.getName());
			message.setUsername(user.getUsername());

			list.add(message);
		}

		return list;
	}
}
