package com.xin.oauth2.bean.vo;


import com.xin.oauth2.bean.DO.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * commit('SET_ROLES', data)
 * commit('SET_NAME', data)
 * commit('SET_AVATAR', data)
 * commit('SET_INTRODUCTION', data)
 * commit('SET_PERMISSIONS', data)
 **/
@Data
public class SysUserVo implements Serializable {

	private static final long serialVersionUID = -4379972969787452441L;
	/**
	 * 用户基本信息
	 */
	private SysUser sysUser;
	/**
	 * 权限标识集合
	 */
	private String[] permissions;

	/**
	 * 角色集合
	 */
	private String[] roles;

}
