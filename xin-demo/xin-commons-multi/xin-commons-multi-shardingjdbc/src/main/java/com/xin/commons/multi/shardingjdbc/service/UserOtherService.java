package com.xin.commons.multi.shardingjdbc.service;


import com.xin.commons.multi.shardingjdbc.bean.UserOtherDo;

import java.util.List;

/**
 * @interface: UserOtherService
 * @Description:  用户平台拓展表 接口
 * @author: 系统
 * @created: 2022-05-12
 */
public interface UserOtherService {

    /**
     * @explain: 添加UserOtherDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int insert(UserOtherDo model);

    /**
     * @explain: 删除UserOtherDo对象
     * @param:   id
     * @return:  int
     */
    int deleteById(Long id);

    /**
     * @explain: 修改UserOtherDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int update(UserOtherDo model);

    /**
     * @explain: 查询UserOtherDo对象
     * @param:   id
     * @return:  UserOtherDo
     */
    UserOtherDo selectById(Long id);

    /**
     * @explain: 查询UserOtherDo对象
     * @param:   model  对象参数
     * @return:  UserOtherDo 对象
     */
    UserOtherDo selectByObject(UserOtherDo model);

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    List<UserOtherDo> listByObject(UserOtherDo model);

}
