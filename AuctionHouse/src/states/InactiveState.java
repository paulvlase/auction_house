package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import network.Message;

public class InactiveState implements State {
	public InactiveState() {
		
	}
	
	public void executeNet(NetworkService net) {
		System.out.println("[InactiveState: executeNet()] Begin");
	}
	
	public void executeWeb(WebService web) {
		System.out.println("[InactiveState: executeWeb()] End");
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
