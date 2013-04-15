package states;

import java.io.Serializable;
import java.util.ArrayList;

import data.Message;
import data.Service;

import interfaces.NetworkService;
import interfaces.WebService;

public interface State extends Serializable {
	public Service getService();

	public void setService(Service service);

	public void executeNet(NetworkService net);

	public void executeWeb(WebService web);

	public String getName();

	public ArrayList<Message> asMessages(NetworkService net);

	public State clone();
}
