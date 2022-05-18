package com.xin.commons.multi.shardingjdbc.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @Class: UserDo
* @Description: 用户表
* @author: 系统
* @created: 2022-05-12
*/
@Data
@ApiModel(value="UserDo",description="用户表")
public class UserDo implements Serializable {
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
     * 用户编号
     */
    @ApiModelProperty(name="code", value="用户编号")
    private String code;

    /**
     * 用户平台
     */
    @ApiModelProperty(name="userType", value="用户平台")
    private Integer userType;

    /**
     * 账号
     */
    @ApiModelProperty(name="account", value="账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(name="password", value="密码")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty(name="name", value="昵称")
    private String name;

    /**
     * 真名
     */
    @ApiModelProperty(name="realName", value="真名")
    private String realName;

    /**
     * 头像
     */
    @ApiModelProperty(name="avatar", value="头像")
    private String avatar;

    /**
     * 邮箱
     */
    @ApiModelProperty(name="email", value="邮箱")
    private String email;

    /**
     * 手机
     */
    @ApiModelProperty(name="phone", value="手机")
    private String phone;

    /**
     * 生日
     */
    @ApiModelProperty(name="birthday", value="生日")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date birthday;

    /**
     * 性别
     */
    @ApiModelProperty(name="sex", value="性别")
    private Integer sex;

    /**
     * 角色id
     */
    @ApiModelProperty(name="roleId", value="角色id")
    private String roleId;

    /**
     * 部门id
     */
    @ApiModelProperty(name="deptId", value="部门id")
    private String deptId;

    /**
     * 岗位id
     */
    @ApiModelProperty(name="postId", value="岗位id")
    private String postId;

    /**
     * 创建人
     */
    @ApiModelProperty(name="createUser", value="创建人")
    private Long createUser;

    /**
     * 创建部门
     */
    @ApiModelProperty(name="createDept", value="创建部门")
    private Long createDept;

    /**
     * 创建时间
     */
    @ApiModelProperty(name="createTime", value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    @ApiModelProperty(name="updateUser", value="修改人")
    private Long updateUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(name="updateTime", value="修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 状态
     */
    @ApiModelProperty(name="status", value="状态")
    private Integer status;

    /**
     * 是否已删除
     */
    @ApiModelProperty(name="isDeleted", value="是否已删除")
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}