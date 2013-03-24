package mediator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;

import config.FilesConfig;
import config.GlobalConfig.ServiceType;
import config.GlobalConfig.UserType;
import data.LoginCred;
import data.Pair;
import data.Service;
import data.UserEntry;
import data.UserProfile;
import data.Service.Status;
import data.UserEntry.Offer;
import data.UserProfile.UserRole;
import interfaces.Gui;
import interfaces.MediatorGui;
import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;
import interfaces.Network;
import interfaces.WebServiceClient;

/**
 * Mediator module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class MediatorMockup implements MediatorGui, MediatorNetwork, MediatorWeb {
	private Gui					gui;
	private Network				net;
	private WebServiceClient	web;

	private UserProfile			profile;

	public MediatorMockup() {

	}

	@Override
	public void registerGui(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void registerNetwork(Network net) {
		this.net = net;
	}

	@Override
	public void registerWebServiceClient(WebServiceClient web) {
		this.web = web;
	}

	@Override
	public void start() {
		// {
		// Service service = new Service("service");
		// service.setPrice(2.2);
		// service.setTime(101);
		// startTransfer(service);
		// }
		gui.start();
	}

	@Override
	public boolean logIn(LoginCred cred) {
		UserProfile profile = web.logIn(cred);

		if (profile != null) {
			this.profile = profile;
			return true;
		}
		return false;
	}

	@Override
	public void logOut() {
		System.out.println("[MockupMediator:logOut()] Bye bye");
		profile = null;
		web.logOut();
	}

	@Override
	public UserProfile getUserProfile() {
		return profile;
	}

	@Override
	public boolean setUserProfile(UserProfile profile) {
		return web.setUserProfile(profile);
	}

	/* Common */
	@Override
	public boolean launchOffer(Service service) {
		// TODO Auto-generated method stub
		return web.launchOffer(service);
	}

	@Override
	public boolean launchOffers(ArrayList<Service> services) {
		return web.launchOffers(services);
	}

	@Override
	public boolean dropOffer(Service service) {
		// TODO Auto-generated method stub
		return web.dropOffer(service);
	}

	@Override
	public boolean dropOffers(ArrayList<Service> service) {
		// TODO Auto-generated method stub
		return web.dropOffers(service);
	}

	/* Buyer */
	@Override
	public void acceptOffer(Pair<Service, Integer> pair) {
		boolean bRet;
		bRet = web.acceptOffer(pair);
		if (bRet) {
			net.startTransfer(pair.getKey());
		}
	}

	/* Seller */
	@Override
	public int makeOffer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dropAction() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static Date create(int day, int month, int year, int hourofday, int minute, int second) {
		if (day == 0 && month == 0 && year == 0)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hourofday, minute, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/* MediatorWeb */
	@Override
	public ArrayList<Service> loadOffers() {
		// if (profile == null)
		// return null;
		//
		// ArrayList<Service> services = null;
		// if (profile.getRole() == UserRole.BUYER)
		// services = loadServicesFile(FilesConfig.DEMANDS_FILENAME,
		// ServiceType.DEMAND);
		//
		// if (profile.getRole() == UserRole.SELLER)
		// services = loadServicesFile(FilesConfig.SUPPLIES_FILENAME,
		// ServiceType.SUPPLY);
		//
		// return services;
		ArrayList<Service> services = new ArrayList<Service>();
		Date date1 = create(14, 12, 2014, 18, 56, 45);
		Date date2 = create(29, 1, 2015, 10, 44, 21);

		// for (int i = 0; i < 10; i++) {

		Service service1 = new Service("service1");
		Service service2 = new Service("service2", Status.ACTIVE);
		Service service3 = new Service("service3", Status.TRANSFER_STARTED);

		service2.addUserEntry(new UserEntry("Paul Vlase", Offer.NO_OFFER, date1.getTime(), 25.2));
		service2.addUserEntry(new UserEntry("Ghennadi", Offer.NO_OFFER, date2.getTime(), 28.7));
		service2.addUserEntry(new UserEntry("Ana", Offer.NO_OFFER, date1.getTime(), 29.9));

		service3.addUserEntry(new UserEntry("Paul Vlase", Offer.OFFER_ACCEPTED, date2.getTime(),
				25.2));

		services.add(service1);
		services.add(service2);
		services.add(service3);
		// }
		
		for (Service service: services) {
			if(service.getUsers() != null){
				Collections.sort(service.getUsers());
			}
		}
		return services;
	}

	private Service parseLine(String line, ServiceType type) {
		StringTokenizer st = new StringTokenizer(line, " ");

		if (!st.hasMoreTokens())
			return null;
		Service service = new Service(st.nextToken());

		if (!st.hasMoreTokens())
			return null;

		try {
			long time = Long.parseLong(st.nextToken());

			service.setTime(time);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		if (type == ServiceType.SUPPLY) {
			try {
				double price = Double.parseDouble(st.nextToken());

				service.setPrice(price);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		if (st.hasMoreTokens())
			return null;

		return service;
	}

	private ArrayList<Service> loadServicesFile(String filename, ServiceType type) {
		ArrayList<Service> services = new ArrayList<Service>();

		File demandsFile = new File(filename);
		if (demandsFile.exists()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(demandsFile));

				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					Service d = parseLine(line, type);

					/* TODO: wrong file format. */
					services.add(d);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return services;
	}

	@Override
	public void refuseOffer(Pair<Service, Integer> pair) {
		System.out.println("[MediatorMockup:refuseOffer()] Aici");
		web.refuseOffer(pair);
	}

	@Override
	public void changeServiceNotify(Service service) {
		gui.changeServiceNotify(service);
	}

	@Override
	public void changeServicesNotify(ArrayList<Service> services) {
		gui.changeServicesNotify(services);
	}
	
	@Override
	public void changeProfileNotify(UserProfile profile) {
		gui.changeProfileNotify(profile);
	}
	
}
