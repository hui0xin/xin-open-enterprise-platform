package com.xin.commons.mail.bean;

public class InlineResource {

	/** 图片名称*/
	private String cid;

	/** 图片路径*/
	private String path;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public InlineResource(String cid, String path) {
		super();
		this.cid = cid;
		this.path = path;
	}
}
