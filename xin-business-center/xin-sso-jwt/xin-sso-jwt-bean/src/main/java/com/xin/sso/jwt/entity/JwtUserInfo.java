package com.xin.sso.jwt.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用于生成token的必须字段", description = "用于生成token的必须字段，可以根据业务需要，只用userId")
public class JwtUserInfo {

    @ApiModelProperty(name ="userId", value = "用户id", required = true)
    private Long userId;

    @ApiModelProperty(name ="userName", value = "用户名称")
    private String userName;

    @ApiModelProperty(name ="headImage", value = "用户头像")
    private String headImage;
}

