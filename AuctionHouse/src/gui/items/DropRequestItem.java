package gui.items;

import javax.swing.JMenuItem;

import data.Service;

import interfaces.Command;
import interfaces.Gui;
import gui.MainWindow;

public class DropRequestItem extends JMenuItem implements Command {
	private MainWindow	window;
	private Gui			gui;
	private Service		service;

	public DropRequestItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Drop Request");
	}
	
	public void showItem(Service service) {
		this.service = service;
		setVisible(true);
	}

	public void hideItem() {
		setVisible(false);
	}
}
