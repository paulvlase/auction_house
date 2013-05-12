package gui;

import org.apache.log4j.Logger;

import data.UserProfile;

public class GuiProfileRunnable implements Runnable {
	private static Logger	logger	= Logger.getLogger(GuiProfileRunnable.class);

	private UserProfile		profile;
	private MainWindow		mainWindow;

	public GuiProfileRunnable(MainWindow mainWindow, UserProfile profile) {
		// TODO: logger.setLevel(Level.OFF);

		this.profile = profile;
		this.mainWindow = mainWindow;
	}

	public void run() {
		mainWindow.changeProfileNotify(profile);
	}
}
