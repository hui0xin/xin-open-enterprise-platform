package com.xin.commons.multi.shardingjdbc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @Class: UserDeptDo
* @Description: 用户部门表
* @author: 系统
* @created: 2022-05-12
*/
@Data
@ApiModel(value="UserDeptDo",description="用户部门表")
public class UserDeptDo implements Serializable {
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
     * 部门ID
     */
    @ApiModelProperty(name="deptId", value="部门ID")
    private Long deptId;

    private static final long serialVersionUID = 1L;
}