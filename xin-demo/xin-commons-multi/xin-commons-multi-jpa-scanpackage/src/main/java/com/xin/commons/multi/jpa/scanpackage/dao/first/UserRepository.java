package com.xin.commons.multi.jpa.scanpackage.dao.first;

import com.xin.commons.multi.jpa.scanpackage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User Repository
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
