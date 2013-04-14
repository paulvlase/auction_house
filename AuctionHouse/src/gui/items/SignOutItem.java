package gui.items;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class SignOutItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger logger = Logger.getLogger(SignOutItem.class);

	public SignOutItem(MainWindow window, Gui gui) {
		//TODO: logger.setLevel(Level.OFF);
		
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("signOutAction");
		gui.logOut();
	}
}
