package webClient;

import interfaces.Command;
import interfaces.WebService;
import data.Service;

public class WebAdapter implements Command {
	private Service service;
	private WebService web;
	
	public WebAdapter(Service service, WebService web) {
		this.service = service;
		this.web = web;
	}
	
	public void execute() {
		System.out.println("[WebAdapter:execute()] " + service.getName());
		
		service.executeWeb();
	}
}
