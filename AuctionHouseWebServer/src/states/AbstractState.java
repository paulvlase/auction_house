package states;

import interfaces.NetworkService;
import interfaces.WebService;

import java.util.ArrayList;

import data.Message;
import data.Service;

public abstract class AbstractState implements State {
	protected static final long	serialVersionUID	= 1L;
	protected Service			service;

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public void updateState() {
	}

	@Override
	public abstract void executeNet(NetworkService net);

	@Override
	public abstract void executeWeb(WebService web);

	@Override
	public abstract String getName();

	@Override
	public abstract State clone();
}
