package com.xin.pay.omc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;


/**
 */
@Data
public class OmcCart extends BaseDO {

	private Long userId;

	private Long productId;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * 是否选择,1=已勾选,0=未勾选
	 */
	private Integer checked;
}