package com.xin.commons.multi.shardingjdbc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @Class: UserOauthDo
* @Description: 用户第三方认证表
* @author: 系统
* @created: 2022-05-12
*/
@Data
@ApiModel(value="UserOauthDo",description="用户第三方认证表")
public class UserOauthDo implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name="id", value="主键")
    private Long id;

    /**
     * 租户ID
     */
    @ApiModelProperty(name="tenantId", value="租户ID")
    private String tenantId;

    /**
     * 第三方系统用户ID
     */
    @ApiModelProperty(name="uuid", value="第三方系统用户ID")
    private String uuid;

    /**
     * 用户ID
     */
    @ApiModelProperty(name="userId", value="用户ID")
    private Long userId;

    /**
     * 账号
     */
    @ApiModelProperty(name="username", value="账号")
    private String username;

    /**
     * 用户名
     */
    @ApiModelProperty(name="nickname", value="用户名")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(name="avatar", value="头像")
    private String avatar;

    /**
     * 应用主页
     */
    @ApiModelProperty(name="blog", value="应用主页")
    private String blog;

    /**
     * 公司名
     */
    @ApiModelProperty(name="company", value="公司名")
    private String company;

    /**
     * 地址
     */
    @ApiModelProperty(name="location", value="地址")
    private String location;

    /**
     * 邮件
     */
    @ApiModelProperty(name="email", value="邮件")
    private String email;

    /**
     * 备注
     */
    @ApiModelProperty(name="remark", value="备注")
    private String remark;

    /**
     * 性别
     */
    @ApiModelProperty(name="gender", value="性别")
    private String gender;

    /**
     * 来源
     */
    @ApiModelProperty(name="source", value="来源")
    private String source;

    private static final long serialVersionUID = 1L;
}