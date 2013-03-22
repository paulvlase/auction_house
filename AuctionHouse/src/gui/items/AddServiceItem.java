package gui.items;

import javax.swing.JButton;

import gui.AddNewService;
import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class AddServiceItem extends JButton implements Command {
	MainWindow window;
	Gui gui;

	public AddServiceItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;
		
		addActionListener(window.getActionListener());
	}
	
	@Override
	public void execute() {
		System.out.println("Add Service action");

		new AddNewService(window).setVisible(true);
	}
}
