package com.xin.pay.mdc;

import com.xin.commons.support.bean.BaseDO;

/**
 *
 */
public class MdcAddress extends BaseDO {
	private static final long serialVersionUID = -499010884211394846L;

	/**
	 * 地址名称
	 */
	private String name;

	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 级别（省市区县）
	 */
	private Integer level;

	/**
	 * 区域编码
	 */
	private String adCode;

	/**
	 * 行政区边界坐标点
	 */
	private String polyline;

	/**
	 * 城市中心点
	 */
	private String center;
}