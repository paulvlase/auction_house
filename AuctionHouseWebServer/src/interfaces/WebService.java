package interfaces;

import data.Service;
import data.UserProfile.UserRole;

public interface WebService {
	public String getUsername();
	public UserRole getUserRole();
	public void notifyNetwork(Service service);
}
