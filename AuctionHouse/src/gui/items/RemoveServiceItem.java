package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class RemoveServiceItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;	
	
	public RemoveServiceItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		window.getModel().removeService(service);
		//gui.removeOffer(service);
	}
}
