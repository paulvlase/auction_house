package network;

import interfaces.MediatorNetwork;

import java.util.List;

import javax.swing.SwingWorker;

import data.Service;
import data.Service.Status;

public class NetworkTask extends SwingWorker<Service, Service> {
	private MediatorNetwork med;
	private NetworkJoinThread joinThread;
	private Service service;
	
	private final int DELAY = 100;
	private final int COUNT = 100;

	public NetworkTask(MediatorNetwork med, NetworkJoinThread joinThread, Service service) {
		this.med = med;
		this.joinThread = joinThread;
		this.service = service;
		
		joinThread.registerJoin(this);
		
		service.setStatus(Status.TRANSFER_IN_PROGRESS);
	}
	
	public String getServiceName() {
		return service.getName();
	}

	@Override
	protected Service doInBackground() throws Exception {		
		int i = 0;
		try {
			while (i < COUNT) {
				i++;
				Thread.sleep(DELAY);
				service.setProgress(i);

				publish(service);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return service;
	}

	protected void process(List<Service> services) {
		for (Service service:  services) {
			med.changeServiceNotify(service);
		}
	}

	@Override
	protected void done() {
		System.out.println(Thread.currentThread());
		if (isCancelled()) {
			System.out.println("Cancelled !");
			
			service.setProgress(-1);
			service.setStatus(Status.TRANSFER_FAILED);
		} else {
			System.out.println("Done !");
			
			service.setProgress(COUNT + 1);
			service.setStatus(Status.TRANSFER_COMPLETE);
		}
		
		med.changeServiceNotify(service);
		
		synchronized (joinThread.getMonitor()) {
			joinThread.getMonitor().notify();
		}
	}
}