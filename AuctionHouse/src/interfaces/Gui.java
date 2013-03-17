package interfaces;

import config.GlobalConfig.UserType;

/**
 * Gui interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface Gui {
	public void signIn(String username, String password, UserType type);
}
