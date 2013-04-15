package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class AcceptOfferState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(AcceptOfferState.class);

	private Integer				userIndex;

	public AcceptOfferState(Service service) {
		// TODO: logger.setLevel(Level.OFF);

		this.service = service;
	}

	public AcceptOfferState(AcceptOfferState state) {
		service = state.service;
		userIndex = state.userIndex;
	}

	@Override
	public void executeNet(NetworkService net) {
		synchronized (service) {
			ArrayList<UserEntry> users = service.getUsers();

			for (UserEntry user : users) {
				user.setOffer(Offer.OFFER_REFUSED);
			}

			UserEntry user = users.get(userIndex);
			user.setOffer(Offer.OFFER_ACCEPTED);

			/* TODO: communicate with server */

			users.clear();
			users.add(user);

			// TODO
			// net.startTransfer(service);
		}
	}

	public void executeWeb(WebService web) {

	}

	public void updateState(Integer userIndex) {
		this.userIndex = userIndex;
	}

	public String getName() {
		return "AcceptOfferState";
	}

	@Override
	public ArrayList<Message> asMessages(NetworkService net) {
		logger.debug("Begin");
		Message message = new Message();
		message.setType(data.Message.MessageType.ACCEPT);
		message.setServiceName(service.getName());
		message.setUsername(service.getUsers().get(userIndex).getUsername());
		message.setDestination(service.getUsers().get(userIndex).getUsername());

		return message.asArrayList();
	}

	@Override
	public AcceptOfferState clone() {
		return new AcceptOfferState(this);
	}
}
