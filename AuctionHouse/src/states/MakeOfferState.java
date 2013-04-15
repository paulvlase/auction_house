package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class MakeOfferState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(MakeOfferState.class);

	private Integer				userIndex;
	private Double				price;

	public MakeOfferState(Service service) {
		this.service = service;
	}

	public MakeOfferState(MakeOfferState state) {
		service = state.service;
		userIndex = state.userIndex;
		price = state.price;
	}

	public void executeNet(NetworkService net) {
		System.out.println("[MakeOfferState:executeNet()] Begin");
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			UserEntry user = users.get(userIndex);
			user.setOffer(Offer.OFFER_MADE);
			user.setPrice(price);

			service.setEnabledState();
			// TODO
			// net.changeServiceNotify(service);
		}

		System.out.println("[MakeOfferState:executeNet()] End");
	}

	public void executeWeb(WebService web) {
	}

	public void updateState(Integer userIndex, Double price) {
		this.userIndex = userIndex;
		this.price = price;
	}

	public String getName() {
		return "MakeOfferState";
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

	@Override
	public MakeOfferState clone() {
		// TODO Auto-generated method stub
		return new MakeOfferState(this);
	}
}
