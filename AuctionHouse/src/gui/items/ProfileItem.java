package gui.items;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.MainWindow;
import gui.ProfileWindow;
import interfaces.Command;
import interfaces.Gui;

public class ProfileItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(ProfileItem.class);

	public ProfileItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		new ProfileWindow(gui).setVisible(true);
	}

}
