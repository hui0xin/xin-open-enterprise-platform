package com.xin.pay.omc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PtcPayInfo extends BaseDO {

	private static final long serialVersionUID = 7949091072343450552L;
	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 支付平台:1-支付宝,2-微信
	 */
	private Integer payPlatform;

	/**
	 * 支付宝支付流水号
	 */
	private String platformNumber;

	/**
	 * 支付宝支付状态
	 */
	private String platformStatus;

}