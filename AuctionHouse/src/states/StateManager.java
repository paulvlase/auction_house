package states;

import data.Service;
import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

public class StateManager {
	private InactiveState inactiveState;
	private MakeOfferState makeOfferState;
	private RemoveOfferState removeOfferState;
	private DropAuctionState dropAuctionState;
	private RefuseOfferState refuseOfferState;
	private AcceptOfferState acceptOfferState;
	
	private LaunchOfferState launchOfferState;
	private DropOfferState dropOfferState;

	private State currentState;
	
	private MediatorNetwork mednet;
	private MediatorWeb     medweb;
	
	public StateManager(MediatorNetwork mednet, MediatorWeb medweb) {
		inactiveState = new InactiveState();
		makeOfferState = new MakeOfferState();
		removeOfferState = new RemoveOfferState();
		dropAuctionState = new DropAuctionState();
		refuseOfferState = new RefuseOfferState();
		acceptOfferState = new AcceptOfferState();
		
		launchOfferState = new LaunchOfferState();
		dropOfferState = new DropOfferState();

		currentState = inactiveState;
		
		this.mednet = mednet;
		this.medweb = medweb;
	}
	
	public void setInactiveState() {
		currentState = inactiveState;
	}
	
	public void setMakeOfferState(Service service, Integer userIndex, Double price) {
		makeOfferState.setState(service, userIndex, price);
		currentState = makeOfferState;
	}
	
	public void setRemoveOfferState(Service service) {
		removeOfferState.setState(service);
		currentState = removeOfferState;
	}
	
	public void setDropAuctionState(Service service) {
		dropAuctionState.setState(service);
		currentState = dropAuctionState;
	}	
	
	public void setRefuseOfferState(Service service, Integer userIndex) {
		refuseOfferState.setState(service, userIndex);
		currentState = refuseOfferState;
	}
	
	public void setAcceptOfferState(Service service, Integer userIndex) {
		acceptOfferState.setState(service, userIndex);
		currentState = acceptOfferState;
	
		System.out.println("[StateManager:setAcceptOfferState()]");
	}
	
	public void setLaunchOfferState(Service service) {
		launchOfferState.setState(service);
		currentState = launchOfferState;
		System.out.println("[StateManager:setLaunchOfferState()]");
	}
	
	public void setDropOfferState(Service service) {
		dropOfferState.setState(service);
		currentState = dropOfferState;
		System.out.println("[StateManager:setDropOfferState()]");
	}
	
	public String getName() {
		return currentState.getName();
	}
	
	public boolean isInactiveState() {
		return currentState == inactiveState;
	}
	
	public void executeNet() {
		currentState.executeNet(mednet);
	}
	
	public void executeWeb() {
		currentState.executeWeb(medweb);
	}
}
