package com.xin.security.oauth2.controller;

import com.xin.commons.support.response.ResponseResult;
import com.xin.security.oauth2.bean.DO.SysUser;
import com.xin.security.oauth2.bean.vo.SysUserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登陆和注销
 */
@Api(tags="用户管理")
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

	@ApiOperation(value = "用户登录")
	@PostMapping("/login")
	public ResponseResult<SysUser> login(@RequestBody SysUserLoginVo sysUserLoginVo) {
		SysUser obj = new SysUser();
		//TODO 1，具体的处理，调用user服务来获取

		//TODO 2，颁发token
		return ResponseResult.success(obj);
	}

	@ApiOperation(value = "用户退出")
	@PostMapping("/logout")
	public ResponseResult logout(HttpServletRequest request) {

		// tODO 把TOKEN清空即可
		
		return ResponseResult.success("退出成功");
	}
	
}
