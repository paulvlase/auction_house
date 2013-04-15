package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class MakeOfferState implements State {
	private static final long	serialVersionUID	= 1L;
	private Service service;
	private Integer userIndex;
	private Double price;

	public MakeOfferState() {
		
	}
	
	public void executeNet(NetworkService net) {
		System.out.println("[MakeOfferState:executeNet()] Begin");
		ArrayList<UserEntry> users = service.getUsers();


		if (users != null) {
			UserEntry user = users.get(userIndex);
			user.setOffer(Offer.OFFER_MADE);
			user.setPrice(price);

			service.setPendingState();
			// TODO
			//net.changeServiceNotify(service);
		}
		
		System.out.println("[MakeOfferState:executeNet()] End");
	}
	
	public void executeWeb(WebService web) {

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
	public ArrayList<Message> asMessages(NetworkService net) {
		System.out.println("[MakeOfferState] asMessages");
		Message message = new Message();
		UserEntry user = service.getUsers().get(userIndex);
		
		message.setType(data.Message.MessageType.MAKE_OFFER);
		message.setServiceName(service.getName());
		message.setUsername(user.getUsername());
		message.setDestination(user.getUsername());
		message.setPayload(price);
		
		return message.asArrayList();
	}
}
