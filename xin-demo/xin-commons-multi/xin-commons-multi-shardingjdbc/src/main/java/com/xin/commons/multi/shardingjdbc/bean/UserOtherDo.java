package com.xin.commons.multi.shardingjdbc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @Class: UserOtherDo
* @Description: 用户平台拓展表
* @author: 系统
* @created: 2022-05-12
*/
@Data
@ApiModel(value="UserOtherDo",description="用户平台拓展表")
public class UserOtherDo implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name="id", value="主键")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty(name="userId", value="用户ID")
    private Long userId;

    /**
     * 用户拓展信息
     */
    @ApiModelProperty(name="userExt", value="用户拓展信息")
    private String userExt;

    private static final long serialVersionUID = 1L;
}