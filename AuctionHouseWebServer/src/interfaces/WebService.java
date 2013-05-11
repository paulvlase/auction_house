package interfaces;

import data.LoginCred;
import data.Service;
import data.UserProfile.UserRole;

public interface WebService {
	public String getUsername();
	public UserRole getUserRole();
	public LoginCred getLoginCred();
	public void notifyNetwork(Service service);
}
