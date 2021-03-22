package com.user.auth.bean.vo;

import com.user.auth.bean.DO.SysUserDo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @class: SysUserVO
 * @Description: 系统用户信息 实体类
 * @author: 系统
 * @created: 2021-03-22
 */
@ApiModel(value="SysUserVo",description="系统用户信息 返回前端的实体")
@Data
public class SysUserVo extends SysUserDo {

}