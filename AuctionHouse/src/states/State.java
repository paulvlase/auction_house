package states;

import java.util.ArrayList;

import network.Message;

import interfaces.NetworkService;
import interfaces.WebService;

public interface State {
	public void executeNet(NetworkService net);
	public void executeWeb(WebService web);

	public String getName();
	public ArrayList<Message> asMessages();
}
