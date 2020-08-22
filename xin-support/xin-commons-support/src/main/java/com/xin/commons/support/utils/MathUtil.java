package com.xin.commons.support.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

@Slf4j
public class MathUtil {

	public static final Random RANDOM = new Random(System.currentTimeMillis());

	private MathUtil() {
	}

	/**
	 * 字符串过长会导致jvm进入死循环
	 * @param s
	 * @return
	 */
	public static String parseAvgFormat(String s) {
		if (s == null) {
			return s;
		}
		if (s.length() <= 30) {
			return s;
		}
		return s.substring(0, 31);
	}

	public static byte toByte(String s) {
		return toByte(s, 10, (byte) 0);
	}

	public static byte toByte(String s, byte defaultValue) {
		return toByte(s, 10, defaultValue);
	}

	public static byte toByte(String s, int radix, byte defaultValue) {
		byte result = defaultValue;
		s = parseAvgFormat(s);
		try {
			result = (byte) toInt(s, radix, (int) result);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public static int toInt(char s) {
		return toInt(s, 0);
	}

	public static int toInt(char s, int defaultValue) {
		int result = defaultValue;
		try {
			result = Integer.parseInt(String.valueOf(s));
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public static int toDefaultInt(int value, int defaultValue) {
		if(value == 0) {
			return defaultValue;
		}
		return value;
	}
	public static int toInt(String s, int radix, int defaultValue) {
		if (StringUtils.isBlank(s)) {
			return defaultValue;
		}
		int result = defaultValue;
		s = parseAvgFormat(s);
		try {
			result = Integer.parseInt(s.trim(), radix);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public static int toInt(String s, int defaultValue) {
		return toInt(s, 10, defaultValue);
	}

	public static int toInt(String s) {
		return toInt(s, 0);
	}

	public static int[] toIntArr(String s, int[] defaultValueArr) {
		if (StringUtils.isBlank(s)) {
			return defaultValueArr;
		}
		String[] sArr = s.split(",");
		int sArrLength = sArr != null ? sArr.length : 0;
		if (sArrLength < 1) {
			return defaultValueArr;
		}
		int[] resultArr = new int[sArrLength];
		for (int i = 0; i < sArrLength; i++) {
			try {
				resultArr[i] = Integer.parseInt(sArr[i]);
			} catch (Exception e) {
			}
		}
		return resultArr;
	}

	public static short toShort(String s, int radix, short defaultValue) {
		if (StringUtils.isBlank(s)) {
			return defaultValue;
		}
		short result = defaultValue;
		s = parseAvgFormat(s);
		try {
			result = Short.parseShort(s.trim(), radix);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public static short toShort(String s, short defaultValue) {
		return toShort(s, 10, defaultValue);
	}

	public static short toShort(String s) {
		return toShort(s, 10, (short) 0);
	}

	public static long toLong(String s, long defaultValue) {
		return toLong(s, 10, defaultValue);
	}

	public static long toLong(String s, int radix, long defaultValue) {
		if (StringUtils.isBlank(s)) {
			return defaultValue;
		}
		long result = defaultValue;
		s = parseAvgFormat(s);
		try {
			result = Long.parseLong(s.trim(), radix);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public static long toLong(String s) {
		return toLong(s, 10, 0);
	}

	public static float toFloat(String s, float defaultValue) {
		if (StringUtils.isBlank(s)) {
			return defaultValue;
		}
		float d = defaultValue;
		s = parseAvgFormat(s);
		try {
			d = Float.parseFloat(s.trim());
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return d;
	}

	public static float toFloat(String s) {
		return toFloat(s, 0.0f);
	}

	public static double toDouble(String s, double defaultValue) {
		if (StringUtils.isBlank(s)) {
			return defaultValue;
		}
		double result = defaultValue;
		s = parseAvgFormat(s);
		try {
			result = Double.parseDouble(s);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public static double toDouble(String s) {
		return toDouble(s, 0.0D);
	}

	public static BigDecimal toBigDecimal(String s) {
		BigDecimal bigDecimal = new BigDecimal("0.0");
		if (StringUtils.isBlank(s)) {
			return bigDecimal;
		}
		try {
			bigDecimal = new BigDecimal(s);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return bigDecimal;
	}

	public static BigDecimal nullToBigDecimal(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return new BigDecimal("0.0");
		}
		return bigDecimal;
	}

	/**
	 * 功能:是否是数学数字(小数,负数,整数等) <br>
	 * 注意:+0000=true 0.0000=true -0000=true -0.0000=true
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumberMath(String s) {
		if (StringUtils.isBlank(s)) {
			return false;
		}
		char c = s.charAt(0);
		int pointIndex = s.indexOf(".");
		if (c == '-' || c == '+' || (c >= '0' && c <= '9')) {
		} else {
			return false;
		}
		if (s.endsWith(".")) {
			return false;
		}
		if (s.indexOf(s, pointIndex + 1) > -1) {// 有2个小数点
			return false;
		}

		if (pointIndex > -1) {
			s = s.replaceAll("\\.", "");
		}
		return ValidateUtil.isNumber(s.substring(1));
	}

	// 负数（Negative）, 非负数（Nonnegative）, 加号（Plus Sign）, 零（Zero）．
	public static boolean isNegative(String s) {
		if (s == null || !s.startsWith("-") || !isNumberMath(s)) {
			return false;
		}
		double sDouble = toDouble(s);
		if (sDouble < -0.00000000000000000001D) {
			return true;
		}
		return false;
	}

	public static boolean isPositive(String s) {
		if (s == null || s.startsWith("-") || !isNumberMath(s)) {
			return false;
		}
		double sDouble = toDouble(s);
		if (sDouble > 0.00000000000000000001D) {
			return true;
		}
		return false;
	}

	public static boolean isZero(String s) {
		if (s == null || !isNumberMath(s)) {
			return false;
		}
		double sDouble = toDouble(s);
		if (sDouble > -0.00000000000000000001D && sDouble < 0.00000000000000000001D) {
			return true;
		}
		return false;
	}

	public static boolean isDouble(String s) {
		s = parseAvgFormat(s);
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 功能：格式化双精度数,四舍五入 <br>
	 * 
	 * @param d
	 * @param pattern
	 *            ########0<br>
	 *            ########0.0<br>
	 *            ########0.00<br>
	 *            ########0.000<br>
	 *            ########0.0000<br>
	 *            ########0.00000<br>
	 *            ##.######最高6位精度123.456789 等
	 * @return
	 */
	public static String formatDouble(double d, String pattern) {
		return formatDouble(d, pattern, RoundingMode.HALF_UP);// 四舍五入
	}

	/**
	 * 功能：格式化双精度数,四舍五入 <br>
	 * 
	 * @param d
	 * @param pattern
	 *            ########0<br>
	 *            ########0.0<br>
	 *            ########0.00<br>
	 *            ########0.000<br>
	 *            ########0.0000<br>
	 *            ########0.00000<br>
	 *            ##.######最高6位精度123.456789 等
	 * @param roundingMode
	 *            {@link RoundingMode#HALF_UP}
	 * @return
	 */
	public static String formatDouble(double d, String pattern, RoundingMode roundingMode) {
		String result = "Format Error";
		if (pattern == null || "".equals(pattern))
			pattern = "########0.00";
		try {
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			decimalFormat.setRoundingMode(roundingMode);
			result = decimalFormat.format(d);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public static String formatDouble(double d) {
		return formatDouble(d, "########0.00");
	}

	/**
	 * 功能：格式化浮点数
	 * 
	 * @param f
	 * @return
	 */
	public static String formatFloat(float f, String pattern) {
		String result = "Format Error";
		double d = 0.00d;
		try {
			d = Double.parseDouble(parseAvgFormat(Float.toString(f)));
			result = formatDouble(d, pattern);
		} catch (Exception e) {
			// LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public static String formatFloat(float f) {
		return formatFloat(f, "########0.00");
	}

	/**
	 * 
	 * @param number
	 *            数据值
	 * @param scale
	 *            保留小时点位数
	 * @return 返回经过四舍五入的小数
	 */
	public static Object round(Object number, int scale) {
		if (number instanceof Float) {
			BigDecimal bg = new BigDecimal((Float) number);
			number = bg.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
		} else if (number instanceof Double) {
			BigDecimal bg = new BigDecimal((Double) number);
			number = bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return number;
	}

	/**
	 * 功能：返回整数字符串数组,包括最小的,不包括最大的,最多随机提取100万次就返回数组,超过就强制终止
	 * 
	 * @param valueMin
	 * @param valueMax
	 *            返回值中不包括
	 * @param size
	 *            有多少个符合返回多少个
	 * @return valueMin<=return<valueMax
	 */
	public static int[] getRandomIntArr(int valueMin, int valueMax, int size) {
		Integer[] reInteger = new Integer[size];
		int valueCount = valueMax - valueMin;

		if (valueCount <= size) {
			for (int i = 0; i < valueCount; i++) {
				reInteger[i] = new Integer(valueMin + i);
			}
		} else {// 符合的数大于size
			int forCount = 0;
			for (int iPos = 0; iPos < size;) {// 防止这里出现死循环
				int ranValue = getRandomInt(valueMin, valueMax);
				if (isInArr(reInteger, ranValue) == -1) {
					reInteger[iPos] = new Integer(ranValue);
					iPos++;
				}
				forCount++;
				if (forCount > 1000000) {// 最多随机提取100万次就返回数组,超过就强制终止
					String errMsg = new StringBuffer("ERROR!!! over 1000000 times at  public static int[] getRandomIntArray(").append(valueMin).append(",").append(valueMax).append(",").append(size).toString();
					log.error(errMsg);
					break;
				}
			}
		}

		int reIntLength = 0;
		for (int i = 0; i < size; i++) {
			if (reInteger[i] != null)
				reIntLength++;
		}
		if (reIntLength < 1) {
			return null;
		} else {
			int[] reInt = new int[reIntLength];
			for (int i = 0, j = 0; i < size; i++) {
				if (reInteger[i] != null) {
					reInt[j] = reInteger[i];
					j++;
				}
			}
			Arrays.sort(reInt);
			return reInt;
		}
	}

	public static int isInArr(Object[] arr, Object value) {
		int arrLength = arr != null ? arr.length : 0;
		if (arrLength < 1) {
			return -1;
		}
		if (value == null) {
			for (int i = 0; i < arrLength; i++) {
				if (arr[i] == null) {
					return i;
				}
			}
			return -1;
		}
		if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float) {
			String valueStr = value.toString();
			for (int i = 0; i < arrLength; i++) {
				if (valueStr.equals(arr[i])) {
					return i;
				}
			}
			return -1;
		}

		for (int i = 0; i < arrLength; i++) {
			if (value.equals(arr[i])) {
				return i;
			}
		}
		return -1;
	}

	public static int getRandomInt() {
		return RANDOM.nextInt();
	}

	/**
	 * 
	 * @param valueMax
	 *            必须大于0
	 * @return
	 */
	public static int getRandomInt(int valueMax) {
		return RANDOM.nextInt(valueMax);
	}

	/**
	 * 返回随机的数 包括valueMin,不包括valueMax
	 * 
	 * @param valueMin
	 *            可以是负数
	 * @param valueMax
	 *            可以是负数
	 * @return valueMin<=return<valueMax
	 */
	public static int getRandomInt(int valueMin, int valueMax) {
		if (valueMin >= valueMax) {// 返回valueMin
			return valueMin;
		}
		int valueMinRandom = valueMin;
		int valueMaxRandom = valueMax;
		if (valueMin < 0) {// 要做特殊的处理
			valueMinRandom = 0;
			valueMaxRandom = valueMax + valueMin * (-1);
		}
		int valueRandom = valueMinRandom;
		try {
			do {
				valueRandom = RANDOM.nextInt(valueMaxRandom);// 返回一个大于等于0小于n的随机数random.nextInt(maxInt)
				// 返回0<=int<maxInt
				// random.nextInt()有可能返回负数
				// n<1时抛出异常
			} while (valueRandom < valueMinRandom);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (valueMin < 0) {// 要做特殊的处理
			valueRandom = valueRandom + valueMin;
		}
		return valueRandom;
	}

	public static String toUnsignBinaryString(byte number) {
		if (number < 0) {
			int max = 256;
			number = (byte) (max + number);
		}
		return toUnsignBinaryString((int) number, 8);
	}

	public static String toUnsignBinaryString(short number) {
		if (number < 0) {
			int max = 65536;
			number = (short) (max + number);
		}
		return toUnsignBinaryString((int) number, 16);
	}

	public static String toUnsignBinaryString(int number) {
		return toUnsignBinaryString(number, 32);
	}

	/**
	 * 
	 * @param number
	 * @param bitLength
	 *            8||16||32
	 * @return
	 */
	public static String toUnsignBinaryString(int number, int bitLength) {
		if (bitLength != 8 && bitLength != 16 && bitLength != 32) {
			throw new IllegalArgumentException("bitLength != 8 && bitLength != 16 && bitLength != 32 bitLength=" + bitLength);
		}
		if (number < 0) {
			long max = (bitLength == 8 ? 256 : (bitLength == 16 ? 65536 : 4294967296L));
			number = (int) (max + number);
		}
		String result = Integer.toBinaryString(number);
		for (int i = result.length(); i < bitLength; i++) {
			result = "0" + result;
		}
		if (result.length() > bitLength) {
			result = result.substring(result.length() - bitLength, result.length());
		}
		return result;
	}

	/**
	 * 
	 * @param number
	 * @param bitLength
	 *            8||16||32||64
	 * @return
	 */
	public static String toBinaryString(long number, int bitLength) {
		if (bitLength != 8 && bitLength != 16 && bitLength != 32 && bitLength != 64) {
			throw new IllegalArgumentException("bitLength != 8 && bitLength != 16 && bitLength != 32 bitLength=" + bitLength);
		}
		String result = Long.toBinaryString(number);
		for (int i = result.length(); i < bitLength; i++) {
			result = "0" + result;
		}
		if (result.length() > bitLength) {
			result = result.substring(result.length() - bitLength, result.length());
		}
		return result;
	}

	public static int getRandomVersion() {
		return RANDOM.nextInt(10000000);
	}
}
