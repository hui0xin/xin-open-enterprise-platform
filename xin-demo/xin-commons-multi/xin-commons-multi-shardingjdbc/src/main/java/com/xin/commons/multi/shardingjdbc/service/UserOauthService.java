package com.xin.commons.multi.shardingjdbc.service;


import com.xin.commons.multi.shardingjdbc.bean.UserOauthDo;

import java.util.List;

/**
 * @interface: UserOauthService
 * @Description:  用户第三方认证表 接口
 * @author: 系统
 * @created: 2022-05-12
 */
public interface UserOauthService {

    /**
     * @explain: 添加UserOauthDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int insert(UserOauthDo model);

    /**
     * @explain: 删除UserOauthDo对象
     * @param:   id
     * @return:  int
     */
    int deleteById(Long id);

    /**
     * @explain: 修改UserOauthDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    int update(UserOauthDo model);

    /**
     * @explain: 查询UserOauthDo对象
     * @param:   id
     * @return:  UserOauthDo
     */
    UserOauthDo selectById(Long id);

    /**
     * @explain: 查询UserOauthDo对象
     * @param:   model  对象参数
     * @return:  UserOauthDo 对象
     */
    UserOauthDo selectByObject(UserOauthDo model);

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    List<UserOauthDo> listByObject(UserOauthDo model);

}
