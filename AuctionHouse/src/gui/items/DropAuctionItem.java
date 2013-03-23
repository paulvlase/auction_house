package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

import javax.swing.JMenuItem;

import data.Service;

public class DropAuctionItem extends GuiAbstractItem implements Command {

	public DropAuctionItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Drop Auction");
	}
}
