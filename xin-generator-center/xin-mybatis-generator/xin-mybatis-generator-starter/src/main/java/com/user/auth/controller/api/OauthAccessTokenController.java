package com.user.auth.controller.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.user.auth.service.OauthAccessTokenService;
import com.user.auth.bean.DO.OauthAccessTokenDo;

/**
 * @class: OauthAccessTokenController
 * @Description:  oauth访问令牌 Controller
 * @author: huixin
 * @created: 2021-03-06
 */
@RestController
@RequestMapping("/api")
@Api(value = "oauth访问令牌 API", tags = {"oauth访问令牌 api操作接口"})
public class OauthAccessTokenController {

    @Autowired
    public OauthAccessTokenService oauthAccessTokenService;

}