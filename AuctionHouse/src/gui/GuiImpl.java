package gui;

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
}
