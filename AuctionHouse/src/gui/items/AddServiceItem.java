package gui.items;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.AddNewService;
import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class AddServiceItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(AddServiceItem.class);

	public AddServiceItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("Add Service action");

		new AddNewService(window).setVisible(true);
	}
}
