package com.xin.commons.multi.mongodb.repository.primary;

import com.xin.commons.multi.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrimaryRepository extends MongoRepository<User, String> {
}
