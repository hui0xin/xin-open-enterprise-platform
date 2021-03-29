package com.xin.commons.multi.mybatis.annotation.mapper;

import com.xin.commons.multi.mybatis.annotation.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

	@Select("SELECT * FROM t_users")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "nickName", column = "nick_name"),
			@Result(property = "passWord", column = "pass_word")
	})
	List<User> getAll();

	@Insert("INSERT INTO t_users(user_name,nick_name,pass_word) VALUES(#{userName}, #{nickName}, #{passWord})")
	void insert(User user);

	@Update("UPDATE t_users SET user_name=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(User user);

	@Delete("DELETE FROM t_users WHERE id =#{id}")
	void delete(Long id);

}