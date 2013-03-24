package webServiceClient;

import interfaces.Command;
import interfaces.MediatorWeb;

import java.util.ArrayList;
import java.util.Random;

import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class TransferFailedEvent implements Command {
	private MediatorWeb		med;
	private Service			service;
	private static Random	random	= new Random();

	public TransferFailedEvent(MediatorWeb med, Service service) {
		this.med = med;
		this.service = service;
	}

	@Override
	public void execute() {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
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
}
