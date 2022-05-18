package com.xin.commons.multi.shardingjdbc.service.impl;


import com.xin.commons.multi.shardingjdbc.bean.UserOtherDo;
import com.xin.commons.multi.shardingjdbc.mapper.UserOtherMapper;
import com.xin.commons.multi.shardingjdbc.service.UserOtherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @class: UserOtherServiceImpl
 * @Description:  用户平台拓展表 接口实现
 * @author: 系统
 * @created: 2022-05-12
 */
@Slf4j
@Service
public class UserOtherServiceImpl implements UserOtherService {

    @Autowired
    private UserOtherMapper userOtherMapper;

    /**
     * @explain: 添加UserOtherDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int insert(UserOtherDo model) {
        return userOtherMapper.insert(model);
    }

    /**
     * @explain: 删除UserOtherDo对象
     * @param:   id
     * @return:  int
     */
    @Override
    public int deleteById(Long id) {
        return userOtherMapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改UserOtherDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int update(UserOtherDo model) {
        return userOtherMapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询UserOtherDo对象
     * @param:   id
     * @return:  UserOtherDo
     */
    @Override
    public UserOtherDo selectById(Long id) {
        return userOtherMapper.selectByPrimaryKey(id);
    }

    /**
     * @explain: 查询UserOtherDo对象
     * @param:   model 对象参数
     * @return:  UserOtherDo 对象
     */
    @Override
    public UserOtherDo selectByObject(UserOtherDo model) {
        return userOtherMapper.selectByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    @Override
    public List<UserOtherDo> listByObject(UserOtherDo model) {
        return userOtherMapper.selectByPrimaryKeySelectiveList(model);
    }

}
