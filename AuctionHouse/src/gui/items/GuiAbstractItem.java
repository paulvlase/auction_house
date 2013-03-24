package gui.items;

import javax.swing.JMenuItem;

import gui.MainWindow;
import interfaces.Gui;
import data.Pair;
import data.Service;

public abstract class GuiAbstractItem extends JMenuItem {

	private static final long	serialVersionUID	= 1L;

	protected MainWindow		window;
	protected Gui				gui;
	protected Service			service;
	protected Integer			row;

	public void showItem(Pair<Service, Integer> pair){
		setVisible(true);
		this.service = pair.getKey();
		this.row = pair.getValue();
	}

	public void hideItem(){
		setVisible(false);
	}
}
