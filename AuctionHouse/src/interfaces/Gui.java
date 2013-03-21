package interfaces;

import data.LoginCred;

/**
 * Gui interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface Gui {
	public void login();
	public void signIn(LoginCred cred);
	public void signOut();
}
