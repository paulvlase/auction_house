package states;

import java.util.ArrayList;

import network.Message;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

public interface State {
	public void executeNet(MediatorNetwork mednet);
	public void executeWeb(MediatorWeb medweb);

	public String getName();
	public ArrayList<Message> asMessages();
}
