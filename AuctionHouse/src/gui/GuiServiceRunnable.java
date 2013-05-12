package gui;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import data.Service;

public class GuiServiceRunnable implements Runnable {
	private static Logger	logger	= Logger.getLogger(GuiServiceRunnable.class);

	private Service			service;
	private MainWindow		mainWindow;

	public GuiServiceRunnable(MainWindow mainWindow, Service service) {
		// TODO: logger.setLevel(Level.OFF);

		this.service = service;
		this.mainWindow = mainWindow;
	}

	public void run() {
		logger.debug("service: " + service);

		mainWindow.changeServiceNotify(service);
	}
}
