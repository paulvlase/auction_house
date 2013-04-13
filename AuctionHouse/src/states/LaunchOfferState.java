package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import webClient.Util;

import network.Message;
import data.Service;
import data.Service.Status;
import data.UserEntry;

public class LaunchOfferState implements State {
	private Service service;

	public LaunchOfferState() {

	}

	public void executeNet(NetworkService net) {
		System.out.println("[LaunchOfferState:executeNet()] Begin");
		
		System.out.println("[LaunchOfferState:executeNet()] End");
	}

	public void executeWeb(WebService web) {
		System.out.println("[LaunchOfferState:executeWeb()] Begin");

		service.setStatus(Status.ACTIVE);
		service.setInactiveState();

		// TODO
		Service service = (Service) Util.askWebServer(this);
		web.notifyNetwork(service);
		//web.changeServiceNotify(service);

		System.out.println("[LaunchOfferState:executeWeb()] End");
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
