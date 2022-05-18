package com.xin.commons.multi.shardingjdbc.service.impl;

import com.xin.commons.multi.shardingjdbc.bean.UserAppDo;
import com.xin.commons.multi.shardingjdbc.mapper.UserAppMapper;
import com.xin.commons.multi.shardingjdbc.service.UserAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @class: UserAppServiceImpl
 * @Description:  用户平台拓展表 接口实现
 * @author: 系统
 * @created: 2022-05-12
 */
@Slf4j
@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserAppMapper userAppMapper;

    /**
     * @explain: 添加UserAppDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int insert(UserAppDo model) {
        return userAppMapper.insert(model);
    }

    /**
     * @explain: 删除UserAppDo对象
     * @param:   id
     * @return:  int
     */
    @Override
    public int deleteById(Long id) {
        return userAppMapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改UserAppDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int update(UserAppDo model) {
        return userAppMapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询UserAppDo对象
     * @param:   id
     * @return:  UserAppDo
     */
    @Override
    public UserAppDo selectById(Long id) {
        return userAppMapper.selectByPrimaryKey(id);
    }

    /**
     * @explain: 查询UserAppDo对象
     * @param:   model 对象参数
     * @return:  UserAppDo 对象
     */
    @Override
    public UserAppDo selectByObject(UserAppDo model) {
        return userAppMapper.selectByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    @Override
    public List<UserAppDo> listByObject(UserAppDo model) {
        return userAppMapper.selectByPrimaryKeySelectiveList(model);
    }

}
