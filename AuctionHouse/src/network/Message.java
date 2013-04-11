package network;

import java.nio.ByteBuffer;

import data.Service;

/**
 * Used for tranfers over network between clients and sellers.
 * 
 * --------------------------------------------- 
 * | size | MessageType | ServiceName | [Data] | 
 * ---------------------------------------------
 * 
 * ------------------------------------------------ 
 * | Int   | Int        | char+ \0 | [payload]    | 
 * ------------------------------------------------
 * 
 * LAUNCH : size, LAUNCH, serviceName
 * ACCEPT : size, ACCEPT, serviceName
 * REFUSE : size, REFUSE, serviceName 
 * DROP : size, DROP, serviceName 
 * TRANSFER_SIZE : size, TRANSFER_SIZE, serviceName 
 * TRANSFER_CHUNK : size, TRANSFER_CHUNK, serviceName, payload
 * 
 * @author Ghennadi Procopciuc
 */
public class Message {
	private static final Integer BYTE_SIZE = 8;

	private ByteBuffer buffer;
	private Service service;

	private enum MessageType {
		LAUNCH, ACCEPT, REFUSE, DROP,

		TRANSFER_SIZE, TRANSFER_CHUNCK
	}

	public Message() {

	}

	public Message(Service service) {
		this.service = service;
		// buildFromService();
		serializeLaunch();
	}

	private void serializeLaunch() {
		Integer size = (2 * Integer.SIZE) / BYTE_SIZE
				+ service.getName().length();
		buffer = ByteBuffer.allocate(size);
		buffer.clear();

		/* Package size */
		buffer.putInt(size);

		/* Package type */
		buffer.putInt(MessageType.LAUNCH.ordinal());

		System.out.println("Number of bytes : "
				+ service.getName().getBytes().length + " Length : "
				+ service.getName().length());

		/* Service name */
		buffer.put(service.getName().getBytes());

		buffer.flip();
	}

	private Service deserializeLaunch() {
		Service service = new Service();
		Integer size;

		/* Package size */
		size = buffer.getInt();
		
		/* Package type */
		buffer.getInt();

		
		return service;
	}

	private void buildFromService() {
		// TODO
	}

	public static void main(String[] args) {
		new Message(new Service("ghita"));
	}
}
