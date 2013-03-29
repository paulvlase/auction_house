package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;
import data.Service;

public class RefuseOfferItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

	public RefuseOfferItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Refuse Request");
		
		if (service.isInactiveState()) {
			service.setRefuseOfferState(row);
			gui.publishService(new Service(this.service));
		}
	}

}
