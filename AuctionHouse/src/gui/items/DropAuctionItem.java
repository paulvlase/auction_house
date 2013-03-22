package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

import javax.swing.JMenuItem;

public class DropAuctionItem  extends JMenuItem implements Command {
	MainWindow window;
	Gui gui;
	
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
