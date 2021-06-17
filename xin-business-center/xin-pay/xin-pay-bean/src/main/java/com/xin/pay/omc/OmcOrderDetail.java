package com.xin.pay.omc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * The class Omc order detail.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OmcOrderDetail extends BaseDO {

	private static final long serialVersionUID = -2167960069551022897L;
	/**
	 * 订单明细序列号
	 */
	private String detailNo;

	private Long userId;

	private String orderNo;

	private Long productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品图片地址
	 */
	private String productImage;

	/**
	 * 生成订单时的商品单价, 单位是元,保留两位小数
	 */
	private BigDecimal currentUnitPrice;

	/**
	 * 商品数量
	 */
	private Integer quantity;

	/**
	 * 商品总价,单位是元,保留两位小数
	 */
	private BigDecimal totalPrice;
}