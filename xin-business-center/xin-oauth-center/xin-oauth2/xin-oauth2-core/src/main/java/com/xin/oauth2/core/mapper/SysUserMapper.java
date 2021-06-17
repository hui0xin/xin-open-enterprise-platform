package com.xin.oauth2.core.mapper;

import com.xin.oauth2.bean.DO.SysUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
* @Interface: SysUserMapper
* @Description: 系统用户信息 Mapper
* @author: 系统
* @created: 2020-08-03
*/
@Mapper
public interface SysUserMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    @Delete({
        "delete from sys_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     * @param record
     */
    @Insert({
        "insert into sys_user (id, username, ",
        "password, salt, ",
        "phone, avatar, deptId, ",
        "state, author, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=BIGINT}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{salt,jdbcType=VARCHAR}, ",
        "#{phone,jdbcType=VARCHAR}, #{avatar,jdbcType=VARCHAR}, #{deptid,jdbcType=INTEGER}, ",
        "#{state,jdbcType=INTEGER}, #{author,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(SysUser record);

    /**
     * 插入数据库记录
     * @param record
     */
    @InsertProvider(type=SysUserSqlProvider.class, method="insertSelective")
    int insertSelective(SysUser record);

    /**
     * 根据主键id查询
     * @param id
     */
    @Select({
        "select",
        "id, username, password, salt, phone, avatar, deptId, state, author, create_time, ",
        "update_time",
        "from sys_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="salt", property="salt", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
        @Result(column="deptId", property="deptid", jdbcType=JdbcType.INTEGER),
        @Result(column="state", property="state", jdbcType=JdbcType.INTEGER),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    SysUser selectByPrimaryKey(Long id);

    /**
     * 根据主键id查询
     * @param username
     */
    @Select({
            "select",
            "id, username, password, salt, phone, avatar, deptId, state, author, create_time, ",
            "update_time",
            "from sys_user",
            "where username = #{username,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="salt", property="salt", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
            @Result(column="deptId", property="deptid", jdbcType=JdbcType.INTEGER),
            @Result(column="state", property="state", jdbcType=JdbcType.INTEGER),
            @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    SysUser selectByUserName(String username);

    /**
     * 修改数据
     * @param record
     */
    @UpdateProvider(type=SysUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 修改数据
     * @param record
     */
    @Update({
        "update sys_user",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "salt = #{salt,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "avatar = #{avatar,jdbcType=VARCHAR},",
          "deptId = #{deptid,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "author = #{author,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SysUser record);
}