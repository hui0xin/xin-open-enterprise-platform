package com.xin.pay.tpc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TpcMqProducer extends BaseDO {
	private static final long serialVersionUID = -4064061704648362318L;

	/**
	 * 微服务名称
	 */
	private String applicationName;

	/**
	 * PID 生产者组编码
	 */
	private String producerCode;

	/**
	 * PID 生产者组名称
	 */
	private String producerName;

	private String queryMessageUrl;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;
}