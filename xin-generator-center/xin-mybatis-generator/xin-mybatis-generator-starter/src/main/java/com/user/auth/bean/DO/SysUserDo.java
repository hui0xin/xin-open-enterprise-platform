package com.user.auth.bean.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
* @Class: SysUserDo
* @Description: 系统用户信息
* @author: 系统
* @created: 2021-03-22
*/
@Data
@ApiModel(value="SysUserDo",description="系统用户信息")
public class SysUserDo implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name="id", value="主键")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(name="username", value="用户名")
    private String username;

    /**
     * 
     */
    @ApiModelProperty(name="password", value="")
    private String password;

    /**
     * 随机盐
     */
    @ApiModelProperty(name="salt", value="随机盐")
    private String salt;

    /**
     * 手机号码
     */
    @ApiModelProperty(name="phone", value="手机号码")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty(name="avatar", value="头像")
    private String avatar;

    /**
     * 部门ID
     */
    @ApiModelProperty(name="deptid", value="部门ID")
    private Integer deptid;

    /**
     * 状态（1=正常，0=删除）
     */
    @ApiModelProperty(name="state", value="状态（1=正常，0=删除）")
    private Integer state;

    /**
     * 创建人
     */
    @ApiModelProperty(name="author", value="创建人")
    private String author;

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