package com.xin.auth.jwt.controller;

import com.xin.auth.jwt.core.service.AuthJwtService;
import com.xin.auth.jwt.entity.JwtParam;
import com.xin.auth.jwt.entity.JwtUserInfo;
import com.xin.commons.support.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/common")
@Api(value = "auth服务 controller", tags = {"登陆认证 操作接口"})
public class AuthJwtController {

    @Autowired
    private AuthJwtService authJwtService;

    @ApiOperation(value = "生成token", notes = "生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwtUserInfo", value = "生成token参数实体", required = true, dataType = "JwtUserInfo", paramType = "query")
    })
    @PostMapping(value = "/v1/generateToken")
    public ResponseResult generateToken(@RequestBody JwtUserInfo jwtUserInfo) throws Exception{
        String token = authJwtService.generateToken(jwtUserInfo);
        return ResponseResult.success(token);
    }

    @ApiOperation(value = "生成token", notes = "生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwtParam", value = "生成token参数实体", dataType = "JwtParam", paramType = "query"),
            @ApiImplicitParam(name = "jwtUserInfo", value = "生成token参数实体", required = true, dataType = "JwtUserInfo", paramType = "query")
    })
    @PostMapping(value = "/v1/generateTokenByBO")
    public ResponseResult generateTokenByBO(@RequestBody JwtUserInfo jwtUserInfo,
                                        @RequestBody JwtParam jwtParam) throws Exception{
        String token = authJwtService.generateToken(jwtUserInfo,jwtParam);
        return ResponseResult.success(token);
    }

    @ApiOperation(value = "生成token", notes = "生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ttlMillis", value = "过期时间，单位是毫秒", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "jwtUserInfo", value = "生成token参数实体", required = true, dataType = "JwtUserInfo", paramType = "query")
    })
    @PostMapping(value = "/v1/generateTokenByTime")
    public ResponseResult generateTokenByTime(@RequestBody JwtUserInfo jwtUserInfo,
                                          @RequestParam("ttlMillis") Long ttlMillis) throws Exception{
        String token = authJwtService.generateToken(jwtUserInfo,ttlMillis);
        return ResponseResult.success(token);
    }

    @ApiOperation(value = "刷新token", notes = "生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token字符串", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/v1/refreshToken")
    public ResponseResult refreshToken(@RequestParam String token) throws Exception{
        String tokenResult = authJwtService.refreshToken(token);
        return ResponseResult.success(tokenResult);
    }

    @ApiOperation(value = "验证token", notes = "验证token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token字符串", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/v1/verifyToken")
    public ResponseResult<Boolean> verifyToken(@RequestParam String token) throws Exception{
        Boolean tokenResult = authJwtService.verifyToken(token);
        return ResponseResult.success(tokenResult);
    }

    @ApiOperation(value = "获取用户信息，userId，userName，头像", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token字符串", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/v1/getUserInfo")
    public ResponseResult<JwtUserInfo> getUserInfo(@RequestParam String token) throws Exception{
        JwtUserInfo tokenResult = authJwtService.getUserInfo(token);
        return ResponseResult.success(tokenResult);
    }

}
