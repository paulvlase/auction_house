package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import config.FilesConfig;
import config.GuiConfig;
import config.GlobalConfig.ServiceType;
import data.Pair;
import data.Service;
import data.LoginCred;
import data.UserProfile;
import data.UserProfile.UserRole;
import interfaces.Gui;
import interfaces.MediatorGui;

/**
 * Gui module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class GuiImpl implements Gui {
	private MediatorGui	med;
	private Login		login;
	private MainWindow	mainWindow;

	public GuiImpl(MediatorGui med) {
		this.med = med;
		med.registerGui(this);

		login = new Login(this);
	}

	public void start() {
		LoginCred cred = loadLoginFile();

		if (cred == null) {
			login.showWindow();
		} else {
			logIn(cred);
		}
	}

	public void logIn(LoginCred cred) {
		if (med.logIn(cred)) {
			login.hideWindow();

			System.out.println("[GuiImpl:logIn] Signed in");

			mainWindow = new MainWindow(this);
			mainWindow.showWindow();
		} else {
			// autentificare esuata, afisare dialog
			JOptionPane.showMessageDialog(null, GuiConfig.getValue(GuiConfig.WRONG_USR_PASS),
					GuiConfig.getValue(GuiConfig.WRONG_USR_PASS), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void logOut() {
		System.out.println("[GuiImpl:logOut()] Bye bye");
		med.logOut();

		mainWindow.setVisible(false);
		login.showWindow();
	}
	
	public UserProfile getUserProfile() {
		return med.getUserProfile();
	}
	
	public boolean setUserProfile(UserProfile profile) {
		return med.setUserProfile(profile);
	}

	public boolean launchOffer(Service service) {
		return med.launchOffer(service);
	}

	public boolean launchOffers(ArrayList<Service> services) {
		return med.launchOffers(services);
	}

	public boolean dropOffer(Service service) {
		return med.dropOffer(service);
	}

	public boolean dropOffers(ArrayList<Service> services) {
		return med.dropOffers(services);
	}
	
	/* Notify */
	private LoginCred loadLoginFile() {
		File loginFile = new File(FilesConfig.LOGIN_FILENAME);

		if (!loginFile.exists())
			return null;

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(loginFile));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		LoginCred loginCred = null;
		try {
			br.readLine();
			String username = br.readLine();

			br.readLine();
			String typeStr = br.readLine();

			UserRole role;
			if (typeStr.equals("SELLER")) {
				role = UserRole.SELLER;
			} else if (typeStr.equals("BUYER")) {
				role = UserRole.BUYER;
			} else {
				System.out.println("Invalid user type");
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			br.readLine();
			String password = br.readLine();

			loginCred = new LoginCred(username, password, role);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loginCred;
	}
	
	public ArrayList<Service> loadOffers(){
		return med.loadOffers();
	}

	@Override
	public void acceptOffer(Pair<Service, Integer> pair) {
		med.acceptOffer(pair);
	}
	
	@Override
	public void changeServiceNotify(Service service) {
		if (mainWindow != null)
			mainWindow.changeServiceNotify(service);
	}

	@Override
	public void changeServicesNotify(ArrayList<Service> services) {
		if (mainWindow != null)
			mainWindow.changeServicesNotify(services);
	}

	@Override
	public void changeProfileNotify(UserProfile profile) {
		if (mainWindow != null)
			mainWindow.changeProfileNotify(profile);
	}

	@Override
	public void refuseOffer(Pair<Service, Integer> pair) {
		med.refuseOffer(pair);	
	}

	@Override
	public boolean makeOffer(Pair<Service, Integer> pair, Double price) {
		// TODO Auto-generated method stub
		return med.makeOffer(pair, price);
	}

	@Override
	public boolean dropAuction(Pair<Service, Integer> pair) {
		// TODO Auto-generated method stub
		return med.dropAuction(pair);
	}
}
