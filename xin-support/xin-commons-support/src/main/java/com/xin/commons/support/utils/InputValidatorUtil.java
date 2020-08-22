package com.xin.commons.support.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidatorUtil {
	// 提前编译正则表达式 提高性能
	private static final Pattern PATTERN_CHECKTEL = Pattern.compile("^[1](3|4|5|7|8)[0-9]{9}$");
	private static final Pattern PATTERN_ISSPECHARS = Pattern
			.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
	private static final Pattern PATTERN_REPLACESPECHARS = Pattern
			.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
	private static final Pattern PATTERN_CHECKEMAIL = Pattern
			.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
	private static final Pattern PATTERN_CHECK_NICKNAME = Pattern.compile("^[a-zA-Z0-9_\u4e00-\u9fa5]+$");

	/**
	 * 校验用户名是否合法
	 * 
	 * @param userName
	 * @return
	 */
	public static boolean validatorUserName(String userName) {
		if (StringUtils.isEmpty(userName)) {
			return false;
		}
		if (userName.contains(" ")) {
			return false;
		}
		int len = userName.length();
		if (len < 6 || len > 20) {
			return false;
		}
		if (userName.matches("[A-Za-z0-9]+")) {
			Pattern p1 = Pattern.compile("[A-Za-z]+");
			Pattern p3 = Pattern.compile("[0-9]+");
			Matcher m = p1.matcher(userName);
			if (!m.find())
				return false;
			else {
				m.reset().usePattern(p3);
				if (!m.find())
					return false;
				else {
					return true;
				}
			}
		} else {
			return false;
		}
	}

	/**
	 * 检查昵称，字符类别：中文，英文字母，数字，下划线，长度：最多10个中文，20个英文或组合字符。
	 * 
	 * @param nickname
	 * @return
	 */
	public static boolean checkNickname(String nickname) {
		if (StringUtils.isBlank(nickname)) {
			return false;
		}
		if (nickname.length() > 20) {
			return false;
		}
		return PATTERN_CHECK_NICKNAME.matcher(nickname).find();
	}

	/**
	 * 邮箱校验
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		if (email.contains(" ")) {
			return false;
		}
		return PATTERN_CHECKEMAIL.matcher(email).find();
	}

	/**
	 * 手机号码校验
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isTel(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return false;
		}
		if (phone.contains(" ")) {
			return false;
		}
		if (phone.length() != 11) {
			return false;
		}
		return PATTERN_CHECKTEL.matcher(phone).find();
	}

	/**
	 * 特殊字符校验
	 * 
	 * @param
	 * @return
	 */
	public static boolean isSpechars(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return PATTERN_ISSPECHARS.matcher(str).find();
	}

	/**
	 * 使用空字符串替换掉特殊字符
	 * 
	 * @param
	 * @return
	 */
	public static String replaceSpechars(String str) {
		if (isSpechars(str)) {
			return PATTERN_REPLACESPECHARS.matcher(str).replaceAll("").trim();
		} else {
			return str;
		}
	}
}
