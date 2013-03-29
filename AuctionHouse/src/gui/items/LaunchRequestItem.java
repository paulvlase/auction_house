package gui.items;

import data.Service;
import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class LaunchRequestItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

	public LaunchRequestItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Launch Offer");
		
		if (service.isInactiveState()) {
			service.setLaunchOfferState();

			gui.publishService(service.clone());
		}
	}
}
