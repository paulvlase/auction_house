package app;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import webServiceClient.WebServiceClientImpl;
import network.NetworkImpl;
import gui.GuiImpl;
import mediator.MediatorImpl;

public class Main {
	public static void main(String[] args) {
		// TODO : Check if this look and feel is available on system
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} catch (Exception e1) {}
		}
		
		MediatorImpl med = new MediatorImpl();
		GuiImpl gui = new GuiImpl(med);
		NetworkImpl net = new NetworkImpl(med);
		WebServiceClientImpl web = new WebServiceClientImpl(med);
	}
}
