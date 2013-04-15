package gui;

import interfaces.Gui;
import interfaces.MediatorGui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import config.FilesConfig;
import config.GuiConfig;
import data.LoginCred;
import data.Pair;
import data.Service;
import data.UserProfile;
import data.UserProfile.UserRole;

/**
 * Gui module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class GuiImpl implements Gui {
	private static Logger logger = Logger.getLogger(GuiImpl.class);

	private MediatorGui	med;
	private LoginWindow		loginWindow;
	private MainWindow	mainWindow;
	private RegisterWindow registerWindow;

	public GuiImpl(MediatorGui med) {
		// TODO: logger.setLevel(Level.OFF);
		
		this.med = med;
		med.registerGui(this);

		loginWindow = new LoginWindow(this);
	}

	public void start() {
		LoginCred cred = loadLoginFile();

		if (cred == null) {
			loginWindow.setVisible(true);
		} else {
			logIn(cred);
		}
	}

	public void logIn(LoginCred cred) {
		Boolean bRet = med.logIn(cred);
		if (bRet) {
			loginWindow.setVisible(false);
			loginWindow.clear();

			logger.debug("Logged in");
			
			mainWindow = new MainWindow(this);
			mainWindow.setVisible(true);
			
			ArrayList<Service> services = med.loadOffers();
			changeServicesNotify(services);
			
			if (med.getUserProfile().getRole() == UserRole.SELLER)
				med.launchOffers(services);
		} else {
			// autentificare esuata, afisare dialog
			JOptionPane.showMessageDialog(null, GuiConfig.getValue(GuiConfig.WRONG_USR_PASS),
					GuiConfig.getValue(GuiConfig.WRONG_USR_PASS), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void logOut() {
		logger.debug("Begin");

		med.logOut();

		mainWindow.setVisible(false);
		loginWindow.setVisible(true);
		
		logger.debug("End");
	}
	
	public UserProfile getUserProfile() {
		return med.getUserProfile();
	}
	
	public boolean setUserProfile(UserProfile profile) {
		return med.setUserProfile(profile);
	}
	
	public void registerUserStep1() {
		loginWindow.setVisible(false);

		if (registerWindow == null) {
			registerWindow =  new RegisterWindow(this);
		}
		registerWindow.setVisible(true);
	}
	
	public boolean registerUser(UserProfile profile) {
		return med.registerUser(profile);
	}
	
	public void registerUserStep3() {
		registerWindow.setVisible(false);
		loginWindow.setVisible(true);
}
	
	public boolean verifyUsername(String username) {
		return med.verifyUsername(username);
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
				logger.error("Invalid user type");

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
	public void changeServiceNotify(Service service) {
		logger.debug("Begin");
		
		if (mainWindow != null) {
			logger.debug("SwingUtilities.invokeLater()");
			SwingUtilities.invokeLater(new GuiServiceRunnable(mainWindow, service));
		}
		
		logger.debug("End");
	}
	
	public void changeServicesNotify(ArrayList<Service> services) {
		logger.debug("Begin");
		
		for (Service service: services) {
			changeServiceNotify(service);
		}
		
		logger.debug("End");
	}

	@Override
	public void changeProfileNotify(UserProfile profile) {
		if (mainWindow != null) {
			 SwingUtilities.invokeLater(new GuiProfileRunnable(mainWindow, profile));
		}
	}
	
	@Override
	public void publishService(Service service) {
		med.publishService(service);
	}
	
	@Override
	public Service createService(String name, Long time, Double price) {
		return med.createService(name, time, price);
	}
}
