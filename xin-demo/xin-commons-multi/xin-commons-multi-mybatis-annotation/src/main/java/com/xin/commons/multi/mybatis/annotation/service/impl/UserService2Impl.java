package com.xin.commons.multi.mybatis.annotation.service.impl;

import com.xin.commons.multi.mybatis.annotation.bean.User;
import com.xin.commons.multi.mybatis.annotation.config.DBConfig;
import com.xin.commons.multi.mybatis.annotation.config.DataSource;
import com.xin.commons.multi.mybatis.annotation.mapper.UserMapper;
import com.xin.commons.multi.mybatis.annotation.service.UserService;
import com.xin.commons.multi.mybatis.annotation.service.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService2")
public class UserService2Impl implements UserService2 {

    @Autowired
    private UserService userService;

    /**
     * 注意如果 事物@Transactional和
     * userService.add1(user)
     * userService.add2(user)
     * userService.add3(user)
     * 任何一个在一哥类中，事物都不生效
     *
     * @param user
     */
    @Override
    @Transactional
    public void add(User user) {
        userService.add1(user);
        userService.add2(user);
        userService.add3(user);
        return ;
    }
}