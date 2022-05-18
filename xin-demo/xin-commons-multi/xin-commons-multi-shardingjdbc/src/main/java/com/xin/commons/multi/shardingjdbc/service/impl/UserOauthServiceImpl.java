package com.xin.commons.multi.shardingjdbc.service.impl;

import com.xin.commons.multi.shardingjdbc.bean.UserOauthDo;
import com.xin.commons.multi.shardingjdbc.mapper.UserOauthMapper;
import com.xin.commons.multi.shardingjdbc.service.UserOauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @class: UserOauthServiceImpl
 * @Description:  用户第三方认证表 接口实现
 * @author: 系统
 * @created: 2022-05-12
 */
@Slf4j
@Service
public class UserOauthServiceImpl implements UserOauthService {

    @Autowired
    private UserOauthMapper userOauthMapper;

    /**
     * @explain: 添加UserOauthDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int insert(UserOauthDo model) {
        return userOauthMapper.insert(model);
    }

    /**
     * @explain: 删除UserOauthDo对象
     * @param:   id
     * @return:  int
     */
    @Override
    public int deleteById(Long id) {
        return userOauthMapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改UserOauthDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int update(UserOauthDo model) {
        return userOauthMapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询UserOauthDo对象
     * @param:   id
     * @return:  UserOauthDo
     */
    @Override
    public UserOauthDo selectById(Long id) {
        return userOauthMapper.selectByPrimaryKey(id);
    }

    /**
     * @explain: 查询UserOauthDo对象
     * @param:   model 对象参数
     * @return:  UserOauthDo 对象
     */
    @Override
    public UserOauthDo selectByObject(UserOauthDo model) {
        return userOauthMapper.selectByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    @Override
    public List<UserOauthDo> listByObject(UserOauthDo model) {
        return userOauthMapper.selectByPrimaryKeySelectiveList(model);
    }

}
