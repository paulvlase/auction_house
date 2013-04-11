package states;

import java.util.ArrayList;

import network.Message;
import network.Message_Deprecated;

import data.Service;
import data.UserEntry;
import data.Service.Status;
import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

public class LaunchOfferState implements State {
	private Service service;

	public LaunchOfferState() {

	}

	public void executeNet(MediatorNetwork mednet) {
		System.out.println("LaunchOfferState(): executeNet");
	}

	public void executeWeb(MediatorWeb medweb) {
		System.out.println("LaunchOfferState(): executeWeb");
		service.setStatus(Status.ACTIVE);

		service.setInactiveState();

		medweb.changeServiceNotify(service);
	}

	public void setState(Service service) {
		this.service = service;
	}

	public String getName() {
		return "Launch Offer xxx";
	}

	@Override
	public ArrayList<Message> asMessages() {
		ArrayList<Message> list = null;
		Boolean first = true;

		for (UserEntry user : service.getUsers()) {
			Message message = new Message();
			message.setType(network.Message.MessageType.LAUNCH);
			message.setServiceName(service.getName());
			message.setUsername(user.getUsername());
			message.setPeer(user.getUsername());

			if (first) {
				list = message.asArrayList();
			} else {
				list.add(message);
			}
		}

		return list;
	}
}
