package gui.items;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;
import data.Service;

public class AcceptOfferItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger logger = Logger.getLogger(AcceptOfferItem.class);

	public AcceptOfferItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("Accept Request");

		if (service.isInactiveState()) {
			service.setAccceptOfferState(row);

			gui.publishService(service.clone());
		}
	}
}
