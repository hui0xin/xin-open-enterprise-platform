package com.xin.pay.tpc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TpcMqTopic extends BaseDO {
	private static final long serialVersionUID = 5642946024630652202L;

	/**
	 * 城市编码
	 */
	private String topicCode;

	/**
	 * 区域编码
	 */
	private String topicName;

	/**
	 * MQ类型, 10 rocketmq 20 kafka
	 */
	private Integer mqType;

	/**
	 * 消息类型, 10 无序消息, 20 无序消息
	 */
	private Integer msgType;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remarks;
}