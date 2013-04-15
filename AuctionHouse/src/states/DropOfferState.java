package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import data.Service.Status;
import data.UserEntry;

public class DropOfferState extends AbstractState {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(DropOfferState.class);

	public DropOfferState(Service service) {
		this.service = service;
	}

	public DropOfferState(DropOfferState state) {
		service = state.service;
	}

	public void executeNet(NetworkService net) {
		service.setStatus(Status.INACTIVE);
		service.setUsers(null);

		// TODO
		// net.removeOffer(service.getName());
		System.out.println("[DropOfferState:executeNet()] " + service.getName());

		service.setEnabledState();
		// TODO
		// net.stopTransfer(service);
		// net.changeServiceNotify(service);
	}

	public void executeWeb(WebService web) {

	}

	public void setState(Service service) {
		this.service = service;
	}

	public String getName() {
		return "DropOfferState";
	}

	@Override
	public ArrayList<Message> asMessages(NetworkService net) {
		System.out.println("[DropOfferState] asMessages");
		ArrayList<Message> list = new ArrayList<Message>();

		if (service.getUsers() == null) {
			return list;
		}

		for (UserEntry user : service.getUsers()) {
			Message message = new Message();
			message.setType(data.Message.MessageType.REFUSE);
			message.setServiceName(service.getName());
			message.setUsername(user.getUsername());

			list.add(message);
		}

		return list;
	}

	@Override
	public DropOfferState clone() {
		return new DropOfferState(this);
	}
}
