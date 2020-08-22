package com.xin.commons.multi.jpa.scanpackage.service;

import com.xin.commons.multi.jpa.scanpackage.model.User;

/**
 * User Service Interface
 */
public interface IUserService {

    User save(User user);

    User save(String name, Integer age);
}
