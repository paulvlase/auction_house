package gui.items;

import gui.MainWindow;
import gui.ProfileWindow;
import interfaces.Command;
import interfaces.Gui;

public class ProfileItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

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
