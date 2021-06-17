//package com.xin.pay.controller;
//
//import com.xin.commons.support.response.ResponseResult;
//import io.swagger.annotations.*;
//import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;
//
//import java.util.*;
//
///**
// * produces	For example, "application/json, application/xml"
// * consumes	For example, "application/json, application/xml"
// * protocols	Possible values: http, https, ws, wss.
// * hidden	配置为true 将在文档中隐藏
// * tags	如果设置这个值、value的值会被覆盖
// * authorizations	高级特性认证时配置
// */
//@Api(value = "用户管理api",tags = "", protocols = "http",consumes = "",produces = "")
//@RestController
//@RequestMapping(value = "/user")
//public class UserController {
//
//    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
//            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "ipAddr", value = "ip哟", required = false, dataType = "String", paramType = "query")
//    })
//    @PostMapping(value = "")
//    public ResponseResult<String> postUser(@ApiIgnore UserInfo user) {
//        return ResponseResult.success(user);
//    }
//
//
//
//}