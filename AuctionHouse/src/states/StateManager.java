package states;

import interfaces.MediatorGui;

public class StateManager {
	State inactiveState;
	State noOfferState;
	State offerMadeState;

	State currentState;
	
	public StateManager(MediatorGui med) {
		inactiveState = new InactiveState();
		noOfferState = new NoOfferState();
		offerMadeState = new OfferMadeState();

		currentState = inactiveState;
	}
	
	public void setInactive() {
		currentState = inactiveState;
	}
	
	public void setNoOffer() {
		currentState = noOfferState;
	}
	
	public void setOfferMade() {
		currentState = offerMadeState;
	}
	
	public boolean execute() {
		return currentState.execute();
	}
	
	public String getName() {
		return currentState.getName();
	}
}
