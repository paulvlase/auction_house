package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class SignOutItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

	public SignOutItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("TODO signOutAction");
		gui.logOut();
	}
}
