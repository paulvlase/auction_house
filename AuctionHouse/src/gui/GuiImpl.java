package gui;

import javax.swing.JOptionPane;

import config.GuiConfig;
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
			JOptionPane.showMessageDialog(null,
					GuiConfig.getValue(GuiConfig.WRONG_USR_PASS),
					GuiConfig.getValue(GuiConfig.WRONG_USR_PASS), JOptionPane.ERROR_MESSAGE);
		}
	}
}
