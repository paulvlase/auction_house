package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.FilesConfig;
import config.GuiConfig;
import config.GlobalConfig.UserType;
import data.Service;
import data.User;
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
	private MainWindow  mainWindow;

	public GuiImpl(MediatorGui med) {
		boolean showLogin = false;
		
		this.med = med;
		med.registerGui(this);
		
		login = new Login(this);
	}

	public String getName() {
		return this.med.getName();
	}
	
	public void login() {
		File loginFile = new File(FilesConfig.LOGIN_FILENAME);

		if (!loginFile.exists()) {
			login.setVisible(true);
			return;
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(loginFile));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		User user = loadLoginFile();
		try {
			br.readLine();
			String username = br.readLine();
			
			br.readLine();
			String typeStr = br.readLine();
			
			UserType type;
			if (typeStr.equals("SELLER")) {
				type = UserType.SELLER;
			} else if (typeStr.equals("BUYER")) {
				type = UserType.BUYER;
			} else {
				System.out.println("Invalid user type");
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			br.readLine();
			String password = br.readLine();
			
			signIn(username, password, type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void signIn(String username, String password, UserType type) {
		if (med.signIn(username, password)) {
			login.setVisible(false);
			
			//TODO: autentificare reusita, afisez fereastra pentru
			// cumparator, vanzator
			System.out.println("Signed in");
			
			ArrayList<Service> services = null;
			if (type == UserType.BUYER) {
				services = loadDemandsFile();
			}
			if (type == UserType.SELLER) {
				services = loadSuppliesFile();
			}
			
			mainWindow = new MainWindow(services);
			mainWindow.setVisible(true);
		} else {
			// autentificare esuata, afisare dialog
			JOptionPane.showMessageDialog(null,
					GuiConfig.getValue(GuiConfig.WRONG_USR_PASS),
					GuiConfig.getValue(GuiConfig.WRONG_USR_PASS),
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private User loadLoginFile() {
		return null;
	}
	
	private ArrayList<Service> loadDemandsFile() {
		File demandsFile = new File("FilesConfig.");
		//TODO : Change this ...
		return null;
	}
	
	private ArrayList<Service> loadSuppliesFile() {
		// TODO : Fix this ...
		return null;
	}
}
