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
	private MediatorGui	med;
	private Login		login;
	private MainWindow	mainWindow;
	private RegisterWindow registerWindow;

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
	
	public void registerUserStep1() {
		login.hideWindow();
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
		login.showWindow();
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
	public void changeServiceNotify(Service service) {
		if (mainWindow != null) {
			/*
			 SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				      // Here, we can safely update the GUI
				      // because we'll be called from the
				      // event dispatch thread
				      statusLabel.setText("Query: " + queryNo);
				    }
				  });
			*/
			mainWindow.changeServiceNotify(service);
		}
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
	public void publishService(Service service) {
		med.publishService(service);
	}
	
	@Override
	public Service createService(String name, Long time, Double price) {
		return med.createService(name, time, price);
	}
}
