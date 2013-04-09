package states;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

public interface State {
	public void executeNet(MediatorNetwork mednet);
	public void executeWeb(MediatorWeb medweb);

	public String getName();
}
