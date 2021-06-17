package com.xin.pay.tpc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TpcMqConsumer extends BaseDO {

	private static final long serialVersionUID = 9104188440392558541L;

	/**
	 * 微服务名称
	 */
	private String applicationName;

	/**
	 * 城市编码
	 */
	private String consumerCode;

	/**
	 * 区域编码
	 */
	private String consumerName;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

}