package gui.items;

import gui.AddNewService;
import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

import javax.swing.JMenuItem;

import data.Service;

public class AddServiceItem  extends GuiAbstractItem implements Command {

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
