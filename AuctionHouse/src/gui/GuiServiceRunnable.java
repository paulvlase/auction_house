package gui;

import data.Service;

public class GuiServiceRunnable implements Runnable {
	private Service service;
	private MainWindow mainWindow;
	
	public GuiServiceRunnable(MainWindow mainWindow, Service service) {
		this.service = service;
		this.mainWindow = mainWindow;
	}
	
	public void run() {
		mainWindow.changeServiceNotify(service);
	}
}
