package network;

import interfaces.Command;
import interfaces.MediatorNetwork;

import java.util.ArrayList;
import java.util.Random;

import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class TransferFailedEvent implements Command {
	private MediatorNetwork		med;
	private Service			service;
	private static Random	random	= new Random();

	public TransferFailedEvent(MediatorNetwork med, Service service) {
		this.med = med;
		this.service = service;
	}

	@Override
	public void execute() {
		ArrayList<UserEntry> users = service.getUsers();

		if (users == null || users.size() == 0) {
			return;
		}
		Integer userIndex = random.nextInt(users.size());

		UserEntry user = users.get(userIndex);

		if (user.getOffer() != Offer.TRANSFER_IN_PROGRESS
				&& user.getOffer() != Offer.TRANSFER_STARTED) {
			return;
		}

		user.setOffer(Offer.TRANSFER_FAILED);

		med.putOffer(service);
		med.changeServiceNotify(service);
	}
}