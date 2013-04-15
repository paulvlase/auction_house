package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class RefuseOfferState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(RefuseOfferState.class);

	private Integer				userIndex;

	public RefuseOfferState(Service service) {
		this.service = service;
	}

	public RefuseOfferState(RefuseOfferState state) {
		service = state.service;
		userIndex = state.userIndex;
	}

	@Override
	public void executeNet(NetworkService net) {
		ArrayList<UserEntry> users = service.getUsers();

		System.out.println("[RefuseOfferState:executeNet()]");
		if (users != null) {
			/* TODO Will be implemented */
			UserEntry user = users.get(userIndex);

			user.setOffer(Offer.OFFER_REFUSED);
			users.remove(userIndex);

			if (users.size() == 0) {
				service.setUsers(null);
			}
			// TODO
			// net.changeServiceNotify(service);
		}
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
		System.out.println("[RefuseOfferState] asMessages");
		Message message = new Message();
		UserEntry user = service.getUsers().get(userIndex);

		message.setType(data.Message.MessageType.REFUSE);
		message.setServiceName(service.getName());
		message.setUsername(user.getUsername());

		return message.asArrayList();
	}

	@Override
	public RefuseOfferState clone() {
		return new RefuseOfferState(this);
	}
}