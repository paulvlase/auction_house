package states;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

import java.util.ArrayList;

import network.Message;

public class InactiveState implements State {
	public InactiveState() {
		
	}
	
	public void executeNet(MediatorNetwork mednet) {
		System.out.println("[InactiveState: executeNet()] ");
	}
	
	public void executeWeb(MediatorWeb medweb) {
		System.out.println("[InactiveState: executeWeb()] ");
	}

	public String getName() {
		return "Inactive";
	}

	@Override
	public ArrayList<Message> asMessages() {
		//Nothing to do, for intern usage
		return null;
	}
}
