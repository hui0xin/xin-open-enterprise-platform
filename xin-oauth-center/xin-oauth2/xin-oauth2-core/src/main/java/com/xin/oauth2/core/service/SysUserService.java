package com.xin.oauth2.core.service;

import com.xin.oauth2.bean.DO.SysUser;
import com.xin.oauth2.bean.vo.SysUserVo;

/**
* @interface: SysUserService
* @Description:  系统用户信息 接口
* @author: 系统
* @created: 2020-08-03
*/
public interface SysUserService {

    /**
     * @explain: 添加SysUser对象
     * @param:   SysUser 对象参数
     * @return:  int
     */
    int insertSelective(SysUser model);

    /**
     * @explain: 删除SysUser对象
     * @param:   id  对象参数
     * @return:  int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @explain: 修改SysUser对象
     * @param:   SysUser  对象参数
     * @return:  int
     */
    int updateByPrimaryKeySelective(SysUser model);

    /**
     * @explain: 查询SysUser对象
     * @param:   id  对象参数
     * @return:  SysUser
     */
    SysUser selectByPrimaryKey(Long id);

    SysUserVo getSysUserVo(String username);

}
