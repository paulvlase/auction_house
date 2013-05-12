package gui.items;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ExitItem extends GuiAbstractItem implements Command {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(ExitItem.class);

	public ExitItem(MainWindow window, Gui gui) {
		// TODO: logger.setLevel(Level.OFF);
		this.window = window;
		this.gui = gui;

		addActionListener(window.getActionListener());
	}

	@Override
	public void execute() {
		logger.debug("Exit Action");
		gui.logOut();

		/* Dispose main window */
		WindowEvent wev = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
}
