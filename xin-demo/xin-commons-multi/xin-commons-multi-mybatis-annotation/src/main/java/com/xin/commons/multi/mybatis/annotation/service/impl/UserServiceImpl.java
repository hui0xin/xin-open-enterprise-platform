package com.xin.commons.multi.mybatis.annotation.service.impl;

import com.xin.commons.multi.mybatis.annotation.bean.User;
import com.xin.commons.multi.mybatis.annotation.config.DBConfig;
import com.xin.commons.multi.mybatis.annotation.config.DataSource;
import com.xin.commons.multi.mybatis.annotation.mapper.User1Mapper;
import com.xin.commons.multi.mybatis.annotation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private User1Mapper user1Mapper;

    /**
     * 这里 DataSource 注解为了切换数据源
     * @return
     */
    @DataSource(DBConfig.dataSource1)
    @Override
    public List<User> getAll1() {
        return user1Mapper.getAll();
    }

    /**
     * 这里 DataSource 注解为了切换数据源
     * @return
     */
    @DataSource(DBConfig.dataSource2)
    @Override
    public List<User> getAll2() {
        return user1Mapper.getAll();
    }
}