package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import data.Message;
import data.Message.MessageType;

public class NetworkDriver extends Thread {
	// private static final Integer MAX_POOL_THREADS = 5;

	private NetworkImpl									network;

	private boolean										running;
	private Selector									selector;
	private ServerSocketChannel							serverSocketChannel;
	private ArrayList<ServerSocketChannel>				serverChannels;
	private ArrayList<SocketChannel>					socketChannels;

	// private static ExecutorService pool = Executors.newCachedThreadPool();
	private ByteBuffer									rBuffer	= ByteBuffer.allocate(8192);
	private ByteBuffer									wBuffer	= ByteBuffer.allocate(8192);

	private Hashtable<SelectionKey, ArrayList<byte[]>>	writeBuffers;
	private Hashtable<SelectionKey, byte[]>				readBuffers;

	private LinkedList<ChangeRequest>					changeRequestQueue;

	public NetworkDriver(NetworkImpl network) {
		this.network = network;

		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(null);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			System.out.println("[NetworkDriver] Linstening on " + serverSocketChannel.socket().getLocalSocketAddress());

			serverChannels = new ArrayList<ServerSocketChannel>();
			socketChannels = new ArrayList<SocketChannel>();

			rBuffer = ByteBuffer.allocate(8192);
			wBuffer = ByteBuffer.allocate(8192);

			writeBuffers = new Hashtable<SelectionKey, ArrayList<byte[]>>();
			readBuffers = new Hashtable<SelectionKey, byte[]>();
			changeRequestQueue = new LinkedList<ChangeRequest>();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InetSocketAddress getAddress() {
		if (serverSocketChannel == null) {
			return null;
		}

		ServerSocket socket = serverSocketChannel.socket();
		return new InetSocketAddress(socket.getInetAddress(), socket.getLocalPort());
	}

	public synchronized void startRunning() {
		this.running = true;
		this.start();
	}

	protected void accept(SelectionKey key) {
		System.out.println("[NetworkDriver: accept] Begin");

		SocketChannel socketChannel;
		ServerSocketChannel serverSocketChannel;
		Message message;

		System.out.println("[NetworkDriver: accept] Accept a connection");
		try {
			serverSocketChannel = (ServerSocketChannel) key.channel();
			socketChannel = serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			socketChannels.add(socketChannel);

			System.out.println("[NetworkDriver: accept] Done");

			// /* Check if we know who is at the other end of the connection */
			// if (network.getUserKeyMap().containsKey(key)) {
			// String username = null;
			// for (String user : network.getUserKeyMap().keySet()) {
			// if (network.getUserKeyMap().get(user).equals(key)) {
			// username = user;
			// break;
			// }
			// }
			//
			// ConcurrentHashMap<String, ArrayList<Message>> unsentMessages =
			// network.getUserUnsentMessages();
			// if (unsentMessages.contains(username)) {
			// network.getSendEvents().enqueue(key,
			// unsentMessages.get(username));
			// }
			// } else {
			// // TODO : Make & send an username request
			// System.err.println("[NetworkDriver: connect] Something wrong went ...");
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[NetworkDriver: accept] End");

	}

	private void appendMessage(Message message, SelectionKey key) {
		ConcurrentHashMap<String, SocketChannel> userChanelMap;
		NetworkReceiveEvents networkEvents = network.getEventsTask();

		userChanelMap = network.getUserChanelMap();

		if (message.getType() == MessageType.SEND_USERNAME) {
			String username = message.getSource();
			System.out.println("[NetworkDriver: appendMessage] SEND_USERNAME received from " + username);
			userChanelMap.putIfAbsent(username, (SocketChannel) key.channel());

			// TODO : Seems to be an impossible case
			// ConcurrentHashMap<String, ArrayList<Message>> unsentMessages =
			// network.getUserUnsentMessages();
			// System.out.println("[NetworkDriver, appendMessages] Unsent messages : "
			// + unsentMessages);
			// if (unsentMessages.contains(username)) {
			// System.out.println("[NetworkDriver, appendMessages] Enqueue some messages to send queue");
			// network.getSendEvents().enqueue(key,
			// unsentMessages.get(username));
			// }

			return;
		}

		networkEvents.enqueue(key, message);
	}

	private static Integer byteArrayToInt(byte[] array) {
		Integer result = 0;

		if (array.length != Integer.SIZE / Byte.SIZE) {
			System.err.println("Integer size = 4, no more, no less");
			return 0;
		}

		for (int i = 0; i < array.length; i++) {
			result <<= Byte.SIZE;
			result |= array[i];
		}

		return result;
	}

	private void read(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		this.rBuffer.clear();

		int numRead;

		try {
			numRead = socketChannel.read(this.rBuffer);
		} catch (Exception e) {
			numRead = -1000000000;
		}

		if (numRead <= 0) {
			System.out.println("[NetworkDriver: read] Socket closed for " + key);
			key.channel().close();
			key.cancel();
			return;
		}

		byte[] rbuf = null;
		rbuf = this.readBuffers.get(key);

		int rbuflen = 0;
		if (rbuf != null) {
			rbuflen = rbuf.length;
		}

		byte[] currentBuf = this.rBuffer.array();
		System.out.println("[NetworkDriver: read] Were read " + numRead + " bytes from the socket associated yo key "
				+ key + " : " + currentBuf);

		byte[] newBuf = new byte[rbuflen + numRead];

		// Copiaza datele primite in newBuf (rbuf sunt datele primite
		// anterior si care nu formeaza o cerere completa, iar currentBuf
		// contine datele primite la read-ul curent.
		for (int i = 0; i < rbuflen; i++) {
			newBuf[i] = rbuf[i];
		}

		for (int i = rbuflen; i < newBuf.length; i++) {
			newBuf[i] = currentBuf[i - rbuflen];
		}

		int i = 0;
		int length = 0;

		// Citire dimensiune cerere
		if (i + 4 >= newBuf.length) {
			return;
		}

		// length = ((128 + (int) newBuf[i]) << 24) + ((128 + (int) newBuf[i +
		// 1]) << 16)
		// + ((128 + (int) newBuf[i + 2]) << 8) + (128 + (int) newBuf[i + 3]);
		length = byteArrayToInt(Arrays.copyOfRange(newBuf, i, i + 4));
		System.out.println("[NetworkDriver: read] Request length = " + length);
		i += 4;

		System.out.println("[NetworkDriver: read] " + (i + length) + " <= " + newBuf.length);

		/* Read serialized object */
		if (i + length <= newBuf.length) {
			i -= 4;
			while(true){
				if (i + 4 >= newBuf.length) {
					break;
				}
				
				/* Get length */
				length = byteArrayToInt(Arrays.copyOfRange(newBuf, i, i + 4));
				if(i + length + 4 > newBuf.length){
					break;
				}
				
				Message message = new Message(Arrays.copyOfRange(newBuf, i, length + i + 4));

				System.out.println("[NetworkDriver: read] Message received : " + message);
				appendMessage(message, key);
				
				i += 4 + length;

			}
		} else {
			i -= 4;
		}

		byte[] finalBuf = null;
		if (i > 0) {
			finalBuf = new byte[newBuf.length - i];
			for (int j = i; j < newBuf.length; j++) {
				finalBuf[j - i] = newBuf[j];
			}
		} else {
			finalBuf = newBuf;
		}

		this.readBuffers.put(key, finalBuf);
	}

	protected void write(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		ArrayList<byte[]> wbuf = null;

		synchronized (key) {
			wbuf = this.writeBuffers.get(key);

			while (wbuf.size() > 0) {
				byte[] bbuf = wbuf.get(0);
				wbuf.remove(0);

				this.wBuffer.clear();
				this.wBuffer.put(bbuf);
				this.wBuffer.flip();

				int numWritten = socketChannel.write(this.wBuffer);
				System.out.println("[NetworkDriver: write] Am scris " + numWritten
						+ " bytes pe socket-ul asociat cheii " + key);

				if (numWritten < bbuf.length) {
					byte[] newBuf = new byte[bbuf.length - numWritten];

					// Copiaza datele inca nescrise din bbuf in newBuf.
					for (int i = numWritten; i < bbuf.length; i++) {
						newBuf[i - numWritten] = bbuf[i];
					}

					wbuf.add(0, newBuf);
					break;
				}
			}

			if (wbuf.size() == 0) {
				// synchronized (changeRequestQueue) {
				// // this.changeRequestQueue.add(new ChangeRequest(key,
				// // key.interestOps() | SelectionKey.OP_WRITE));
				// this.changeRequestQueue.add(new ChangeRequest(socketChannel,
				// ChangeRequest.CHANGEOPS,
				// SelectionKey.OP_WRITE));
				// }
				key.interestOps(SelectionKey.OP_READ);
			}
		}
	}

	public void sendData(Message message, String username, InetSocketAddress address) {
		System.out.println("[NetworkDriver, sendData()] Begin");
		ConcurrentHashMap<String, SocketChannel> userChanelMap;
		ConcurrentHashMap<String, ArrayList<Message>> userUnsentMessages;

		userChanelMap = network.getUserChanelMap();
		userUnsentMessages = network.getUserUnsentMessages();

		if (!userChanelMap.containsKey(message.getDestination())) {
			System.out.println("[NetworkDriver: sendData] Initiate a new connection with " + message.getDestination());
			/* Initiate a new connection and save all messages */
			initiateConnect(address, username);
			userUnsentMessages.putIfAbsent(message.getDestination(), new ArrayList<Message>());
			userUnsentMessages.get(message.getDestination()).add(message);
		} else {
			sendData(message, message.getDestination());
		}
	}

	public void sendData(Message message, String username) {
		sendData(network.getUserChanelMap().get(username).keyFor(selector), message.serialize());
	}

	public void sendData(Message message, SelectionKey key) {
		sendData(key, message.serialize());
	}
	
	public void sendData(Message message, SocketChannel chanel) {
		sendData(message, chanel.keyFor(selector));
	}

	public void sendData(SelectionKey key, byte[] data) {
		System.out.println("[NetworkDriver: sendData] Se doreste scrierea a " + data.length
				+ " bytes pe socket-ul asociat cheii " + key);

		ArrayList<byte[]> wbuf = null;

		synchronized (key) {
			wbuf = this.writeBuffers.get(key);
			if (wbuf == null) {
				wbuf = new ArrayList<byte[]>();
				this.writeBuffers.put(key, wbuf);
			}

			wbuf.add(data);
			synchronized (changeRequestQueue) {
				// this.changeRequestQueue.add(new ChangeRequest(key,
				// SelectionKey.OP_READ | SelectionKey.OP_WRITE));
				this.changeRequestQueue.add(new ChangeRequest((SocketChannel) key.channel(), ChangeRequest.CHANGEOPS,
						SelectionKey.OP_WRITE));
			}
		}

		this.selector.wakeup();
	}

	public boolean initiateConnect(InetSocketAddress destination, String username) {
		SocketChannel socketChannel = null;
		Boolean bRet;

		System.out.println("[NetworkDriver: initiateConnect] Begin");
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			System.out.println("[NetworkDriver: initiateConnect] Connect to : " + destination + " " + username);
			bRet = socketChannel.connect(destination);

			if (bRet) {
				System.out.println("[NetworkDriver: initiateConnect] Connection established");
			} else {
				System.out.println("[NetworkDriver: initiateConnect] Connection will be finish later");
			}

			System.out.println("Map : " + network.getUserChanelMap());
			System.out.println("Username : " + username);
			System.out.println("Key : " + socketChannel.keyFor(selector));
			network.getUserChanelMap().putIfAbsent(username, socketChannel);
			System.out.println("[NetworkDriver: initiateConnect] Before registering new interest");
			// socketChannel.register(selector, SelectionKey.OP_CONNECT);

			// Queue a channel registration since the caller is not the
			// selecting thread. As part of the registration we'll register
			// an interest in connection events. These are raised when a channel
			// is ready to complete connection establishment.
			synchronized (changeRequestQueue) {
				changeRequestQueue
						.add(new ChangeRequest(socketChannel, ChangeRequest.REGISTER, SelectionKey.OP_CONNECT));
			}

			System.out.println("[NetworkDriver: initiateConnect] Before wakeup");
			selector.wakeup();
			System.out.println("[NetworkDriver: initiateConnect] Wakeup was sent");

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private void finishConnection(SelectionKey key) {

		SocketChannel socketChannel = (SocketChannel) key.channel();
		try {
			socketChannel.finishConnect();
		} catch (Exception e) {
			System.err.println("[NetworkDriver: connect] ERROR: finishConnect");
			e.printStackTrace();
			key.cancel();
			return;
		}

		System.out.println("[NetworkDriver: connect] Connection finished");
		key.interestOps(SelectionKey.OP_WRITE);

		Message message = new Message();
		message.setType(MessageType.SEND_USERNAME);
		message.setSource(network.getUserProfile().getUsername());

		sendData(key, message.serialize());

		System.out.println("[NetworkDriver: finishConnect] Chanel = " + key.channel());
		System.out.println("[NetworkDriver: finishConnect] KeyMap = " + network.getUserChanelMap());
		/* Check if we know who is at the other end of the connection */
		if (network.getUserChanelMap().containsValue(key.channel())) {
			String username = null;
			for (String user : network.getUserChanelMap().keySet()) {
				if (network.getUserChanelMap().get(user).equals(key.channel())) {
					username = user;
					break;
				}
			}

			/* Send all pending messages */
			ConcurrentHashMap<String, ArrayList<Message>> unsentMessages = network.getUserUnsentMessages();
			System.out.println("[NetworkDriver: finishConnect] Unsent messages list = " + unsentMessages);
			System.out.println("[NetworkDriver: finishConnect] Username : " + username);
			if (unsentMessages.containsKey(username)) {
				System.out.println("[NetworkDriver: finishConnect] Send pending messages");
				network.getSendEvents().enqueue(socketChannel, unsentMessages.get(username));
			}
		} else {
			// TODO : Make & send an username request
			System.err.println("[NetworkDriver: finishConnect] Something wrong went ...");
		}
	}

	public void run() {
		ChangeRequest creq;

		try {
			while (isRunning()) {

				synchronized (changeRequestQueue) {

					Iterator changes = this.changeRequestQueue.iterator();
					while (changes.hasNext()) {
						ChangeRequest change = (ChangeRequest) changes.next();
						switch (change.type) {
						case ChangeRequest.CHANGEOPS:
							System.out.println("[NetworkDriver, run] CHANGEOPS = " + change.ops);
							SelectionKey key = change.socket.keyFor(selector);
							key.interestOps(change.ops);
							break;
						case ChangeRequest.REGISTER:
							System.out.println("[NetworkDriver, run] REGISTER = " + change.ops);
							change.socket.register(selector, change.ops);
							break;
						}
					}
					this.changeRequestQueue.clear();
				}

				// System.out.println("[NetworkDriver: run] Listening on " +
				// getAddress().getPort());
				selector.select();

				// System.out.println("[NetworkDriver: run]  After select");

				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext();) {
					SelectionKey key = it.next();
					it.remove();

					// System.out.println("[NetworkDriver: run]  for's body");

					if (!key.isValid()) {
						System.out.println("[NetworkDriver, run] Key isn't valid");
						continue;
					}

					if (key.isAcceptable()) {
						System.out.println("[NetworkDriver, run] accept");
						accept(key);
					} else if (key.isReadable()) {
						System.out.println("[NetworkDriver, run] read");
						read(key);
					} else if (key.isWritable()) {
						System.out.println("[NetworkDriver, run] write");
						write(key);
					} else if (key.isConnectable()) {
						System.out.println("[NetworkDriver, run] connect");
						finishConnection(key);
					}
				}

//				synchronized (changeRequestQueue) {
//					// while ((creq = this.changeRequestQueue.poll()) != null) {
//					// System.out.println("[NIOTCPServer] Schimb operatiile cheii "
//					// + creq.key + " la " + creq.newOps);
//					// creq.key.interestOps(creq.newOps);
//					// }
//
//					Iterator changes = this.changeRequestQueue.iterator();
//					while (changes.hasNext()) {
//						ChangeRequest change = (ChangeRequest) changes.next();
//						switch (change.type) {
//						case ChangeRequest.CHANGEOPS:
//							SelectionKey key = change.socket.keyFor(this.selector);
//							key.interestOps(change.ops);
//							break;
//						case ChangeRequest.REGISTER:
//							change.socket.register(this.selector, change.ops);
//							System.out.println("[NetworkDriver: run] ChangeRequest.REGISTER");
//							break;
//						}
//					}
//					this.changeRequestQueue.clear();
//				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (selector != null) {
					selector.close();
				}

				for (ServerSocketChannel ssc : serverChannels) {
					ssc.close();
				}

				for (SocketChannel schan : socketChannels) {
					schan.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void stopRunning() {
		this.running = false;
		this.selector.wakeup();
	}

	protected synchronized boolean isRunning() {
		return this.running;
	}
}
