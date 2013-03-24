package webServiceClient;

import java.util.Random;

public class Util {
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
}
