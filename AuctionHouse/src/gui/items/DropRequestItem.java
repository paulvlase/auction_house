package gui.items;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class DropRequestItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(DropRequestItem.class);

	public DropRequestItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("Drop offer");

		if (service.isEnabledState()) {
			service.setDropOfferState();

			gui.publishService(service.clone());
		}
	}
}
