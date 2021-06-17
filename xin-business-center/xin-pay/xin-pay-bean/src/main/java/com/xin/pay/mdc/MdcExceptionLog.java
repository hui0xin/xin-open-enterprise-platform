package com.xin.pay.mdc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class MdcExceptionLog {

	private Long id;

	/**
	 * 系统应用名
	 */
	private String applicationName;

	/**
	 * 异常类型
	 */
	private String exceptionSimpleName;

	/**
	 * 异常信息(通过exception.getMessage()获取到的内容)
	 */
	private String exceptionMessage;

	/**
	 * 异常原因(通过exception.getCause()获取到的内容)
	 */
	private String exceptionCause;

	/**
	 * 异常堆栈信息
	 */
	private String exceptionStack;

	/**
	 * 操作者姓名
	 */
	private String creator;

	/**
	 * 操作者id
	 */
	private String creatorId;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
}