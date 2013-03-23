package gui.items;

import javax.swing.JMenuItem;

import data.Service;

import interfaces.Command;
import interfaces.Gui;
import gui.MainWindow;

public class DropRequestItem extends GuiAbstractItem implements Command {

	public DropRequestItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("User : " + service.getUsers());
		gui.dropOffer(service);
	}
}
