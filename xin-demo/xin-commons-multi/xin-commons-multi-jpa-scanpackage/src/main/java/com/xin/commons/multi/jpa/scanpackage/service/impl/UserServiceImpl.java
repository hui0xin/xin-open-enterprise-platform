package com.xin.commons.multi.jpa.scanpackage.service.impl;

import com.xin.commons.multi.jpa.scanpackage.dao.first.UserRepository;
import com.xin.commons.multi.jpa.scanpackage.model.User;
import com.xin.commons.multi.jpa.scanpackage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(transactionManager = "firstTransactionManager")
    public User save(String name, Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return save(user);
    }
}
