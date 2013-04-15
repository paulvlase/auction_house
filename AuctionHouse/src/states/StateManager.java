package states;

import java.io.Serializable;
import java.util.ArrayList;

import data.Message;
import data.Service;
import interfaces.MediatorNetwork;
import interfaces.NetworkMediator;
import interfaces.NetworkService;
import interfaces.WebService;

public class StateManager implements Serializable{
	private static final long	serialVersionUID	= 1L;
	private PendingState pendingState;
	private MakeOfferState makeOfferState;
	private RemoveOfferState removeOfferState;
	private DropAuctionState dropAuctionState;
	private RefuseOfferState refuseOfferState;
	private AcceptOfferState acceptOfferState;
	
	private LaunchOfferState launchOfferState;
	private DropOfferState dropOfferState;

	private State currentState;
	
	public StateManager() {
		pendingState = new PendingState();
		makeOfferState = new MakeOfferState();
		removeOfferState = new RemoveOfferState();
		dropAuctionState = new DropAuctionState();
		refuseOfferState = new RefuseOfferState();
		acceptOfferState = new AcceptOfferState();
		
		launchOfferState = new LaunchOfferState();
		dropOfferState = new DropOfferState();

		currentState = pendingState;
	}
	
	public void setPendingState() {
		currentState = pendingState;
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
	
		System.out.println("[StateManager: setAcceptOfferState]");
	}
	
	public void setLaunchOfferState(Service service) {
		launchOfferState.setState(service);
		currentState = launchOfferState;
		System.out.println("[StateManager: setLaunchOfferState]");
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
		return currentState == pendingState;
	}
	
	public void executeNet(NetworkService net) {
		currentState.executeNet(net);
	}
	
	public void executeWeb(WebService web) {
		currentState.executeWeb(web);
	}
	
	public ArrayList<Message> asMessages(NetworkService net) {
		return currentState.asMessages(net);
	}
}
