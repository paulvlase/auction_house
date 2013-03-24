package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class DropRequestItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

	public DropRequestItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("User : " + service.getUsers());
		gui.dropOffer(service);
	}
}
