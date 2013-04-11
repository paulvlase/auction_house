package states;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;

import network.Message;
import network.Message_Deprecated;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class AcceptOfferState implements State {
	private Service service;
	private Integer userIndex;

	public AcceptOfferState() {
		
	}
	
	@Override
	public void executeNet(MediatorNetwork mednet) {
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
			
			mednet.startTransfer(service);
		}
	}

	
	public void executeWeb(MediatorWeb medweb) {
		
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
		Message message = new Message();
		message.setType(network.Message.MessageType.ACCEPT);
		message.setServiceName(service.getName());
		message.setUsername(service.getUsers().get(userIndex).getUsername());
		message.setPeer(service.getUsers().get(userIndex).getUsername());
		
		return message.asArrayList();
	}
}