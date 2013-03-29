package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;
import data.Service;

public class AcceptOfferItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

	public AcceptOfferItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Accept Request");

		if (service.isInactiveState()) {
			service.setAccceptOfferState(row);

			gui.publishService(service.clone());
		}
	}
}
