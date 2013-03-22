package gui.items;

import javax.swing.JMenuItem;

import interfaces.Command;
import interfaces.Gui;
import gui.MainWindow;

public class DropRequestItem extends JMenuItem implements Command {
	MainWindow window;
	Gui gui;
	
	public DropRequestItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;
		
		addActionListener(window.getActionListener());
	}
	
	@Override
	public void execute() {
		System.out.println("Drop Request");
	}
}
