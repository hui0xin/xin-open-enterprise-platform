package com.user.auth.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.user.auth.service.SysUserService;
import com.user.auth.bean.DO.SysUserDo;

/**
 * @class: SysUserController
 * @Description:  系统用户信息 Controller
 * @author: 系统
 * @created: 2021-03-22
 */
@RestController
@RequestMapping("/controller")
@Api(value = "系统用户信息 API", tags = {"系统用户信息 api操作接口"})
public class SysUserController {

    @Autowired
    public SysUserService sysUserService;

}