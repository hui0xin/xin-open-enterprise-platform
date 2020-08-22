package com.xin.oauth2.core.mapper;

import com.xin.oauth2.bean.DO.OauthClientDetails;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
* @Interface: OauthClientDetailsMapper
* @Description: oauth客户端信息 Mapper
* @author: 系统
* @created: 2020-08-03
*/
@Mapper
public interface OauthClientDetailsMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    @Delete({
        "delete from oauth_client_details",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     * @param record
     */
    @Insert({
        "insert into oauth_client_details (id, client_id, ",
        "resource_ids, client_secret, ",
        "scope, authorized_grant_types, ",
        "web_server_redirect_uri, authorities, ",
        "access_token_validity, refresh_token_validity, ",
        "additional_information, autoapprove, ",
        "create_time, update_time)",
        "values (#{id,jdbcType=BIGINT}, #{clientId,jdbcType=VARCHAR}, ",
        "#{resourceIds,jdbcType=VARCHAR}, #{clientSecret,jdbcType=VARCHAR}, ",
        "#{scope,jdbcType=VARCHAR}, #{authorizedGrantTypes,jdbcType=VARCHAR}, ",
        "#{webServerRedirectUri,jdbcType=VARCHAR}, #{authorities,jdbcType=VARCHAR}, ",
        "#{accessTokenValidity,jdbcType=INTEGER}, #{refreshTokenValidity,jdbcType=INTEGER}, ",
        "#{additionalInformation,jdbcType=VARCHAR}, #{autoapprove,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(OauthClientDetails record);

    /**
     * 插入数据库记录
     * @param record
     */
    @InsertProvider(type=OauthClientDetailsSqlProvider.class, method="insertSelective")
    int insertSelective(OauthClientDetails record);

    /**
     * 根据主键id查询
     * @param id
     */
    @Select({
        "select",
        "id, client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, ",
        "authorities, access_token_validity, refresh_token_validity, additional_information, ",
        "autoapprove, create_time, update_time",
        "from oauth_client_details",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="client_id", property="clientId", jdbcType=JdbcType.VARCHAR),
        @Result(column="resource_ids", property="resourceIds", jdbcType=JdbcType.VARCHAR),
        @Result(column="client_secret", property="clientSecret", jdbcType=JdbcType.VARCHAR),
        @Result(column="scope", property="scope", jdbcType=JdbcType.VARCHAR),
        @Result(column="authorized_grant_types", property="authorizedGrantTypes", jdbcType=JdbcType.VARCHAR),
        @Result(column="web_server_redirect_uri", property="webServerRedirectUri", jdbcType=JdbcType.VARCHAR),
        @Result(column="authorities", property="authorities", jdbcType=JdbcType.VARCHAR),
        @Result(column="access_token_validity", property="accessTokenValidity", jdbcType=JdbcType.INTEGER),
        @Result(column="refresh_token_validity", property="refreshTokenValidity", jdbcType=JdbcType.INTEGER),
        @Result(column="additional_information", property="additionalInformation", jdbcType=JdbcType.VARCHAR),
        @Result(column="autoapprove", property="autoapprove", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    OauthClientDetails selectByPrimaryKey(Long id);

    /**
     * 修改数据
     * @param record
     */
    @UpdateProvider(type=OauthClientDetailsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(OauthClientDetails record);

    /**
     * 修改数据
     * @param record
     */
    @Update({
        "update oauth_client_details",
        "set client_id = #{clientId,jdbcType=VARCHAR},",
          "resource_ids = #{resourceIds,jdbcType=VARCHAR},",
          "client_secret = #{clientSecret,jdbcType=VARCHAR},",
          "scope = #{scope,jdbcType=VARCHAR},",
          "authorized_grant_types = #{authorizedGrantTypes,jdbcType=VARCHAR},",
          "web_server_redirect_uri = #{webServerRedirectUri,jdbcType=VARCHAR},",
          "authorities = #{authorities,jdbcType=VARCHAR},",
          "access_token_validity = #{accessTokenValidity,jdbcType=INTEGER},",
          "refresh_token_validity = #{refreshTokenValidity,jdbcType=INTEGER},",
          "additional_information = #{additionalInformation,jdbcType=VARCHAR},",
          "autoapprove = #{autoapprove,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(OauthClientDetails record);
}