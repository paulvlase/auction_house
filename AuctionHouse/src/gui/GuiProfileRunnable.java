package gui;

import data.UserProfile;

public class GuiProfileRunnable implements Runnable {
	private UserProfile profile;
	private MainWindow mainWindow;
	
	public GuiProfileRunnable(MainWindow mainWindow, UserProfile profile) {
		this.profile = profile;
		this.mainWindow = mainWindow;
	}
	
	public void run() {
		mainWindow.changeProfileNotify(profile);
	}
}
