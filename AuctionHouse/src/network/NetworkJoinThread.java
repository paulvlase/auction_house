package network;

import interfaces.MediatorNetwork;

import java.util.Hashtable;
import java.util.Map;

import data.Service;

public class NetworkJoinThread extends Thread {
	MediatorNetwork med;
	Hashtable<String, NetworkTransferTask> tasks;
	Object monitor;

	public NetworkJoinThread(MediatorNetwork med) {
		this.med = med;
		tasks = new Hashtable<String, NetworkTransferTask>();
		monitor = new Object();
	}

	public void run() {
		while (true) {
			try {
				synchronized(monitor) {
					monitor.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			makeJoin();
		}
	}
	
	private synchronized void makeJoin() {
		for (Map.Entry<String, NetworkTransferTask> entry: tasks.entrySet()) {
			NetworkTransferTask task = entry.getValue();
			try {
				task.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		tasks.clear();
	}
	
	public synchronized void registerJoin(NetworkTransferTask task) {
		tasks.put(task.getServiceName(), task);
	}
	
	public Object getMonitor() {
		return monitor;
	}
	
	public synchronized void stopTask(Service service) {
		NetworkTransferTask task = tasks.get(service.getName());
		
		task.cancel(false);
		tasks.remove(service.getName());
		
	}
}
