package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;
import data.Service;

public class DropAuctionItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

	public DropAuctionItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Drop auction");

		if (service.isInactiveState()) {
			service.setDropAuctionState(row);
			gui.publishService(new Service(this.service));
		}
	}
}
