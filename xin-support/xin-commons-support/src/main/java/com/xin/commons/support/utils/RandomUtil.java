package com.xin.commons.support.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.UUID;

/**
 * Random util.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtil {

	private static final int MAX_LENGTH = 50;

	/**
	 * 生成一个随机验证码 大小写字母+数字
	 *
	 * @param length the length
	 *
	 * @return 随机验证码 string
	 */
	public static String createComplexCode(int length) {

		if (length > MAX_LENGTH) {
			length = MAX_LENGTH;
		}

		Random r = new Random();

		StringBuilder code = new StringBuilder();

		while (true) {
			if (code.length() == length) {
				break;
			}
			int tmp = r.nextInt(127);
			if (tmp < 33 || tmp == 92 || tmp == 47 || tmp == 34) {
				continue;
			}
			char x = (char) (tmp);
			if (code.toString().indexOf(x) > 0) {
				continue;
			}
			code.append(x);
		}

		return code.toString();
	}

	/**
	 * Create number code string.
	 *
	 * @param length the length
	 *
	 * @return the string
	 */
	public static String createNumberCode(int length) {

		return randomString("0123456789", length);
	}

	private static String randomString(String baseString, int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		if (length < 1) {
			length = 1;
		}

		int baseLength = baseString.length();

		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(baseLength);
			sb.append(baseString.charAt(number));
		}

		return sb.toString();
	}

	/**
	 * Uuid string.
	 *
	 * @return the string
	 */
	public synchronized static String uuid() {

		return UUID.randomUUID().toString().replace("-", "");
	}
}