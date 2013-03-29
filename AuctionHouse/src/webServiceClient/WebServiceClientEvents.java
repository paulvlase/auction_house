package webServiceClient;

import interfaces.Command;
import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

import java.util.Date;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.SwingWorker;

import network.NetworkAdapter;
import network.NetworkJoinThread;

import data.LoginCred;
import data.Pair;
import data.Service;
import data.UserEntry;
import data.UserProfile;
import data.Service.Status;
import data.UserEntry.Offer;
import data.UserProfile.UserRole;

/**
 * WebServiceClient module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceClientEvents extends SwingWorker<Command, Command> {
	private Random				random;
	private ArrayList<String>	usersOnline;

	private MediatorWeb			med;

	public WebServiceClientEvents(MediatorWeb med) {
		this.med = med;

		random = new Random();
		usersOnline = new ArrayList<String>();
	}

	@Override
	protected Command doInBackground() throws Exception {
		int timeLimit = 2500;
		System.out.println("[WebserviceClientEvents:doInBackground()]");
		try {
			while (true) {
				int sleepTime = 1000 + random.nextInt(timeLimit);

				Thread.sleep(sleepTime);
				publishEvents();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	protected void process(List<Command> events) {
		for (Command event : events) {
			System.out.println("[WebserviceClientEvents:process()]");
			event.execute();
		}
	}

	@Override
	protected void done() {
		System.out.println(Thread.currentThread());
		if (isCancelled()) {
			System.out.println("Cancelled !");
		} else {
			System.out.println("Done !");
		}
	}

	public void publishEvents() {
		System.out.println("[webServiceClientEvents:generateEvents()] Begin");

		Integer n = random.nextInt(1000);

		switch (n % 9) {
		case 1: {
			publish(new LogInEvent(med, usersOnline));
		}
			break;

		case 2: {
			publish(new LogOutEvent(med, usersOnline));
		}
			break;

		case 3: {
			publish(new NewOfferEvent(med, usersOnline));
		}
			break;

		case 4: {
			publish(new RemovedOfferEvent(med));
		}
			break;
		}

		System.out.println("[webServiceClientEvents:generateEvents()] End");
	}

	public void publishService(Service service) {
		publish(new WebAdapter(service));
	}

	public void publishServices(ArrayList<Service> services) {
		for (Service service : services) {
			publish(new WebAdapter(service));
		}
	}
}
