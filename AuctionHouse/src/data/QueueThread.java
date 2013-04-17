package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public abstract class QueueThread<K, T> extends Thread {
	private static Logger logger = Logger.getLogger(QueueThread.class);

	private Object									monitor;
	protected ConcurrentHashMap<K, ArrayList<T>>	queue;
	private Boolean									running;
	private String									threadName;

	public QueueThread(String threadName) {
//		 logger.setLevel(Level.OFF);

		this.monitor = new Object();
		this.threadName = threadName;
		queue = new ConcurrentHashMap<K, ArrayList<T>>();
	}

	public QueueThread() {
		this("");
	}

	public Map.Entry<K, T> getJob() {
//		Iterator<Entry<K, ArrayList<T>>> it = queue.entrySet().iterator();
//
//		if (!it.hasNext()) {
//			return null;
//		}
//
//		Map.Entry<K, ArrayList<T>> entry = it.next();
//		K key = entry.getKey();
//		T value = entry.getValue().get(0);
//
//		System.out.println("[QueueThread : getJob] Before " + queue);
//		queue.get(key).remove(0);
//		if (queue.get(key).size() == 0) {
//			queue.remove(key);
//		}
//		System.out.println("[QueueThread : getJob] After " + queue);
//
//		return new Pair<K, T>(key, value);
		return getRandomJob();
	}

	public Map.Entry<K, T> getRandomJob() {
		@SuppressWarnings("unchecked")
		K[] keys = (K[]) queue.keySet().toArray();
		Random random = new Random();
		Integer keyIndex;

		System.out.println("[" + threadName + ": getRandomJob] Empty queue");
		if (keys.length == 0) {
			return null;
		}

		/* Peek a random key */
		keyIndex = random.nextInt(keys.length);
		K key = keys[keyIndex];
		T value = queue.get(key).get(0);

		System.out.println("[" + threadName + ": getRandomJob] Before " + queue);
		queue.get(key).remove(0);
		if (queue.get(key).size() == 0) {
			queue.remove(key);
		}
		System.out.println("[" + threadName + ": getRandomJob] After " + queue);

		return new Pair<K, T>(key, value);
	}

	@Override
	public void run() {
		System.out.println("[" + threadName + "] Start running");
		running = true;

		while (running) {
			try {
				synchronized (monitor) {
					if(queue.isEmpty()){
						monitor.wait();
					}
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

	public synchronized boolean haveToProcess() {
		if (queue == null) {
			return false;
		}

		if (queue.elements().hasMoreElements()) {
			return true;
		}

		return false;
	}

	protected abstract void process();

	public synchronized void enqueue(K key, T value) {
		System.out.println("[" + threadName + ": enqueue] Before " + value);
		System.out.println("[" + threadName + ": enqueue] Before " + key);
		System.out.println("[" + threadName + ": enqueue] Running " + running);
		
		if (key == null) {
			return;
		}
		
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
	
	public synchronized void clear(){
		queue.clear();
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	public void removeKey(K key){
		if(queue == null){
			return;
		}
		
		queue.remove(key);
	}
	
	public void removeKey(K key, T value) {
		if (queue == null) {
			return;
		}

		queue.get(key).remove(value);
	}

	public ArrayList<T> getJobs(K key) {
		if (queue == null) {
			return null;
		}

		return queue.get(key);
	}
}
