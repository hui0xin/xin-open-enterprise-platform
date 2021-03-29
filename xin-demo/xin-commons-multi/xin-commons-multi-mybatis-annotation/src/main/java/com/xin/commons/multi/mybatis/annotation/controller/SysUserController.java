package com.xin.commons.multi.mybatis.annotation.controller;

import com.xin.commons.multi.mybatis.annotation.bean.User;
import com.xin.commons.multi.mybatis.annotation.service.UserService2;
import com.xin.commons.support.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SysUserController {

	@Autowired
	private UserService2 userService2;

	@GetMapping("/add")
	public ResponseResult<String> add() {
		User user = new User();
		user.setNickName("mmmmm");
		user.setUserName("mmmmm");
		user.setPassWord("mmmmm");
		userService2.add(user);
		return ResponseResult.success("success");
	}

}
