package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import data.Message;


public class PendingState implements State {
	private static final long	serialVersionUID	= 1L;

	public PendingState() {
		
	}
	
	public void executeNet(NetworkService net) {
		System.out.println("[InactiveState: executeNet()] Begin");
	}
	
	public void executeWeb(WebService web) {
		System.out.println("[InactiveState: executeWeb()] End");
	}

	public String getName() {
		return "PendingState";
	}

	@Override
	public ArrayList<Message> asMessages(NetworkService net) {
		System.out.println("[InactiveOfferState] asMessages");
		//Nothing to do, for intern usage
		return new ArrayList<Message>();
	}
}
