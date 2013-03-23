package gui.items;

import javax.swing.JMenuItem;

import data.Service;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class SignOutItem extends GuiAbstractItem implements Command {

	public SignOutItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("TODO signOutAction");
		gui.logOut();
	}
}
