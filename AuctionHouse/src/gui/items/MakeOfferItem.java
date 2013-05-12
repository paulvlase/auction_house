package gui.items;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gui.MainWindow;
import gui.PriceWindow;
import interfaces.Command;
import interfaces.Gui;

public class MakeOfferItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(MakeOfferItem.class);

	public MakeOfferItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);

		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("Make offer");
		new PriceWindow(gui, service, row).setVisible(true);
	}

}
