package mediator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import config.FilesConfig;
import config.GlobalConfig.ServiceType;
import config.GlobalConfig.UserType;
import data.LoginCred;
import data.Service;
import data.UserProfile;
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
public class MockupMediator implements MediatorGui, MediatorNetwork, MediatorWeb {
	private Gui					gui;
	private Network				net;
	private WebServiceClient	web;

	private UserProfile         profile;

	public MockupMediator() {

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
		{
			Service service =  new Service("service");
			service.setPrice(2.2);
			service.setTime(101);
			startTransfer(service);
		}
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
		web.logOut();
		profile = null;
	}
	

	@Override
	public boolean startTransfer(Service service) {
		return net.startTransfer(service);
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
	public int addOffer(String service) {
		return web.addOffer(service);
	}
	
	@Override
	public int removeOffer(String service) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int launchOffer(Service service) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dropOffer(Service service) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/* Buyer */
	@Override
	public int acceptOffer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int refuseOffer() {
		// TODO Auto-generated method stub
		return 0;
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
	
	@Override
	public ArrayList<Service> loadOffers() {
		if (profile == null)
			return null;
		
		ArrayList<Service> services = null;
		if (profile.getRole() == UserRole.BUYER)
			services = loadServicesFile(FilesConfig.DEMANDS_FILENAME, ServiceType.DEMAND);

		if (profile.getRole() == UserRole.SELLER)
			services = loadServicesFile(FilesConfig.SUPPLIES_FILENAME, ServiceType.SUPPLY);
		
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
				br = new BufferedReader(
						new FileReader(demandsFile));
				
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
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return services;
	}
}
