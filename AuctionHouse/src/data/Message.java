package data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class Message implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(Message.class);

	private MessageType			type;
	private String				serviceName;

	private String				username;

	private String				source;
	private String				destination;
	private Object				payload;

	/* Used for TRANSFER_* */
	private Integer				progress;

	public enum MessageType {
		LAUNCH, LAUNCH_RESPONSE, ACCEPT, REFUSE, REMOVE, GET_USERNAME, SEND_USERNAME, MAKE_OFFER, OFFER_EXCEED, TRANSFER_STARTED, TRANSFER_CHUNCK, TRANSFER_PROGRESS, LOGOUT;
	}

	public Message() {
	}

	public Message(MessageType type, String serviceName) {
		this.type = type;
		this.serviceName = serviceName;
	}

	public Message(byte[] array) {
		deserialize(array);
	}

	public byte[] serialize() {
		byte[] result;
		byte[] resultSize;
		byte[] object;

		System.out.println("[Message: serialize] Message : " + this);
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = null;
		try {
			o = new ObjectOutputStream(b);
			o.writeObject(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			o.close();
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* Object representation */
		object = b.toByteArray();
		
		result = new byte[b.size() + Integer.SIZE / Byte.SIZE];
		resultSize = intToByteArray(b.size());
		System.out.println("[Message: serialize] Message length as array = " + Arrays.toString(resultSize));
		System.out.println("[Message: serialize] Message length = " + b.size());

		/* Package length */
		for (int i = 0; i < resultSize.length; i++) {
			result[i] = resultSize[i];
		}

		for (int i = 0; i < object.length; i++) {
			result[i + Integer.SIZE / Byte.SIZE] = object[i];
		}
		
		return result;
	}

	private void deserialize(byte[] array) {
		ByteArrayInputStream b;
		ObjectInputStream o;
		Object obj = null;
		byte object[];

		if (array.length <= 4) {
			System.err.println("Corrupted package ...");
			return;
		}

		object = new byte[array.length - Integer.SIZE / Byte.SIZE];

		/* Skip first 4 bytes [package size] */
		for (int i = 0; i < object.length; i++) {
			object[i] = array[i + Integer.SIZE / Byte.SIZE];
		}

		b = new ByteArrayInputStream(object);
		try {
			o = new ObjectInputStream(b);
			obj = o.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (!(obj instanceof Message)) {
			System.out.println("Unknown class :| " + obj.getClass());
		}

		/* Object clone */
		this.type = ((Message) obj).getType();
		this.serviceName = ((Message) obj).getServiceName();
		this.source = ((Message) obj).getSource();
		this.destination = ((Message) obj).getDestination();
		this.payload = ((Message) obj).getPayload();
		this.progress = ((Message) obj).getProgress();
		this.username = ((Message) obj).getUsername();
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	private static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (((value >> (3 * Byte.SIZE)) % 256) - 128),
				(byte) (((value >> (2 * Byte.SIZE)) % 256) - 128), (byte) (((value >> Byte.SIZE) % 256) - 128),
				(byte) ((value % 256) - 128) };
	}

	public ArrayList<Message> asArrayList() {
		ArrayList<Message> list = new ArrayList<Message>();
		list.add(this);
		return list;
	}

	@Override
	public String toString() {
		return "type : " + type + ", serviceName : " + serviceName + ", source : " + source + ", destination : "
				+ destination + ", username : " + getUsername() + ", progress : " + getProgress() + ", payload : "
				+ Arrays.asList(payload);
	}

	public static void main(String[] args) {
		System.out.println(new Message(new Message(MessageType.LAUNCH, "service1").serialize()));
	}
}
