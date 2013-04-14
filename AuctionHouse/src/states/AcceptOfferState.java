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

public class AcceptOfferState implements State {
	private static final long	serialVersionUID	= 1L;
	static Logger logger = Logger.getLogger(AcceptOfferState.class);

	private Service service;
	private Integer userIndex;

	public AcceptOfferState() {
		// TODO: logger.setLevel(Level.OFF);	
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
			//net.startTransfer(service);
		}
	}

	
	public void executeWeb(WebService web) {
		
	}
	
	public void setState(Service service, Integer userIndex) {
		this.service = service;
		this.userIndex = userIndex;
	}

	public String getName() {
		return "Inactive";
	}

	@Override
	public ArrayList<Message> asMessages() {
		logger.debug("Begin");

		Message message = new Message();
		message.setType(data.Message.MessageType.ACCEPT);
		message.setServiceName(service.getName());
		message.setUsername(service.getUsers().get(userIndex).getUsername());
		message.setDestination(service.getUsers().get(userIndex).getUsername());
		
		return message.asArrayList();
	}
}