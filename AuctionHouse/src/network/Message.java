package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Array;
import java.util.Arrays;

import data.Service;

/**
 * Used for tranfers over network between clients and sellers.
 * 
 * --------------------------------------------- | size | MessageType |
 * ServiceName | [Data] | ---------------------------------------------
 * 
 * ------------------------------------------------ | Int | Int | char+ \0 |
 * [payload] | ------------------------------------------------
 * 
 * LAUNCH : size, LAUNCH, serviceName ACCEPT : size, ACCEPT, serviceName REFUSE
 * : size, REFUSE, serviceName DROP : size, DROP, serviceName TRANSFER_SIZE :
 * size, TRANSFER_SIZE, serviceName TRANSFER_CHUNK : size, TRANSFER_CHUNK,
 * serviceName, payload
 * 
 * It's recommended to fill buffer only once, from the leftmost member (above
 * figure) to the rightmost, otherwise unpredictable results can occur.
 * 
 * @author Ghennadi Procopciuc
 */
public class Message {
	private ByteArrayOutputStream outputStream;
	private String destination;

	private enum MessageType {
		LAUNCH, ACCEPT, REFUSE, DROP, TRANSFER_SIZE, TRANSFER_CHUNCK;

		public static MessageType getByOrdinal(Integer order) {
			MessageType[] values = MessageType.values();

			return values[order];
		}
	}

	public Message(MessageType type, String destination) {
		outputStream = new ByteArrayOutputStream();
		this.destination = destination;

		outputStream.write(intToByteArray(type.ordinal()), 0, 4);
	}

	public MessageType getMessageType() {
		Integer typeId;
		MessageType type;
		byte array[];

		array = outputStream.toByteArray();

		if (array.length < Integer.SIZE / Byte.SIZE) {
			System.err
					.println("Buffers seems to be uninitialized or messageType wasn't set");
			return null;
		}

		typeId = byteArrayToInt(Arrays.copyOfRange(array, 0, Integer.SIZE
				/ Byte.SIZE));

		return MessageType.getByOrdinal(typeId);
	}

	public void setServiceName(String serviceName) {
		if (outputStream.size() < Integer.SIZE / Byte.SIZE) {
			System.err.println("Type of message was not set");
			return;
		}

		outputStream.write(serviceName.getBytes(), 0, serviceName.length());
		outputStream.write(new byte[] { '\0' }, 0, 1);
	}

	/**
	 * Gets serviceName. Before calling this method make sure that your buffer
	 * contains <code>serviceName</code> otherwise results may be unpredictable.
	 * 
	 * @return Service name
	 */
	public String getServiceName() {
		String serviceName = null;
		byte[] array;
		Integer endOffset = 0;

		if (outputStream.size() <= Integer.SIZE / Byte.SIZE) {
			System.err.println("Service name field isn't completed yet");
			return null;
		}

		array = outputStream.toByteArray();
		for (int i = Integer.SIZE / Byte.SIZE; i < array.length; i++) {
			if (array[i] == (byte) '\0') {
				endOffset = i;
				break;
			}
		}

		if (endOffset == 0) {
			System.err.println("Can't find end of serviceName");
			return null;
		}

		return new String(Arrays.copyOfRange(array, Integer.SIZE / Byte.SIZE, endOffset));
	}

	/**
	 * Sets buffer payload. Warning ! You should call this method once per
	 * buffer, otherwise new payload will be appended to current content. Before
	 * calling this function, make sure that your buffer has filled all fields
	 * before <code>payload</code>. Usually <code>setSize</code> is called
	 * after.
	 * 
	 * @param payload
	 *            Payload that will be appended to buffer
	 */
	public void setPayload(byte[] payload) {
		if (outputStream.size() <= Integer.SIZE / Byte.SIZE) {
			System.err.println("Type of message or service name was not added");
			return;
		}

		try {
			outputStream.write(payload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] getPayload() {
		byte[] result = null;
		byte[] array;
		String serviceName = getServiceName();

		/* Sanity check */
		if (serviceName == null) {
			return null;
		}

		array = outputStream.toByteArray();

		if (array.length <= Integer.SIZE / Byte.SIZE + serviceName.length()) {
			System.err
					.println("This buffer not contains a payload, it's to short");
			return null;
		}

		result = Arrays.copyOfRange(array, Integer.SIZE / Byte.SIZE
				+ serviceName.length() + 1, array.length);
		return result;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
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

	@Override
	public String toString() {
		String result = "";
		byte[] array = outputStream.toByteArray();
		String serviceName = null;

		result += "{size : " + outputStream.size();

		if (array.length >= Integer.SIZE / Byte.SIZE) {
			result += ", type : " + getMessageType();
		}

		if (array.length > Integer.SIZE / Byte.SIZE) {
			serviceName = getServiceName();
			result += ", name : " + serviceName;
		}

		if (serviceName != null) {
			if (array.length > Integer.SIZE / Byte.SIZE + serviceName.length()) {
				result += ", payload : " + Arrays.toString(getPayload());
			}
		}

		result += "}";

		result += " to " + getDestination();

		return result;
	}

	public static void main(String[] args) {
		// System.out.println("Integer size = " + Integer.SIZE / Byte.SIZE);
		// System.out.println(byteArrayToInt(intToByteArray(666666)));
		Message message = new Message(MessageType.ACCEPT, "Ghennadi");
		System.out.println(message);
		message.setServiceName("service1");
		System.out.println(message);
		message.setPayload("ana".getBytes());
		System.out.println(message);
	}
}
