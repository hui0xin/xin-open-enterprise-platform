package com.xin.commons.support.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.Collection;
import java.util.Map;

/**
 * ObjectUtil
 * 判断对象 util
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtil {
	
	/**
	 * The constant STRING_NULL.
	 */
	private final static String STRING_NULL = "-";

	/**
	 * 判断对象是否Empty(null或元素为0)
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj 待检查对象
	 *
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null) {
			return true;
		}
		if (pObj == "") {
			return true;
		}
		if (pObj instanceof String) {
			return ((String) pObj).length() == 0;
		} else if (pObj instanceof Collection) {
			return ((Collection) pObj).isEmpty();
		} else if (pObj instanceof Map) {
			return ((Map) pObj).size() == 0;
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素大于0)
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj 待检查对象
	 *
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null) {
			return false;
		}
		if (pObj == "") {
			return false;
		}
		if (pObj instanceof String) {
			return ((String) pObj).length() != 0;
		} else if (pObj instanceof Collection) {
			return !((Collection) pObj).isEmpty();
		} else if (pObj instanceof Map) {
			return ((Map) pObj).size() != 0;
		}
		return true;
	}

	/**
	 * 判断一个或多个对象是否为空
	 *
	 * @param values 可变参数, 要判断的一个或多个对象
	 *
	 * @return 只有要判断的一个对象都为空则返回true, 否则返回false boolean
	 */
	public static boolean isNull(Object... values) {
		if (!isNotNullAndNotEmpty(values)) {
			return true;
		}
		for (Object value : values) {
			boolean flag;
			if (value instanceof Object[]) {
				flag = !isNotNullAndNotEmpty((Object[]) value);
			} else if (value instanceof Collection<?>) {
				flag = !isNotNullAndNotEmpty((Collection<?>) value);
			} else if (value instanceof String) {
				flag = isOEmptyOrNull(value);
			} else {
				flag = (null == value);
			}
			if (flag) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象数组是否为空并且数量大于0
	 *
	 * @param value the value
	 *
	 * @return boolean
	 */
	private static Boolean isNotNullAndNotEmpty(Object[] value) {
		boolean bl = false;
		if (null != value && 0 < value.length) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象集合（List,Set）是否为空并且数量大于0
	 *
	 * @param value the value
	 *
	 * @return boolean
	 */
	private static Boolean isNotNullAndNotEmpty(Collection<?> value) {
		boolean bl = false;
		if (null != value && !value.isEmpty()) {
			bl = true;
		}
		return bl;
	}

	/**
	 * Is o empty or null boolean.
	 *
	 * @param o the o
	 *
	 * @return boolean boolean
	 */
	private static boolean isOEmptyOrNull(Object o) {
		return o == null || isSEmptyOrNull(o.toString());
	}
	/**
	 * Is s empty or null boolean.
	 *
	 * @param s the s
	 *
	 * @return boolean boolean
	 */
	private static boolean isSEmptyOrNull(String s) {
		return trimAndNullAsEmpty(s).length() <= 0;
	}

	/**
	 * Trim and null as empty string.
	 *
	 * @param s the s
	 *
	 * @return java.lang.String string
	 */
	private static String trimAndNullAsEmpty(String s) {
		if (s != null && !s.trim().equals(STRING_NULL)) {
			return s.trim();
		} else {
			return "";
		}
		// return s == null ? "" : s.trim();
	}

}
