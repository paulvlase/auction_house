package gui.items;

import javax.swing.JMenuItem;

import data.Service;

import gui.MainWindow;
import gui.ProfileWindow;
import interfaces.Command;
import interfaces.Gui;

public class ProfileItem extends JMenuItem implements Command {
	private MainWindow	window;
	private Gui			gui;
	private Service		service;

	public ProfileItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		new ProfileWindow(gui).showWindow();
	}

	public void showItem(Service service) {
		this.service = service;
		setVisible(true);
	}

	public void hideItem() {
		setVisible(false);
	}
}
