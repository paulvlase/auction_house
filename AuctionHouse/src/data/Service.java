package data;

import java.util.ArrayList;

/**
 * If user field is null, then this offer is inactive, otherwise it's active.
 * @author Ghennadi Procopciuc
 */
public class Service {
	private String serviceName;
	private ArrayList<UserEntry> users;
	
	public Service(String serviceName, ArrayList<UserEntry> users){
		this.serviceName = serviceName;
		this.users = users;
	}
	
	public Service(String serviceName){
		this(serviceName, null);
	}
	
	public void addUserEntry(UserEntry user){
		if(users == null){
			users = new ArrayList<UserEntry>();
		}
		
		users.add(user);
	}

	public ArrayList<UserEntry> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserEntry> users) {
		this.users = users;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
