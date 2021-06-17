package com.xin.pay.tpc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MqSendStatusEnum {

	/**
	 * 未发送.
	 */
	WAIT_SEND(10, "未发送"),

	/**
	 * 已发送.
	 */
	SENDING(20, "已发送"),

	/**
	 * 已完成
	 */
	FINISH(30, "已完成");

	private int sendStatus;

	private String value;


}
