package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public abstract class QueueThread<K, T> extends Thread {

	private Object									monitor;
	protected ConcurrentHashMap<K, ArrayList<T>>	queue;
	private Boolean									running;
	private String									threadName;

	public QueueThread(String threadName) {
		this.monitor = new Object();
		this.threadName = threadName;
		queue = new ConcurrentHashMap<K, ArrayList<T>>();
	}

	public QueueThread() {
		this("");
	}

	public Map.Entry<K, T> getJob() {
		Iterator<Entry<K, ArrayList<T>>> it = queue.entrySet().iterator();

		if (!it.hasNext()) {
			return null;
		}

		Map.Entry<K, ArrayList<T>> entry = it.next();
		K key = entry.getKey();
		T value = entry.getValue().get(0);

		queue.get(key).remove(0);
		if (queue.get(key).size() == 0) {
			queue.remove(key);
		}

		return new Pair<K, T>(key, value);
	}
	
	public Map.Entry<K, T> getRandomJob() {
		System.err.println("Unimplemented method");
		return null;
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

	public void enqueue(K key, Collection<T> values) {
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
