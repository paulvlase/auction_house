package webServiceClient;

import interfaces.Command;
import interfaces.MediatorWeb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import data.Service;
import data.Service.Status;
import data.UserEntry;
import data.UserEntry.Offer;

public class RemovedOfferEvent implements Command {
	private MediatorWeb			med;

	private static Random		random	= new Random();

	public RemovedOfferEvent(MediatorWeb med) {
		this.med = med;
	}

	public void execute() {
		for (Map.Entry<String, Service> offer : med.getOffers().entrySet()) {
			Service service = offer.getValue();
			ArrayList<UserEntry> userEntries = service.getUsers();

			if (userEntries == null) {
				continue;
			}

			for (UserEntry userEntry: userEntries) {
				Integer n = random.nextInt(1000);
				
				if (n % 9 == 1) {
					if (service.getStatus() == Status.TRANSFER_STARTED ||
							service.getStatus() == Status.TRANSFER_IN_PROGRESS) {
						
						med.stopTransfer(service);
						return;
					} 
					if (service.getStatus() == Status.ACTIVE &&
							(userEntry.getOffer() == Offer.TRANSFER_STARTED ||
							userEntry.getOffer() == Offer.TRANSFER_IN_PROGRESS)) {
						med.stopTransfer(service);
						return;
					}

					userEntries.remove(userEntry);
				}
				med.changeServiceNotify(service);
			}
		}
	}
}
