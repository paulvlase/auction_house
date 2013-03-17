package interfaces;

import config.GlobalConfig.UserType;

/**
 * Gui interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface Gui {
	public void login();
	public void signIn(String username, String password, UserType type);
	public List<Service> loadDemandsFile();
	public List<Service> loadSuppliesFile();
}
