package com.user.auth.service;

import com.user.auth.bean.DO.SysUserDo;
import java.util.List;

/**
 * @interface: SysUserService
 * @Description:  系统用户信息 接口
 * @author: 系统
 * @created: 2021-03-22
 */
public interface SysUserService {

    /**
     * @explain: 添加SysUserDo对象
     * @param:   SysUserDo 对象参数
     * @return:  int
     */
    int insert(SysUserDo model);

    /**
     * @explain: 删除SysUserDo对象
     * @param:   id  对象参数
     * @return:  int
     */
    int deleteById(Long id);

    /**
     * @explain: 修改SysUserDo对象
     * @param:   SysUserDo  对象参数
     * @return:  int
     */
    int update(SysUserDo model);

    /**
     * @explain: 查询SysUserDo对象
     * @param:   id  id
     * @return:  SysUserDo
     */
    SysUserDo selectByPrimaryKey(Long id);

    /**
     * @explain: 查询SysUserDo对象
     * @param:   SysUserDo  对象参数
     * @return:  SysUserDo 对象
     */
    SysUserDo selectByObject(SysUserDo model);

    /**
     * @explain: 查询列表
     * @param:  SysUserDo  对象参数
     * @return: list
     */
    List<SysUserDo> listByObject(SysUserDo model);

}
