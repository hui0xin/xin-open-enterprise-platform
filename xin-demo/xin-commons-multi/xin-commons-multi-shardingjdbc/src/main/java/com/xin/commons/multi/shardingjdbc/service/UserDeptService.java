package com.xin.commons.multi.shardingjdbc.service;


import com.xin.commons.multi.shardingjdbc.bean.UserDeptDo;

import java.util.List;

/**
 * @interface: UserDeptService
 * @Description:  用户部门表 接口
 * @author: 系统
 * @created: 2022-05-12
 */
public interface UserDeptService {

    /**
     * @explain: 添加UserDeptDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int insert(UserDeptDo model);

    /**
     * @explain: 删除UserDeptDo对象
     * @param:   id
     * @return:  int
     */
    int deleteById(Long id);

    /**
     * @explain: 修改UserDeptDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int update(UserDeptDo model);

    /**
     * @explain: 查询UserDeptDo对象
     * @param:   id
     * @return:  UserDeptDo
     */
    UserDeptDo selectById(Long id);

    /**
     * @explain: 查询UserDeptDo对象
     * @param:   model  对象参数
     * @return:  UserDeptDo 对象
     */
    UserDeptDo selectByObject(UserDeptDo model);

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    List<UserDeptDo> listByObject(UserDeptDo model);

}
