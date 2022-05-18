package com.xin.commons.multi.shardingjdbc.service.impl;

import com.xin.commons.multi.shardingjdbc.bean.UserWebDo;
import com.xin.commons.multi.shardingjdbc.mapper.UserWebMapper;
import com.xin.commons.multi.shardingjdbc.service.UserWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @class: UserWebServiceImpl
 * @Description:  用户平台拓展表 接口实现
 * @author: 系统
 * @created: 2022-05-12
 */
@Slf4j
@Service
public class UserWebServiceImpl implements UserWebService {

    @Autowired
    private UserWebMapper userWebMapper;

    /**
     * @explain: 添加UserWebDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int insert(UserWebDo model) {
        return userWebMapper.insert(model);
    }

    /**
     * @explain: 删除UserWebDo对象
     * @param:   id
     * @return:  int
     */
    @Override
    public int deleteById(Long id) {
        return userWebMapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改UserWebDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int update(UserWebDo model) {
        return userWebMapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询UserWebDo对象
     * @param:   id
     * @return:  UserWebDo
     */
    @Override
    public UserWebDo selectById(Long id) {
        return userWebMapper.selectByPrimaryKey(id);
    }

    /**
     * @explain: 查询UserWebDo对象
     * @param:   model 对象参数
     * @return:  UserWebDo 对象
     */
    @Override
    public UserWebDo selectByObject(UserWebDo model) {
        return userWebMapper.selectByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    @Override
    public List<UserWebDo> listByObject(UserWebDo model) {
        return userWebMapper.selectByPrimaryKeySelectiveList(model);
    }

}
