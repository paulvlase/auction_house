package gui.items;

import org.apache.log4j.Logger;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;
import data.Service;

public class DropAuctionItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(DropAuctionItem.class);

	public DropAuctionItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("Drop auction");

		if (service.isEnabledState()) {
			service.setDropAuctionState();
			gui.publishService(new Service(this.service));
		}
	}
}
