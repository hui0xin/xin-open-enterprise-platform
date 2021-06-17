package com.xin.auth.jwt.controller;

import com.xin.auth.jwt.common.consts.JwtConsts;
import com.xin.auth.jwt.core.service.AuthJwtService;
import com.xin.auth.jwt.entity.JwtUserInfo;
import com.xin.commons.support.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户登陆退出相关 controller", tags = {"用户登陆退出相关 操作接口"})
public class AuthUserController {

    @Autowired
    private AuthJwtService authJwtService;

    @ApiOperation(value = "登陆接口", notes = "登陆接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/v1/login")
    public ResponseResult login(HttpServletResponse response,
                            @RequestParam("userName") String userName,
                            @RequestParam("password") String password) throws Exception{

        //TODO 通过用户服务，获取登陆用户信息，没有则登陆失败，如果有，则颁发签名
        //具体业务逻辑根据需要填写
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        //从用户服务获取的信息补全以下代码
        jwtUserInfo.setUserId(null);
        jwtUserInfo.setUserName(null);
        jwtUserInfo.setHeadImage(null);
        String token = authJwtService.generateToken(jwtUserInfo);
        // 将token放在响应头
        response.setHeader(JwtConsts.AUTH_HEADER_KEY, JwtConsts.TOKEN_PREFIX + token);
        return ResponseResult.success(token);
    }

    @ApiOperation(value = "退出接口", notes = "退出接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/v1/logout")
    public ResponseResult logout(@RequestParam("token") String token) throws Exception{
        authJwtService.logoutToken(token);
        return ResponseResult.success();
    }

}
