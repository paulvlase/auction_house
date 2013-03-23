package gui.items;

import javax.swing.JMenuItem;

import data.Pair;
import data.Service;
import data.UserEntry;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class AcceptOfferItem extends GuiAbstractItem implements Command {

	public AcceptOfferItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Accept Request");
		Service service = new Service(this.service);
//		UserEntry user = new UserEntry(service.getUsers().get(row));
//		service.getUsers().clear();
//		service.getUsers().add(user);
		gui.acceptOffer(new Pair<Service, Integer>(service, row));
	}
}
