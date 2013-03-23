package gui.items;

import javax.swing.JMenuItem;

import data.Service;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class LaunchRequestItem extends GuiAbstractItem implements Command {

	public LaunchRequestItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Launch Offer");
		gui.launchOffer(service.clone());
	}
}
