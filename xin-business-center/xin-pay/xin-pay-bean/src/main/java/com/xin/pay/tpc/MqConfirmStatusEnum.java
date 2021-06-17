package com.xin.pay.tpc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MqConfirmStatusEnum {
	/**
	 * 未确认.
	 */
	UN_CONFIRMED(10, "未确认"),

	/**
	 * 已确认.
	 */
	CONFIRMED(20, "已确认"),

	/**
	 * 已消费
	 */
	CONSUMED(30, "已消费");

	private int confirmStatus;

	private String value;


}
