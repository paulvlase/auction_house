package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

import javax.swing.JMenuItem;

import data.Service;

public class DropAuctionItem extends JMenuItem implements Command {
	private MainWindow	window;
	private Gui			gui;
	private Service		service;

	public DropAuctionItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Drop Auction");
	}

	public void showItem(Service service) {
		this.service = service;
		setVisible(true);
	}

	public void hideItem() {
		setVisible(false);
	}

}
