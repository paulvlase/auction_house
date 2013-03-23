package gui.items;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JMenuItem;

import data.Service;

import gui.MainWindow;
import interfaces.Command;
import interfaces.Gui;

public class ExitItem extends GuiAbstractItem implements Command {

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
