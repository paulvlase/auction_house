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
import data.UserProfile;

public class DropAuctionState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(DropAuctionState.class);

	public DropAuctionState(Service service) {
		// TODO: logger.setLevel(Level.OFF);

		this.service = service;
	}

	public DropAuctionState(DropAuctionState state) {
		service = state.service;
	}

	@Override
	public void executeNet(NetworkService net) {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			for (UserEntry user : users) {
				user.setOffer(Offer.OFFER_REFUSED);

				// TODO
				// net.putOffer(service);

				logger.info("users: " + users);
			}

			/* Remove all users */
			service.setUsers(null);
			// TODO
			// net.changeServiceNotify(service);
		}
	}

	public void executeWeb(WebService web) {

	}

	public void updateState(Service service) {

	}

	public String getName() {
		return "DropAuctionState";
	}

	@Override
	public ArrayList<Message> asMessages(NetworkService net) {
		logger.debug("Begin");
		UserProfile userProfile = net.getUserProfile();
		ArrayList<Message> list = null;
		Boolean first = true;

		/* Send refuse messages to all clients */
		for (UserEntry user : service.getUsers()) {
			Message message = new Message();

			message.setType(data.Message.MessageType.REFUSE);
			message.setServiceName(service.getName());
			message.setUsername(new String(user.getUsername()));
			message.setPayload(userProfile.getUsername());
			message.setDestination(user.getUsername());
			message.setSource(userProfile.getUsername());

			if (first) {
				list = message.asArrayList();
			} else {
				list.add(message);
			}
		}

		return list;
	}

	@Override
	public DropAuctionState clone() {
		return new DropAuctionState(this);
	}
}
