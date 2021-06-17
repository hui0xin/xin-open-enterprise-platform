package com.xin.auth.jwt.sdk;

import com.xin.auth.jwt.entity.JwtParam;
import com.xin.auth.jwt.entity.JwtUserInfo;
import com.xin.commons.support.response.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

/**
 * name：指定FeignClient的名称，如果项目使用了Ribbon，name属性会作为微服务的名称，用于服务发现
 * url: url一般用于调试，可以手动指定@FeignClient调用的地址 比如 http://localhost:8080/
 * decode404:当发生http 404错误时，如果该字段为true，会调用decoder进行解码，否则抛出FeignException
 * configuration: Feign配置类，可以自定义Feign的Encoder、Decoder、LogLevel、Contract
 * fallback: 定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口
 * fallbackFactory: 工厂类，用于生成fallback类示例，通过这个属性我们可以实现每个接口通用的容错逻辑，减少重复的代码
 * path: 定义当前FeignClient的统一前缀
 */
@FeignClient(value = "auth-server",path = "/auth")
public interface AuthServerClient {

    @ApiOperation(value = "登陆接口", notes = "登陆接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/user/v1/login")
    ResponseResult login(HttpServletResponse response, @RequestParam("userName") String userName, @RequestParam("password") String password);

    @ApiOperation(value = "退出接口", notes = "退出接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/user/v1/logout")
    ResponseResult logout(@RequestParam("token") String token);


    @ApiOperation(value = "生成token", notes = "生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwtUserInfo", value = "生成token参数实体", required = true, dataType = "JwtUserInfo", paramType = "query")
    })
    @PostMapping(value = "/common/v1/generateToken")
    ResponseResult generateToken(@RequestBody JwtUserInfo jwtUserInfo);

    @ApiOperation(value = "生成token", notes = "生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwtParam", value = "生成token参数实体", dataType = "JwtParam", paramType = "query"),
            @ApiImplicitParam(name = "jwtUserInfo", value = "生成token参数实体", required = true, dataType = "JwtUserInfo", paramType = "query")
    })
    @PostMapping(value = "/common/v1/generateTokenByBO")
    ResponseResult generateTokenByBO(@RequestBody JwtUserInfo jwtUserInfo, @RequestBody JwtParam jwtParam);

    @ApiOperation(value = "生成token", notes = "生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ttlMillis", value = "过期时间，单位是毫秒", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "jwtUserInfo", value = "生成token参数实体", required = true, dataType = "JwtUserInfo", paramType = "query")
    })
    @PostMapping(value = "/common/v1/generateTokenByTime")
    ResponseResult generateTokenByTime(@RequestBody JwtUserInfo jwtUserInfo, @RequestParam("ttlMillis") Long ttlMillis) ;

    @ApiOperation(value = "刷新token", notes = "生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token字符串", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/common/v1/refreshToken")
    ResponseResult refreshToken(@RequestParam String token) ;

    @ApiOperation(value = "验证token", notes = "验证token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token字符串", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/common/v1/verifyToken")
    ResponseResult<Boolean> verifyToken(@RequestParam String token);

    @ApiOperation(value = "获取用户信息，userId，userName，头像", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token字符串", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/common/v1/getUserInfo")
    ResponseResult<JwtUserInfo> getUserInfo(@RequestParam String token);
}
