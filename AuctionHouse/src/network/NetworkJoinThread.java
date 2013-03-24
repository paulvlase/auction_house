package network;

import interfaces.MediatorNetwork;

import java.util.Hashtable;
import java.util.Map;

public class NetworkJoinThread extends Thread {
	MediatorNetwork med;
	Hashtable<String, NetworkTask> tasks;
	Object monitor;

	public NetworkJoinThread(MediatorNetwork med) {
		this.med = med;
		tasks = new Hashtable<String, NetworkTask>();
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
		for (Map.Entry<String, NetworkTask> entry: tasks.entrySet()) {
			NetworkTask task = entry.getValue();
			try {
				task.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		tasks.clear();
	}
	
	public synchronized void registerJoin(NetworkTask task) {
		tasks.put(task.getServiceName(), task);
	}
	
	public Object getMonitor() {
		return monitor;
	}
}
