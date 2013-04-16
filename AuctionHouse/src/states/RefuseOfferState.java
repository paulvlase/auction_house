package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserProfile;
import data.UserEntry.Offer;

public class RefuseOfferState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(RefuseOfferState.class);

	private Integer				userIndex;

	public RefuseOfferState(Service service) {
		// TODO: logger.setLevel(Level.OFF);
		this.service = service;
	}

	public RefuseOfferState(RefuseOfferState state) {
		// TODO: logger.setLevel(Level.OFF);
		service = state.service;
		userIndex = state.userIndex;
	}

	@Override
	public void executeNet(NetworkService net) {
		logger.debug("Begin");
		Service clonedService = service.clone();
		ArrayList<UserEntry> users = clonedService.getUsers();

		if (users != null) {
			/* TODO Will be implemented */
			UserEntry user = users.get(userIndex);

			user.setOffer(Offer.OFFER_REFUSED);
			users.remove(userIndex);

			if (users.size() == 0) {
				service.setUsers(null);
			}
			// TODO
			net.changeServiceNotify(clonedService);
		}

		logger.debug("End");
	}

	public void executeWeb(WebService web) {

	}

	public void updateState(Integer userIndex) {
		this.userIndex = userIndex;
	}

	public String getName() {
		return "RefuseOfferState";
	}

	@Override
	public ArrayList<Message> asMessages(NetworkService net) {
		logger.debug("Begin");
		UserProfile userProfile = net.getUserProfile();
		Message message = new Message();
		UserEntry user = service.getUsers().get(userIndex);

		message.setType(data.Message.MessageType.REFUSE);
		message.setServiceName(service.getName());
		message.setUsername(new String(user.getUsername()));
		message.setPayload(userProfile.getUsername());
		message.setDestination(user.getUsername());
		message.setSource(userProfile.getUsername());

		logger.debug("Message : " + message);

		logger.debug("End");
		return message.asArrayList();
	}

	@Override
	public RefuseOfferState clone() {
		return new RefuseOfferState(this);
	}
}