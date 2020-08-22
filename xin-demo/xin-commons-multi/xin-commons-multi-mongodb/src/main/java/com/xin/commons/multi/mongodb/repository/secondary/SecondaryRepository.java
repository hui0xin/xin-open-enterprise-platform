package com.xin.commons.multi.mongodb.repository.secondary;

import com.xin.commons.multi.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecondaryRepository extends MongoRepository<User, String> {
}
