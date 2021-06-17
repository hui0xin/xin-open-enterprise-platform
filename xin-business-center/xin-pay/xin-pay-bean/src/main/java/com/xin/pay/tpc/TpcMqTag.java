package com.xin.pay.tpc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TpcMqTag extends BaseDO {
	private static final long serialVersionUID = -1063353649973385058L;

	/**
	 * 主题ID
	 */
	private Long topicId;

	/**
	 * 城市编码
	 */
	private String tagCode;

	/**
	 * 区域编码
	 */
	private String tagName;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;
}