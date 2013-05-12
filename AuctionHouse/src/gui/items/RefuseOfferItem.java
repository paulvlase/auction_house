package gui.items;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;
import data.Service;

public class RefuseOfferItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(RefuseOfferItem.class);

	public RefuseOfferItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("Refuse Request");

		if (service.isEnabledState()) {
			service.setRefuseOfferState(row);
			gui.publishService(new Service(this.service));
		}
	}

}
