package gui.items;

import javax.swing.JButton;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class SignOutButton extends JButton implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(SignOutButton.class);

	MainWindow					window;
	Gui							gui;

	public SignOutButton(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

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
