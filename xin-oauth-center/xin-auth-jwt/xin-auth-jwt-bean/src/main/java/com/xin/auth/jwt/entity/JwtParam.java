package com.xin.auth.jwt.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用于生成token的参数", description = "用于生成token的参数，非必填")
public class JwtParam {

    @ApiModelProperty(name = "id",value = "jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值 比如UUID")
    private String id;

    @ApiModelProperty(name = "issuer",value = "jwt签发人")
    private String issuer;

    @ApiModelProperty(name = "ttlMillis",value = "token的有效期，单位是毫秒")
    private Long ttlMillis;

}
