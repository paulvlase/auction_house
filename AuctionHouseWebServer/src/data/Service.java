package data;

import interfaces.NetworkService;
import interfaces.WebService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import states.StateManager;

import data.UserProfile.UserRole;

/**
 * If user field is null, then this offer is inactive, otherwise it's active. If
 * time is 0, this offer is expired. If price is 0, this offer is a demand,
 * otherwise it's supply.
 * 
 * A service that have one of the statuses : TRANSFER_* should have only one
 * user entry, he will be the user who accepted the transfer, the rest of the
 * users specified in <code>users</code> field will be ignored.
 * 
 * @author Ghennadi Procopciuc
 * @author Paul Vlase
 * @see Status
 */
public class Service implements Comparable<Service>, Serializable {
	private static final long		serialVersionUID	= 1L;
	private static Logger			logger				= Logger.getLogger(Service.class);

	private String					name;
	private long					time;
	private double					price;
	private ArrayList<UserEntry>	users;
	private Status					status;

	private StateManager			stateMgr;

	/**
	 * This field will be used only with a TRANSFER_* status, otherwise it means
	 * nothing.
	 */
	private int						progress;

	/**
	 * Service status :
	 * 
	 * INACTIVE - if no one offers this service
	 * 
	 * ACTIVE - if someone offers this service
	 * 
	 * TRANSFER_* - Transaction statuses
	 * 
	 */
	public enum Status {
		NEW, ACTIVE, INACTIVE, TRANSFER_STARTED, TRANSFER_IN_PROGRESS, TRANSFER_COMPLETE, TRANSFER_FAILED, DROP
	};

	public Service() {
		// TODO: logger.setLevel(Level.OFF);
	}

	public Service(String name, ArrayList<UserEntry> users, Status status) {
		// TODO: logger.setLevel(Level.OFF);

		this.name = name;
		this.users = users;
		this.time = 0;
		this.price = 0;
		this.status = status;
		this.stateMgr = new StateManager(this);
	}

	@SuppressWarnings("unchecked")
	public Service(Service service) {
//		 logger.setLevel(Level.OFF);

		this.name = service.getName();

		if (service.getUsers() == null) {
			this.users = service.getUsers();
		} else {
			this.users = (ArrayList<UserEntry>) service.getUsers().clone();
		}
		this.time = service.getTime();
		this.price = service.getPrice();
		this.progress = service.getProgress();
		this.status = service.getStatus();
		this.stateMgr = service.getStateMgr().clone();
		this.stateMgr.setService(this);
	}

	public Service(String serviceName) {
		this(serviceName, null, Status.INACTIVE);
	}

	public StateManager getStateMgr() {
		return stateMgr;
	}

	/**
	 * Note, if <code>status</code> is not <code>Status.INACTIVE</code>, after
	 * instantiation you have to add at least one
	 * <code>UserEntry<code>, otherwise you will get unpredictable results
	 * 
	 * @param serviceName
	 *            Name of the service
	 * @param status
	 *            Service/Transaction status
	 * @see Status
	 */
	public Service(String serviceName, Status status) {
		this(serviceName, null, status);
	}

	public void executeNet(NetworkService net) {
		stateMgr.executeNet(net);
	}

	public void executeWeb(WebService web) {
		stateMgr.executeWeb(web);
	}

	public void executeGui() {
	}

	public boolean isEnabledState() {
		return stateMgr.isEnabledState();
	}

	public void addUserEntry(UserEntry user) {
		if (users == null) {
			users = new ArrayList<UserEntry>();
		}

		users.add(user);
	}

	public ArrayList<UserEntry> getUsers() {
		return users;
	}

	public UserEntry getUser(String username) {
		if (users == null) {
			return null;
		}

		for (UserEntry user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}

		return null;
	}

	public void setUsers(ArrayList<UserEntry> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String serviceName) {
		this.name = serviceName;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getPrice() {
		return price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// TODO Manage case when error occurs during transfer
	public int getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public ArrayList<ArrayList<Object>> getAsTable() {
		return getAsTable(UserRole.BUYER);
	}

	private ArrayList<Object> getActiveRow(UserRole role, UserEntry user) {
		ArrayList<Object> row = new ArrayList<Object>();

		logger.debug("user: " + user);
		logger.debug("user.getOffer(): " + user.getOffer());
		switch (user.getOffer()) {
		case TRANSFER_STARTED:
			if (role == UserRole.BUYER) {
				row = new ArrayList<Object>(Arrays.asList("", "", 0, "", "", ""));
			} else {
				row = new ArrayList<Object>(Arrays.asList("", "", user.getName(), 0, "", ""));
			}
			break;
		case TRANSFER_COMPLETE:
		case TRANSFER_IN_PROGRESS:
			if (role == UserRole.BUYER) {
				row = new ArrayList<Object>(Arrays.asList("", "", user.getProgress(), "", "", ""));
			} else {
				row = new ArrayList<Object>(Arrays.asList("", "", user.getName(), user.getProgress(), "", ""));
			}
			break;
		default:
			row = new ArrayList<Object>(Arrays.asList("", "", user.getName(), user.getOffer(), user.getTime(),
					user.getPrice()));
			break;
		}

		return row;
	}

	public ArrayList<ArrayList<Object>> getAsTable(UserRole role) {
		return null;
	}

	public Service clone() {
		return new Service(this);
	}

	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Service)) {
			return false;
		}

		return ((Service) arg0).getName().equals(name);
	}

	@Override
	public String toString() {
		return "[name: " + name + ", status: " + status + ", state: " + stateMgr + ", time: " + time + ", price: "
				+ price + ", users: " + users + "]";
	}

	@Override
	public int compareTo(Service o) {
		return getName().compareTo(o.getName());
	}

	public void setEnabledState() {
		stateMgr.setEnabledState();
	}

	public void setAccceptOfferState(Integer userIndex) {
		stateMgr.setAcceptOfferState(userIndex);
	}

	public void setDropAuctionState() {
		stateMgr.setDropAuctionState();
	}

	public void setDropOfferState() {
		stateMgr.setDropOfferState();
	}

	public void setLaunchOfferState() {
		stateMgr.setLaunchOfferState();
	}

	public void setRefuseOfferState(Integer userIndex) {
		stateMgr.setRefuseOfferState(userIndex);
	}

	public void setRemoveOfferState() {
		stateMgr.setRemoveOfferState();
	}

	public void setMakeOfferState(Integer userIndex, Double price) {
		stateMgr.setMakeOfferState(userIndex, price);
	}

	public ArrayList<Message> asMessages(NetworkService net) {
		return stateMgr.asMessages(net);
	}

	public String getStateName() {
		return stateMgr.getStateName();
	}
}
