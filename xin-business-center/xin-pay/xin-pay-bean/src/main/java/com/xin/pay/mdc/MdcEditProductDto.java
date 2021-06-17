package com.xin.pay.mdc;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@Data
public class MdcEditProductDto implements Serializable {
	private static final long serialVersionUID = 8578699727403591194L;
	/**
	 * 商品ID
	 */
	private Long id;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品编码
	 */
	private String productCode;

	/**
	 * 品类Id集合
	 */
	private List<Long> categoryIdList;

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
	 * 商品图片流水号集合
	 */
	private List<Long> attachmentIdList;

	/**
	 * 商品详情
	 */
	private String detail;
}
