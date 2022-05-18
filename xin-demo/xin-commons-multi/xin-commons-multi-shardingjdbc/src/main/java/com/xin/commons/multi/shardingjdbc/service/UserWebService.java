package com.xin.commons.multi.shardingjdbc.service;


import com.xin.commons.multi.shardingjdbc.bean.UserWebDo;

import java.util.List;

/**
 * @interface: UserWebService
 * @Description:  用户平台拓展表 接口
 * @author: 系统
 * @created: 2022-05-12
 */
public interface UserWebService {

    /**
     * @explain: 添加UserWebDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int insert(UserWebDo model);

    /**
     * @explain: 删除UserWebDo对象
     * @param:   id
     * @return:  int
     */
    int deleteById(Long id);

    /**
     * @explain: 修改UserWebDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int update(UserWebDo model);

    /**
     * @explain: 查询UserWebDo对象
     * @param:   id
     * @return:  UserWebDo
     */
    UserWebDo selectById(Long id);

    /**
     * @explain: 查询UserWebDo对象
     * @param:   model  对象参数
     * @return:  UserWebDo 对象
     */
    UserWebDo selectByObject(UserWebDo model);

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    List<UserWebDo> listByObject(UserWebDo model);

}
