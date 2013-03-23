package gui.items;

import gui.AddNewService;
import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

import javax.swing.JMenuItem;

public class AddServiceItem extends JMenuItem implements Command {

	private static final long	serialVersionUID	= 1L;
	MainWindow					window;
	Gui							gui;

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
