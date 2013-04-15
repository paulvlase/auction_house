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

public class DropAuctionState implements State {
	private static final long	serialVersionUID	= 1L;
	static Logger logger = Logger.getLogger(DropAuctionState.class);

	private Service service;

	public DropAuctionState() {
		// TODO: logger.setLevel(Level.OFF);
	}

	@Override
	public void executeNet(NetworkService net) {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			for (UserEntry user : users) {
				user.setOffer(Offer.OFFER_REFUSED);

				// TODO
				//net.putOffer(service);

				logger.info("users: " + users);
			}

			/* Remove all users */
			service.setUsers(null);
			// TODO
			//net.changeServiceNotify(service);
		} 
	}  

	public void executeWeb(WebService web) {

	}

	public void setState(Service service) {
		this.service = service;
	}

	public String getName() {
		return "Inactive";
	}

	@Override
	public ArrayList<Message> asMessages(NetworkService net) {
		
		logger.debug("Begin");
		ArrayList<Message> list = null;
		Boolean first = true;

		for (UserEntry user : service.getUsers()) {
			Message message = new Message();
			message.setType(data.Message.MessageType.REFUSE);
			message.setServiceName(service.getName());
			message.setUsername(user.getUsername());

			if (first) {
				list = message.asArrayList();
			} else {
				list.add(message);
			}
		}

		return list;
	}
}
