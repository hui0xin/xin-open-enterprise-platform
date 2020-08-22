package com.xin.commons.support.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate util.
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateUtil {

	/**
	 * 校验手机号码是否合法.
	 * @param mobile the mobile
	 * @return the boolean
	 */
	public static boolean isMobileNumber(final String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		final String reg = "^((\\+?86)|(\\(\\+86\\)))?(13[0-9][0-9]{8}|14[0-9]{9}|15[0-9][0-9]{8}|17[0-9][0-9]{8}|18[0-9][0-9]{8})$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}

	/**
	 * 正则表达式校验邮箱
	 * @param emaile 待匹配的邮箱
	 * @return 匹配成功返回true 否则返回false;
	 */
	public static boolean checkEmaile(String emaile){
		String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		//正则表达式的模式
		Pattern p = Pattern.compile(RULE_EMAIL);
		//正则表达式的匹配器
		Matcher m = p.matcher(emaile);
		//进行正则匹配
		return m.matches();
	}

	/**
	 * 判断字符串是不是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		String reg = "^[A-Za-z0-9]+$";
		return str.matches(reg);
	}


}
