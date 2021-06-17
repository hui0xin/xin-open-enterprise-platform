package com.xin.oauth2.bean.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @Class: OauthClientDetails
* @Description: oauth客户端信息
* @author: 系统
* @created: 2020-08-03
*/
@Data
@ApiModel(value="OauthClientDetails",description="oauth客户端信息")
public class OauthClientDetails implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name="id", value="主键")
    private Long id;

    /**
     * 客户端ID
     */
    @ApiModelProperty(name="clientId", value="客户端ID")
    private String clientId;

    /**
     * 资源ID集合,多个资源时用逗号(,)分隔
     */
    @ApiModelProperty(name="resourceIds", value="资源ID集合,多个资源时用逗号(,)分隔")
    private String resourceIds;

    /**
     * 客户端密匙
     */
    @ApiModelProperty(name="clientSecret", value="客户端密匙")
    private String clientSecret;

    /**
     * 指定客户端申请的权限范围,可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔,如: "read,write". 
     */
    @ApiModelProperty(name="scope", value="指定客户端申请的权限范围,可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔,如: read,write")
    private String scope;

    /**
     * 指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: "authorization_code,password". 

     */
    @ApiModelProperty(name="authorizedGrantTypes", value="指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: authorization_code,password")
    private String authorizedGrantTypes;

    /**
     * 重定向URI
     */
    @ApiModelProperty(name="webServerRedirectUri", value="重定向URI")
    private String webServerRedirectUri;

    /**
     * 客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔
     */
    @ApiModelProperty(name="authorities", value="客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔")
    private String authorities;

    /**
     * 访问令牌有效时间值(单位:秒) 
     */
    @ApiModelProperty(name="accessTokenValidity", value="访问令牌有效时间值(单位:秒) ")
    private Integer accessTokenValidity;

    /**
     * 更新令牌有效时间值(单位:秒) 
     */
    @ApiModelProperty(name="refreshTokenValidity", value="更新令牌有效时间值(单位:秒) ")
    private Integer refreshTokenValidity;

    /**
     * 预留字段
     */
    @ApiModelProperty(name="additionalInformation", value="预留字段")
    private String additionalInformation;

    /**
     * 用户是否自动Approval操作
     */
    @ApiModelProperty(name="autoapprove", value="用户是否自动Approval操作")
    private String autoapprove;

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

    private static final long serialVersionUID = 1L;
}