package gui.items;

import gui.MainWindow;
import gui.PriceWindow;
import interfaces.Command;
import interfaces.Gui;

public class MakeOfferItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

	public MakeOfferItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Make offer");
		new PriceWindow(gui, service, row).setVisible(true);
	}

}
