package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;
import data.UserProfile;

public class MakeOfferState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(MakeOfferState.class);

	private Integer				userIndex;
	private Double				price;

	public MakeOfferState(Service service) {
		// TODO: logger.setLevel(Level.OFF);
		this.service = service;
	}

	public MakeOfferState(MakeOfferState state) {
		// TODO: logger.setLevel(Level.OFF);
		service = state.service;
		userIndex = state.userIndex;
		price = state.price;
	}

	public void executeNet(NetworkService net) {
		System.out.println("Begin");
		Service clonedService = service.clone();
		ArrayList<UserEntry> users = clonedService.getUsers();

		if (users != null) {
			UserEntry user = users.get(userIndex);
			user.setOffer(Offer.OFFER_MADE);
			user.setPrice(price);

			net.changeServiceNotify(clonedService);
		}

		System.out.println("End");
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
		logger.debug("Begin");

		UserProfile profile = net.getUserProfile();
		Message message = new Message();
		UserEntry user = service.getUsers().get(userIndex);

		message.setType(data.Message.MessageType.MAKE_OFFER);
		message.setServiceName(service.getName());
		message.setUsername(user.getUsername());
		message.setDestination(user.getUsername());
		message.setSource(profile.getUsername());
		message.setPayload(new UserEntry(profile.getUsername(), profile.getFirstName() + " " + profile.getLastName(),
				Offer.OFFER_MADE, service.getTime(), price));

		logger.debug("End");
		return message.asArrayList();
	}

	@Override
	public MakeOfferState clone() {
		// TODO Auto-generated method stub
		return new MakeOfferState(this);
	}
}
