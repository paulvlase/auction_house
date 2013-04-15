package webClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import org.apache.log4j.Logger;

import config.WebServiceClientConfig;

public class Util {
	static Logger logger = Logger.getLogger(Util.class);

	public static Random random = new Random();

	public static String getRandomString(int len) {
		char[] str = new char[len];

		for (int i = 0; i < len; i++) {
			int c;
			do {
				c = 48 + random.nextInt(123 - 48);
			} while ((c >= 91 && c <= 96) || (c >= 58 && c <= 64));
			str[i] = (char) c;
		}

		return new String(str);
	}
	
	public static Object askWebServer(Object requestObj) {
		logger.debug("Begin");

		Object responseObj = null;
		
		Socket socket = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		try {
			socket = new Socket(WebServiceClientConfig.IP, WebServiceClientConfig.PORT);

			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			oos.writeObject(requestObj);
			oos.flush();

			responseObj = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		logger.debug("End");
		return responseObj;
	}
}
