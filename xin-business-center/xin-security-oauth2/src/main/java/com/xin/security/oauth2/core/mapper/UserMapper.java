package com.xin.security.oauth2.core.mapper;

import com.xin.security.oauth2.bean.DO.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select( "select id , username , password from user where username = #{username}" )
    UserDo loadUserByUsername(@Param("username") String username);

}
