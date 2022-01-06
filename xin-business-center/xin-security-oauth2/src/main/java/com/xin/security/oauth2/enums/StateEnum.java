package com.xin.security.oauth2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xin
 * 数据状态
 */
@Getter
@AllArgsConstructor
public enum StateEnum {
	/**
	 * 无效
	 */
	INVALID(0, "无效"),

	/**
	 * 有效
	 */
	EFFECTIVE(1, "有效");

	/**
	 * 类型
	 */
	private final int status;
	/**
	 * 描述
	 */
	private final String description;

	public static String getDescription(Integer value) {
		if (value == null) {
			return "";
		} else {
			for (StateEnum ms : StateEnum.values()) {
				if (ms.getStatus() == value.intValue()) {
					return ms.getDescription();
				}
			}
			return "";
		}
	}
}
