package states;

import java.io.Serializable;
import java.util.ArrayList;

import data.Message;


import interfaces.NetworkService;
import interfaces.WebService;

public interface State extends Serializable {
	public void executeNet(NetworkService net);
	public void executeWeb(WebService web);

	public String getName();
	public ArrayList<Message> asMessages();
}
