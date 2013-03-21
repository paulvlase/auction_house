package app;

import gui.GuiImpl;

import javax.swing.UIManager;

import mediator.MockupMediator;
import network.NetworkImpl;
import webServiceClient.WebServiceClientImpl;

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
		
		MockupMediator med = new MockupMediator();
		GuiImpl gui = new GuiImpl(med);
		NetworkImpl net = new NetworkImpl(med);
		WebServiceClientImpl web = new WebServiceClientImpl(med);
		
		med.start();
		System.out.println("Exit");
	}
}
