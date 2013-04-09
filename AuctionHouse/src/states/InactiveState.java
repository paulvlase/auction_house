package states;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

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
}
