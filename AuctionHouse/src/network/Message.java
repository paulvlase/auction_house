package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private MessageType type;
	private String serviceName;

	private String username;

	/**
	 * User name of source / destination
	 */
	private String peer;
	private Object payload;

	public enum MessageType {
		LAUNCH, ACCEPT, REFUSE, GET_USERNAME, SEND_USERNAME, MAKE_OFFER, TRANSFER_SIZE, TRANSFER_CHUNCK;
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

		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o;
		try {
			o = new ObjectOutputStream(b);
			o.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* Object representation */
		object = b.toByteArray();

		result = new byte[b.size() + Integer.SIZE / Byte.SIZE];
		resultSize = intToByteArray(result.length);

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
		this.peer = ((Message) obj).getPeer();
		this.payload = ((Message) obj).getPayload();
	}

	public String getPeer() {
		return peer;
	}

	public void setPeer(String peer) {
		this.peer = peer;
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

	private static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> (3 * Byte.SIZE)),
				(byte) (value >>> (2 * Byte.SIZE)),
				(byte) (value >>> Byte.SIZE), (byte) value };
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

	public ArrayList<Message> asArrayList() {
		ArrayList<Message> list = new ArrayList<Message>();
		list.add(this);
		return list;
	}

	@Override
	public String toString() {
		return "type : " + type + ", serviceName : " + serviceName
				+ ", peer : " + peer + ", payload : " + Arrays.asList(payload);
	}

	public static void main(String[] args) {
		System.out.println(new Message(new Message(MessageType.LAUNCH,
				"service1").serialize()));
	}
}
