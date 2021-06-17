package com.xin.pay.opc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.Set;

@Slf4j
@Data
public class MailEntity {
	/**
	 * 获取或设置电子邮件的回复地址。
	 */
	private String replyTo;
	/**
	 * 获取包含此电子邮件的收件人的地址集合。
	 */
	private Set<String> to;
	/**
	 * 获取包含此电子邮件的抄送 (CC) 收件人的地址集合。
	 */
	private Set<String> cc;
	/**
	 * 获取包含此电子邮件的密件抄送 (BCC) 收件人的地址集合。
	 */
	private Set<String> bcc;
	/**
	 * 发送时间
	 */
	private Date sentDate;
	/**
	 * 获取或设置此电子邮件的主题行。
	 */
	private String subject;
	/**
	 * 内容
	 */
	private String text;


}
