package com.xin.pay.tpc;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@Data
public class TpcJobTask implements Serializable {

	private static final long serialVersionUID = -7833392442916077253L;

	private Long id;

	private Integer version;

	/**
	 * 关联业务单号
	 */
	private String refNo;

	/**
	 * 业务类型
	 */
	private String taskType;

	/**
	 * 执行次数
	 */
	private Integer taskExeCount;

	/**
	 * 任务数据
	 */
	private String taskData;

	/**
	 * 是否死亡
	 */
	private Integer dead;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 执行实例IP
	 */
	private String exeInstanceIp;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 执行时间
	 */
	private Integer exeTime;

	/**
	 * 删除标识
	 */
	private Integer yn;

	/**
	 * Add send times.
	 */
	public void addSendTimes() {
		this.taskExeCount++;
	}
}