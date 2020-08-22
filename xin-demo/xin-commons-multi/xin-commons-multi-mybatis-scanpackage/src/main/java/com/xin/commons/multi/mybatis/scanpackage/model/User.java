package com.xin.commons.multi.mybatis.scanpackage.model;

import com.xin.commons.multi.mybatis.scanpackage.enums.UserSexEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String userName;
	private String passWord;
	private UserSexEnum userSex;
	private String nickName;
}