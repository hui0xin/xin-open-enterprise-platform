package com.xin.pay.opc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * The class Opc sms setting.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OpcSmsSetting extends BaseDO {

	private static final long serialVersionUID = -1811960778993500439L;
	/**
	 * 可再次发送时间（毫秒）
	 */
	private Integer againSendTime;

	/**
	 * 失效时间（分钟）
	 */
	private Integer invalidTime;

	/**
	 * 短信类型
	 */
	private String type;

	/**
	 * 类型描述
	 */
	private String typeDesc;

	/**
	 * 模板code
	 */
	private String templetCode;

	/**
	 * 模板内容
	 */
	private String templetContent;

	/**
	 * 一天中可发送的最大数量
	 */
	private Integer sendMaxNum;

	/**
	 * 一个IP一天中可发送的最大数量
	 */
	private Integer ipSendMaxNum;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 删除标识(1-已删除；0-未删除)
	 */
	private Integer yn;
}