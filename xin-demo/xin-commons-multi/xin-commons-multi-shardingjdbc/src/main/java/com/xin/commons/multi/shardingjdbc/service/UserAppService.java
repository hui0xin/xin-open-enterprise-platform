package com.xin.commons.multi.shardingjdbc.service;


import com.xin.commons.multi.shardingjdbc.bean.UserAppDo;

import java.util.List;

/**
 * @interface: UserAppService
 * @Description:  用户平台拓展表 接口
 * @author: 系统
 * @created: 2022-05-12
 */
public interface UserAppService {

    /**
     * @explain: 添加UserAppDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int insert(UserAppDo model);

    /**
     * @explain: 删除UserAppDo对象
     * @param:   id
     * @return:  int
     */
    int deleteById(Long id);

    /**
     * @explain: 修改UserAppDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int update(UserAppDo model);

    /**
     * @explain: 查询UserAppDo对象
     * @param:   id
     * @return:  UserAppDo
     */
    UserAppDo selectById(Long id);

    /**
     * @explain: 查询UserAppDo对象
     * @param:   model  对象参数
     * @return:  UserAppDo 对象
     */
    UserAppDo selectByObject(UserAppDo model);

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    List<UserAppDo> listByObject(UserAppDo model);

}
