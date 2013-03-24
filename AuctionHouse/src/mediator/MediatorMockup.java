package mediator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;

import config.FilesConfig;
import config.GlobalConfig.ServiceType;
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
	private Hashtable<String, Service> offers;

	public MediatorMockup() {
		offers = new Hashtable<String, Service>();
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
	
	public synchronized void putOffer(Service service) {
		offers.put(service.getName(), service);
	}
	
	public synchronized Service getOffer(String serviceName) {
		return offers.get(serviceName);
	}

	@Override
	public boolean logIn(LoginCred cred) {
		System.out.println("MediatorMockup:logIn()] Aici");
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
		if (profile == null)
			return null;
		
		/*
		ArrayList<Service> services = null;
		if (profile.getRole() == UserRole.BUYER)
			services = loadServicesFile(FilesConfig.DEMANDS_FILENAME,
		ServiceType.DEMAND);
		
		if (profile.getRole() == UserRole.SELLER) {
			services = loadServicesFile(FilesConfig.SUPPLIES_FILENAME,
		ServiceType.SUPPLY);
			web.launchOffers(services);
		}

		for (Service service: services) {
			if(service.getUsers() != null){
				Collections.sort(service.getUsers());
			}
		}
		*/
		
		ArrayList<Service> services = new ArrayList<Service>();
		ArrayList<UserEntry> users = new ArrayList<UserEntry>();

		UserEntry user1 = new UserEntry("Ghennadi", Offer.OFFER_ACCEPTED, new Date().getTime() + 123456876, 22.4);
		UserEntry user2 = new UserEntry("Paul", Offer.TRANSFER_STARTED, new Date().getTime() + 223456876, 25.4);
		//user2.setProgress(22);
		UserEntry user3 = new UserEntry("Ana", Offer.OFFER_ACCEPTED, new Date().getTime() + 323456876, 2.1);
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		Service service1 = new Service("service1", users, Status.ACTIVE);
		
		Service service2 = new Service("service2", null, Status.INACTIVE);
		Service service3 = new Service("service3", null, Status.ACTIVE);
		services.add(service1);
		services.add(service2);
		services.add(service3);
		
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

	/* Seller */
	@Override
	public boolean makeOffer(Pair<Service, Integer> pair, Double price) {
		Service service = pair.getKey();
		Integer userIndex = pair.getValue();
		
		service.getUsers().get(userIndex).setPrice(price);
		return web.makeOffer(pair);
	}

	@Override
	public boolean dropAuction(Pair<Service, Integer> pair) {
		return web.dropAuction(pair);
	}
}
