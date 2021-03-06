package com.user.auth.bean.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
* @Class: OauthAccessTokenDo
* @Description: oauth访问令牌
* @author: 系统
* @created: 2021-03-06
*/
@Data
@ApiModel(value="OauthAccessTokenDo",description="oauth访问令牌")
public class OauthAccessTokenDo implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name="id", value="主键")
    private Long id;

    /**
     * MD5加密的access_token的值
     */
    @ApiModelProperty(name="tokenId", value="MD5加密的access_token的值")
    private String tokenId;

    /**
     * MD5加密过的username,client_id,scope  
     */
    @ApiModelProperty(name="authenticationId", value="MD5加密过的username,client_id,scope  ")
    private String authenticationId;

    /**
     * 登录的用户名
     */
    @ApiModelProperty(name="userName", value="登录的用户名")
    private String userName;

    /**
     * 客户端ID
     */
    @ApiModelProperty(name="clientId", value="客户端ID")
    private String clientId;

    /**
     * MD5加密果的refresh_token的值
     */
    @ApiModelProperty(name="refreshToken", value="MD5加密果的refresh_token的值")
    private String refreshToken;

    /**
     * 创建时间
     */
    @ApiModelProperty(name="createTime", value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(name="updateTime", value="修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * OAuth2AccessToken.java对象序列化后的二进制数据
     */
    @ApiModelProperty(name="token", value="OAuth2AccessToken.java对象序列化后的二进制数据")
    private byte[] token;

    /**
     * OAuth2Authentication.java对象序列化后的二进制数据
     */
    @ApiModelProperty(name="authentication", value="OAuth2Authentication.java对象序列化后的二进制数据")
    private byte[] authentication;

    private static final long serialVersionUID = 1L;
}