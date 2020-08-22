package com.xin.oauth2.core.mapper;

import com.xin.oauth2.bean.DO.OauthClientDetails;
import org.apache.ibatis.jdbc.SQL;

public class OauthClientDetailsSqlProvider {

    public String insertSelective(OauthClientDetails record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("oauth_client_details");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getClientId() != null) {
            sql.VALUES("client_id", "#{clientId,jdbcType=VARCHAR}");
        }
        
        if (record.getResourceIds() != null) {
            sql.VALUES("resource_ids", "#{resourceIds,jdbcType=VARCHAR}");
        }
        
        if (record.getClientSecret() != null) {
            sql.VALUES("client_secret", "#{clientSecret,jdbcType=VARCHAR}");
        }
        
        if (record.getScope() != null) {
            sql.VALUES("scope", "#{scope,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorizedGrantTypes() != null) {
            sql.VALUES("authorized_grant_types", "#{authorizedGrantTypes,jdbcType=VARCHAR}");
        }
        
        if (record.getWebServerRedirectUri() != null) {
            sql.VALUES("web_server_redirect_uri", "#{webServerRedirectUri,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorities() != null) {
            sql.VALUES("authorities", "#{authorities,jdbcType=VARCHAR}");
        }
        
        if (record.getAccessTokenValidity() != null) {
            sql.VALUES("access_token_validity", "#{accessTokenValidity,jdbcType=INTEGER}");
        }
        
        if (record.getRefreshTokenValidity() != null) {
            sql.VALUES("refresh_token_validity", "#{refreshTokenValidity,jdbcType=INTEGER}");
        }
        
        if (record.getAdditionalInformation() != null) {
            sql.VALUES("additional_information", "#{additionalInformation,jdbcType=VARCHAR}");
        }
        
        if (record.getAutoapprove() != null) {
            sql.VALUES("autoapprove", "#{autoapprove,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(OauthClientDetails record) {
        SQL sql = new SQL();
        sql.UPDATE("oauth_client_details");
        
        if (record.getClientId() != null) {
            sql.SET("client_id = #{clientId,jdbcType=VARCHAR}");
        }
        
        if (record.getResourceIds() != null) {
            sql.SET("resource_ids = #{resourceIds,jdbcType=VARCHAR}");
        }
        
        if (record.getClientSecret() != null) {
            sql.SET("client_secret = #{clientSecret,jdbcType=VARCHAR}");
        }
        
        if (record.getScope() != null) {
            sql.SET("scope = #{scope,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorizedGrantTypes() != null) {
            sql.SET("authorized_grant_types = #{authorizedGrantTypes,jdbcType=VARCHAR}");
        }
        
        if (record.getWebServerRedirectUri() != null) {
            sql.SET("web_server_redirect_uri = #{webServerRedirectUri,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthorities() != null) {
            sql.SET("authorities = #{authorities,jdbcType=VARCHAR}");
        }
        
        if (record.getAccessTokenValidity() != null) {
            sql.SET("access_token_validity = #{accessTokenValidity,jdbcType=INTEGER}");
        }
        
        if (record.getRefreshTokenValidity() != null) {
            sql.SET("refresh_token_validity = #{refreshTokenValidity,jdbcType=INTEGER}");
        }
        
        if (record.getAdditionalInformation() != null) {
            sql.SET("additional_information = #{additionalInformation,jdbcType=VARCHAR}");
        }
        
        if (record.getAutoapprove() != null) {
            sql.SET("autoapprove = #{autoapprove,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}