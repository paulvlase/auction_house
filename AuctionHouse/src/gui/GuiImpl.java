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
import config.GlobalConfig.UserType;
import data.Service;
import data.LoginCred;
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
		this.med = med;
		med.registerGui(this);
		
		login = new Login(this);
	}

	public String getName() {
		return this.med.getName();
	}
	
	public void login() {
		LoginCred cred = loadLoginFile();
		
		if (cred == null) { 
			login.setVisible(true);

		} else {
			signIn(cred);
		}
	}
	
	public void signIn(LoginCred cred) {
		if (med.signIn(cred)) {
			login.setVisible(false);
			
			//TODO: autentificare reusita, afisez fereastra pentru
			// cumparator, vanzator
			System.out.println("Signed in");
			
			ArrayList<Service> services = null;
			if (cred.getType() == UserType.BUYER) {
				services = loadServicesFile(FilesConfig.DEMANDS_FILENAME, ServiceType.DEMAND);
			}
			if (cred.getType() == UserType.SELLER) {
				services = loadServicesFile(FilesConfig.SUPPLIES_FILENAME, ServiceType.SUPPLY);
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
	
	public void signOut() {
		System.out.println("[GuiImpl:signOut()] Bye bye");
		med.signOut();
	}
	
	public void addService(Service service) {
		
	}
	public void addServices(ArrayList<Service> services) {
		
	}
	public void removeService(Service service) {
		
	}
	public void removeServices(ArrayList<Service> services) {
		
	}
	
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
				return null;
			}
			
			br.readLine();
			String password = br.readLine();
			
			loginCred = new LoginCred(username, password, type);
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
