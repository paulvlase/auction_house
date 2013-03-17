package gui;

import config.GlobalConfig.UserType;
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

	public GuiImpl(MediatorGui med) {
		this.med = med;

		med.registerGui(this);
		
		login = new Login(this);
		login.setVisible(true);
	}

	public String getName() {
		return this.med.getName();
	}
	
	public void signIn(String username, String password, UserType type) {
		if (med.signIn(username, password)) {
			login.setVisible(false);
			
			//TODO: autentificare reusita, afisez fereastra pentru
			// cumparator, vanzator
			System.out.println("Signed in");
		} else {
			// autentificare esuata, afisare dialog
			System.out.println("Wrong username or password");
		}
	}
}
