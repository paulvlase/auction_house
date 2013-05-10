package states;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import interfaces.MediatorNetwork;
import interfaces.NetworkMediator;
import interfaces.NetworkService;
import interfaces.WebService;

public class StateManager implements Serializable {
	private static final long	serialVersionUID	= 1L;
	static Logger				logger				= Logger.getLogger(RefuseOfferState.class);

	private EnabledState		enabledState;
	private MakeOfferState		makeOfferState;
	private RemoveOfferState	removeOfferState;
	private DropAuctionState	dropAuctionState;
	private RefuseOfferState	refuseOfferState;
	private AcceptOfferState	acceptOfferState;

	private LaunchOfferState	launchOfferState;
	private DropOfferState		dropOfferState;

	protected State				currentState;

	public StateManager(Service service) {
		enabledState = new EnabledState(service);
		makeOfferState = new MakeOfferState(service);
		removeOfferState = new RemoveOfferState(service);
		dropAuctionState = new DropAuctionState(service);
		refuseOfferState = new RefuseOfferState(service);
		acceptOfferState = new AcceptOfferState(service);
		launchOfferState = new LaunchOfferState(service);
		dropOfferState = new DropOfferState(service);

		currentState = enabledState;
	}

	public StateManager(StateManager mgr) {
		enabledState = mgr.enabledState.clone();
		makeOfferState = mgr.makeOfferState.clone();
		removeOfferState = mgr.removeOfferState.clone();
		dropAuctionState = mgr.dropAuctionState.clone();
		refuseOfferState = mgr.refuseOfferState.clone();
		acceptOfferState = mgr.acceptOfferState.clone();
		launchOfferState = mgr.launchOfferState.clone();
		dropOfferState = mgr.dropOfferState.clone();

		if (mgr.currentState == mgr.enabledState) {
			currentState = enabledState;
		} else if (mgr.currentState == mgr.makeOfferState) {
			currentState = makeOfferState;	
		} else if (mgr.currentState == mgr.removeOfferState) {
			currentState = removeOfferState;
		} else if (mgr.currentState == mgr.dropAuctionState) {
			currentState = dropAuctionState;
		} else if (mgr.currentState == mgr.refuseOfferState) {
			currentState = refuseOfferState;
		} else if (mgr.currentState == mgr.acceptOfferState) {
			currentState = acceptOfferState;
		} else if (mgr.currentState == mgr.launchOfferState) {
			currentState = launchOfferState;
		} else if (mgr.currentState == mgr.dropOfferState) {
			currentState = dropOfferState;
		}
	}

	public void setEnabledState() {
		logger.debug("Begin");

		enabledState.updateState();
		currentState = enabledState;
		
		logger.debug("End");
	}

	public void setMakeOfferState(Integer userIndex, Double price) {
		logger.debug("Begin");

		makeOfferState.updateState(userIndex, price);
		currentState = makeOfferState;
		
		logger.debug("End");
	}

	public void setRemoveOfferState() {
		logger.debug("Begin");

		removeOfferState.updateState();
		currentState = removeOfferState;
		
		logger.debug("End");
	}

	public void setDropAuctionState() {
		logger.debug("Begin");

		dropAuctionState.updateState();
		currentState = dropAuctionState;
		
		logger.debug("End");
	}

	public void setRefuseOfferState(Integer userIndex) {
		logger.debug("Begin");

		refuseOfferState.updateState(userIndex);
		currentState = refuseOfferState;
		
		logger.debug("End");
	}

	public void setAcceptOfferState(Integer userIndex) {
		logger.debug("Begin");

		acceptOfferState.updateState(userIndex);
		currentState = acceptOfferState;

		logger.debug("End");
	}

	public void setLaunchOfferState() {
		logger.debug("Begin");

		launchOfferState.updateState();
		currentState = launchOfferState;

		logger.debug("End");
	}

	public void setDropOfferState() {
		logger.debug("Begin");

		dropOfferState.updateState();
		currentState = dropOfferState;

		logger.debug("End");
	}

	public String getName() {
		return currentState.getName();
	}

	public boolean isEnabledState() {
		return currentState == enabledState;
	}

	public void executeNet(NetworkService net) {
		logger.debug("Begin");
		currentState.executeNet(net);
		logger.debug("End");
	}

	public void executeWeb(WebService web) {
		logger.debug("Begin");
		logger.debug("State: " + currentState.getClass());
		currentState.executeWeb(web);
		logger.debug("End");
	}

	public ArrayList<Message> asMessages(NetworkService net) {
		logger.debug("Begin");
		ArrayList<Message> messages = currentState.asMessages(net);
		
		logger.debug("End");
		return messages;
	}

	public String getStateName() {
		return currentState.getName();
	}
	
	public String toString() {
		// TODO: return currentState.toString();
		return currentState.getName();
	}
	
	public void setService(Service service) {
		enabledState.setService(service);
		makeOfferState.setService(service);
		removeOfferState.setService(service);
		dropAuctionState.setService(service);
		refuseOfferState.setService(service);
		acceptOfferState.setService(service);
		launchOfferState.setService(service);
		dropOfferState.setService(service);
	}

	public StateManager clone() {
		return new StateManager(this);
	}
}
