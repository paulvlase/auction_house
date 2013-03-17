package app;

import webServiceClient.WebServiceClientImpl;
import network.NetworkImpl;
import gui.GuiImpl;
import mediator.MediatorImpl;

public class Main {
	public static void main(String[] args) {
		MediatorImpl med = new MediatorImpl();
		GuiImpl gui = new GuiImpl(med);
		NetworkImpl net = new NetworkImpl(med);
		WebServiceClientImpl web = new WebServiceClientImpl(med);
	}
}
