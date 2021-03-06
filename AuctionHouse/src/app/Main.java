package app;

import gui.GuiImpl;

import javax.swing.UIManager;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import mediator.MediatorImpl;
import network.NetworkImpl;
import webClient.WebClientImpl;

public class Main {
	static Logger	logger	= Logger.getLogger(Main.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");

		logger.setLevel(Level.OFF);
		logger.error("Begin");

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} catch (Exception e1) {
			}
		}

		MediatorImpl med = new MediatorImpl();
		GuiImpl gui = new GuiImpl(med);
		NetworkImpl net = new NetworkImpl(med);
		WebClientImpl web = new WebClientImpl(med);

		med.start();
		logger.error("End");
	}
}
