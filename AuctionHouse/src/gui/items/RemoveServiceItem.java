package gui.items;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import data.Service;
import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class RemoveServiceItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger logger = Logger.getLogger(RemoveServiceItem.class);
	
	public RemoveServiceItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		window.getModel().removeService(service);
		
		if (service.isInactiveState()) {
			service.setRemoveOfferState();

			gui.publishService(service.clone());
		}
	}
}
