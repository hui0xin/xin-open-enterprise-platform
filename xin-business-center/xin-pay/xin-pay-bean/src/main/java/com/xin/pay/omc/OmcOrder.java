package com.xin.pay.omc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class OmcOrder extends BaseDO {

	private static final long serialVersionUID = -8434937678211570532L;
	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 用户id
	 */
	private Long userId;

	private Long shippingId;

	/**
	 * 实际付款金额,单位是元,保留两位小数
	 */
	private BigDecimal payment;

	/**
	 * 支付类型,1-在线支付
	 */
	private Integer paymentType;

	/**
	 * 运费,单位是元
	 */
	private Integer postage;

	/**
	 * 订单状态:0-已取消-10-未付款, 20-已付款, 40-已发货, 50-交易成功, 60-交易关闭
	 */
	private Integer status;

	/**
	 * 支付时间
	 */
	private Date paymentTime;

	/**
	 * 发货时间
	 */
	private Date sendTime;

	/**
	 * 交易完成时间
	 */
	private Date endTime;

	/**
	 * 交易关闭时间
	 */
	private Date closeTime;
}