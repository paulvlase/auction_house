package webServiceClient;

import interfaces.Command;
import data.Service;

public class WebAdapter implements Command {
	private Service service;
	
	public WebAdapter(Service service) {
		this.service = service;
	}
	
	public void execute() {
		System.out.println("Execut in adaptor service = " + service.getName());
		
		service.executeWeb();
		System.out.println("Aici nu ajung ?");
	}
}
