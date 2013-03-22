package gui.items;

import javax.swing.JMenuItem;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class SignOutItem extends JMenuItem implements Command {
	MainWindow window;
	Gui gui;

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
