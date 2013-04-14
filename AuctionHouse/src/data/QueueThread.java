package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public abstract class QueueThread<K, T> extends Thread {

	private Object									monitor;
	protected ConcurrentHashMap<K, ArrayList<T>>	queue;
	private Boolean									running;
	private String threadName;

	public QueueThread(String threadName) {
		this.threadName = threadName;
	}
	
	public QueueThread(){
		this("");
	}

	@Override
	public void run() {
		System.out.println("[" + threadName + "] Start running");
		running = true;

		while (running) {
			try {
				synchronized (monitor) {
					monitor.wait();
				}
				if (!running) {
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process();
		}
	}

	public boolean haveToProcess() {
		if (queue == null) {
			return false;
		}

		if (queue.elements().hasMoreElements()) {
			return true;
		}

		return false;
	}

	protected abstract void process();

	public void enqueue(K key, T value) {
		queue.putIfAbsent(key, new ArrayList<T>());
		queue.get(key).add(value);

		synchronized (monitor) {
			monitor.notify();
		}
	}

	public void enqueue(K key, Collection<T> values){
		queue.putIfAbsent(key, new ArrayList<T>());
		queue.get(key).addAll(values);

		synchronized (monitor) {
			monitor.notify();
		}
	}
	
	public synchronized void stopRunning() {
		running = false;

		System.out.println("[" + threadName + "] Stop running");
		
		synchronized (monitor) {
			monitor.notify();
		}
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
}
