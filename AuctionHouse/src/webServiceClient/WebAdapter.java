package webServiceClient;

import interfaces.Command;
import data.Service;

public class WebAdapter implements Command {
	private Service service;
	
	public WebAdapter(Service service) {
		this.service = service;
	}
	
	public void execute() {
		System.out.println("[WebAdapter:execute()] " + service.getName());
		
		service.executeWeb();
	}
}
