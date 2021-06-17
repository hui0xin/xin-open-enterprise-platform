package com.xin.pay.mdc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MdcProduct extends BaseDO {

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品编码
	 */
	private String productCode;

	private Long categoryId;

	/**
	 * 商品副标题
	 */
	private String subtitle;

	/**
	 * 产品主图,url相对地址
	 */
	private String mainImage;

	/**
	 * 价格,单位-元保留两位小数
	 */
	private BigDecimal price;

	/**
	 * 库存数量
	 */
	private Integer stock;

	/**
	 * 商品状态.1-在售 2-下架 3-删除
	 */
	private Integer status;

	/**
	 * 图片地址,json格式,扩展用
	 */
	private String subImages;

	/**
	 * 商品详情
	 */
	private String detail;
}