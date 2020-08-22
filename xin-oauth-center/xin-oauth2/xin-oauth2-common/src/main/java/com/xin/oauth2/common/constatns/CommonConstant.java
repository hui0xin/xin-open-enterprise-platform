package com.xin.oauth2.common.constatns;

/**
 * @Description 公共常量
 * @Author chongzi
 * @Date 2019/2/20 11:46
 * @Param
 * @return
 **/
public interface CommonConstant {
	/**
	 * 删除
	 */
	Integer STATUS_DEL = 0;
	/**
	 * 正常
	 */
	Integer STATUS_NORMAL = 1;

	/**
	 * 锁定
	 */
	Integer STATUS_LOCK = 9;
	/**
	 * 失败标记
	 */
	Integer FAIL = 500;


	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";

	/**
	 * 无效的token
	 */
	int INVALID_TOKEN = 401;

	/**
	 * 访问此资源需要完全的身份验证
	 */
	int UN_LOGIN = 401;

	/**
	 * 权限不足
	 */
	int UNAUTHORIZED = 401;
}
