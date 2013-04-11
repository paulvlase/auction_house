package states;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

import java.util.ArrayList;

import network.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class MakeOfferState implements State {
	private Service service;
	private Integer userIndex;
	private Double price;

	public MakeOfferState() {
		
	}
	
	public void executeNet(MediatorNetwork mednet) {
		System.out.println("[MakeOfferState:executeNet()] Begin");
		ArrayList<UserEntry> users = service.getUsers();


		if (users != null) {
			UserEntry user = users.get(userIndex);
			user.setOffer(Offer.OFFER_MADE);
			user.setPrice(price);

			service.setInactiveState();
			mednet.changeServiceNotify(service);
		}
		
		System.out.println("[MakeOfferState:executeNet()] End");
	}
	
	public void executeWeb(MediatorWeb medweb) {

	}

	public void setState(Service service, Integer userIndex, Double price) {
		this.service = service;
		this.userIndex = userIndex;
		this.price = price;
	}
	
	public String getName() {
		return "Offer Made";
	}

	@Override
	public ArrayList<Message> asMessages() {
		Message message = new Message();
		UserEntry user = service.getUsers().get(userIndex);
		
		message.setType(network.Message.MessageType.MAKE_OFFER);
		message.setServiceName(service.getName());
		message.setUsername(user.getUsername());
		message.setPeer(user.getUsername());
		message.setPayload(price);
		
		return message.asArrayList();
	}
}
