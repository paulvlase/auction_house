package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;

public class ExitItem extends GuiAbstractItem implements Command {

	private static final long	serialVersionUID	= 1L;

	public ExitItem(MainWindow window, Gui gui) {
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		System.out.println("Exit Action");
		gui.logOut();

		/* Dispose main window */
		WindowEvent wev = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
}
