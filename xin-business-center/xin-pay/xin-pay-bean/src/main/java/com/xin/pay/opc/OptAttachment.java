package com.xin.pay.opc;

import com.xin.commons.support.bean.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OptAttachment extends BaseDO {

	private static final long serialVersionUID = -2419047791219240612L;
	/**
	 * 中心名称(英文简写)
	 */
	private String centerName;

	/**
	 * 文件服务器根目录
	 */
	private String bucketName;

	/**
	 * 上传附件的相关业务流水号
	 */
	private String refNo;

	/**
	 * 附件名称
	 */
	private String name;

	/**
	 * 附件存储相对路径
	 */
	private String path;

	/**
	 * 附件类型
	 */
	private String type;

	/**
	 * 附件格式
	 */
	private String format;

	/**
	 * 备注
	 */
	private String description;
}