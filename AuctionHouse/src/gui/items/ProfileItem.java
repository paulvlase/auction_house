package gui.items;

import javax.swing.JMenuItem;

import data.Service;

import gui.MainWindow;
import gui.ProfileWindow;
import interfaces.Command;
import interfaces.Gui;

public class ProfileItem extends GuiAbstractItem implements Command {

	public ProfileItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		new ProfileWindow(gui).showWindow();
	}

}
