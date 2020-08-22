package com.xin.commons.multi.jpa.annotation.service;

import com.xin.commons.multi.jpa.annotation.model.User;
import com.xin.commons.multi.jpa.annotation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    /**
     * 走master库
     * @param user
     * @return
     */
    public User addOne(User user) {
        return userRepository.save(user);
    }

    /**
     * 以下方法开头，都走 selave库
     * method.startsWith("find") ||
     * method.startsWith("select") ||
     * method.startsWith("query") ||
     * method.startsWith("search")) {
     * @param userId
     * @return
     */
    public List<User> findById(Long userId) {

        return userRepository.findAll();
    }

}
