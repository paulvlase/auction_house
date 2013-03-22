package gui.items;

import javax.swing.JButton;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class SignOutButton extends JButton implements Command {
	MainWindow window;
	Gui gui;
	
	public SignOutButton(MainWindow window, Gui gui) {
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
