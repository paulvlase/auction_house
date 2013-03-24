package gui.items;

import javax.swing.JMenuItem;

import data.Pair;
import data.Service;

import gui.MainWindow;
import gui.PriceWindow;
import interfaces.Command;
import interfaces.Gui;

public class MakeOfferItem extends GuiAbstractItem implements Command {
	private Double price;

	public MakeOfferItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Make offer");
		new PriceWindow(gui, new Pair<Service, Integer>(service, row)).setVisible(true);
	}
	
	public void setPrice(Double price){
		this.price = price;
	}

}
