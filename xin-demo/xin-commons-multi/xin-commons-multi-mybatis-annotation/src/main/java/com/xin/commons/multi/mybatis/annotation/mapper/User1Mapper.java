package com.xin.commons.multi.mybatis.annotation.mapper;


import com.xin.commons.multi.mybatis.annotation.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface User1Mapper {


	@Select("SELECT * FROM users")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "nickName", column = "nick_name"),
			@Result(property = "passWord", column = "pass_word")
	})
	List<User> getAll();

	@Select("SELECT * FROM users WHERE id = #{id}")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "nickName", column = "nick_name"),
			@Result(property = "passWord", column = "pass_word")
	})
	User getOne(Long id);

	@Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
	void insert(User user);

	@Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(User user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);

}