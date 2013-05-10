package webClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class WebMessage {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(WebMessage.class);

	public static byte[] serialize(Object obj) {
		byte[] result;
		byte[] resultSize;
		byte[] object;

		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = null;
		try {
			o = new ObjectOutputStream(b);
			o.writeObject(obj);

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			o.close();
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* Object representation */
		object = b.toByteArray();

		result = new byte[b.size() + Integer.SIZE / Byte.SIZE];
		resultSize = intToByteArray(b.size());
		logger.debug("Message length as array = " + Arrays.toString(resultSize));
		logger.debug("Message length = " + b.size());

		/* Package length */
		for (int i = 0; i < resultSize.length; i++) {
			result[i] = resultSize[i];
		}

		for (int i = 0; i < object.length; i++) {
			result[i + Integer.SIZE / Byte.SIZE] = object[i];
		}

		return result;
	}

	public static Object deserialize(byte[] array) {
		ByteArrayInputStream b;
		ObjectInputStream o;
		Object obj = null;
		byte object[];

		if (array.length <= 4) {
			logger.error("Corrupted package ...");
			return null;
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
		
		return obj;
	}

	private static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (((value >> (3 * Byte.SIZE)) % 256) - 128),
				(byte) (((value >> (2 * Byte.SIZE)) % 256) - 128), (byte) (((value >> Byte.SIZE) % 256) - 128),
				(byte) ((value % 256) - 128) };
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
