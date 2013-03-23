package gui.items;

import javax.swing.JMenuItem;

import gui.MainWindow;
import gui.ProfileWindow;
import interfaces.Command;
import interfaces.Gui;

public class ProfileItem extends JMenuItem implements Command {
	MainWindow window;
	Gui gui;

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