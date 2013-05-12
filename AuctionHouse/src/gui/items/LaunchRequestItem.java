package gui.items;

import org.apache.log4j.Logger;

import data.Service;
import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class LaunchRequestItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(LaunchRequestItem.class);

	public LaunchRequestItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("Launch Offer");

		if (service.isEnabledState()) {
			service.setLaunchOfferState();

			gui.publishService(service.clone());
		}
	}
}
