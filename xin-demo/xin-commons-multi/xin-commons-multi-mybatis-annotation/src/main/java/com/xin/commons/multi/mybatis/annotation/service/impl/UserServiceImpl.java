package com.xin.commons.multi.mybatis.annotation.service.impl;

import com.xin.commons.multi.mybatis.annotation.bean.User;
import com.xin.commons.multi.mybatis.annotation.config.DBConfig;
import com.xin.commons.multi.mybatis.annotation.config.DataSource;
import com.xin.commons.multi.mybatis.annotation.mapper.UserMapper;
import com.xin.commons.multi.mybatis.annotation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 这里 DataSource 注解为了切换数据源
     * @return
     */
    @DataSource(DBConfig.dataSource1)
    @Override
    public List<User> getAll1() {
        return userMapper.getAll();
    }

    /**
     * 这里 DataSource 注解为了切换数据源
     * @return
     */
    @DataSource(DBConfig.dataSource2)
    @Override
    public List<User> getAll2() {
        return userMapper.getAll();
    }

    @Override
    @DataSource(DBConfig.dataSource1)
    public void add1(User user) {
        userMapper.insert(user);
        return ;
    }

    @Override
    @DataSource(DBConfig.dataSource2)
    public void add2(User user) {
        userMapper.insert(user);
        return ;
    }

    @Override
    @DataSource(DBConfig.dataSource3)
    public void add3(User user) {
        int a = 1/0;
        userMapper.insert(user);
        return ;
    }

}