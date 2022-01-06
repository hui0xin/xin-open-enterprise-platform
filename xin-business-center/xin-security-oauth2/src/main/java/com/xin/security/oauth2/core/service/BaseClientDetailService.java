package com.xin.security.oauth2.core.service;

import com.xin.security.oauth2.utils.PasswordEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;

/**
 * 自定义客户端认证
 * @author xin
 */
@Slf4j
public class BaseClientDetailService implements ClientDetailsService {

    // 密码模式授权模式
    public static final String GRANT_TYPE_PASSWORD = "password";
    //授权码模式  授权码模式使用到了回调地址，是最为复杂的方式，通常网站中经常出现的微博，qq第三方登录，都会采用这个形式。
    public static final String AUTHORIZATION_CODE = "authorization_code";

    private static final String REFRESH_TOKEN = "refresh_token";
    //简化授权模式
    private static final String IMPLICIT = "implicit";
    //客户端模式
    private static final String GRANT_TYPE = "client_credentials";
    //secret客户端安全码
    private static final String CLIENT_SECRET = "secret";
    //token 时间秒
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
    public static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    /**
     * 对每一个客户端做 不同的验证 配置
     * @param clientId
     * @return
     * @throws ClientRegistrationException
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails client = null;
        if("client1".equals(clientId)) {
            // 用 BCrypt 对密码编码
            String secret = passwordEncoderUtil.encode(CLIENT_SECRET);
            client = new BaseClientDetails();
            // 用来标识客户的Id
            client.setClientId(clientId);
            client.setClientSecret(secret);
            //指定哪些资源是需要授权验证的
            client.setResourceIds(Arrays.asList("order"));
            //允许授权类型 客户端模式
            client.setAuthorizedGrantTypes(Arrays.asList(GRANT_TYPE,REFRESH_TOKEN));
            //不同的client可以通过 一个scope 对应 权限集
            client.setScope(Arrays.asList("select"));
            //客户端可以使用的权限
            client.setAuthorities(AuthorityUtils.createAuthorityList("oauth2"));
            client.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
            client.setRefreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);

            ////指定可以接受令牌和授权码的重定向URIs
            //Set<String> uris = new HashSet<>();
            //uris.add("http://localhost:8080/login");
            //client.setRegisteredRedirectUri(uris);
        }
        if("client2".equals(clientId)) {
            // 用 BCrypt 对密码编码
            String secret = passwordEncoderUtil.encode(CLIENT_SECRET);
            client = new BaseClientDetails();
            // 用来标识客户的Id
            client.setClientId(clientId);
            client.setClientSecret(secret);
            //指定哪些资源是需要授权验证的
            client.setResourceIds(Arrays.asList("order"));
            //允许授权类型 密码模式
            client.setAuthorizedGrantTypes(Arrays.asList(GRANT_TYPE_PASSWORD, REFRESH_TOKEN));
            //不同的client可以通过 一个scope 对应 权限集
            client.setScope(Arrays.asList("select"));
            //客户端可以使用的权限
            client.setAuthorities(AuthorityUtils.createAuthorityList("oauth2"));
            client.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
            client.setRefreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);

            ////指定可以接受令牌和授权码的重定向URIs
            //Set<String> uris = new HashSet<>();
            //uris.add("http://localhost:8080/login");
            //client.setRegisteredRedirectUri(uris);
        }
        if("client3".equals(clientId)) {
            // 用 BCrypt 对密码编码
            String secret = passwordEncoderUtil.encode(CLIENT_SECRET);
            client = new BaseClientDetails();
            // 用来标识客户的Id
            client.setClientId(clientId);
            client.setClientSecret(secret);
            //指定哪些资源是需要授权验证的
            client.setResourceIds(Arrays.asList("order"));
            //允许授权类型 授权码模式 客户端模式  token模式 密码模式 简化授权模式
            client.setAuthorizedGrantTypes(Arrays.asList(AUTHORIZATION_CODE, GRANT_TYPE, REFRESH_TOKEN, GRANT_TYPE_PASSWORD, IMPLICIT));
            //不同的client可以通过 一个scope 对应 权限集
            client.setScope(Arrays.asList("all", "select"));
            //客户端可以使用的权限
            client.setAuthorities(AuthorityUtils.createAuthorityList("admin_role"));
            client.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
            client.setRefreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);

            ////指定可以接受令牌和授权码的重定向URIs
            //Set<String> uris = new HashSet<>();
            //uris.add("http://localhost:8080/login");
            //client.setRegisteredRedirectUri(uris);
        }
        if(client == null) {
            throw new NoSuchClientException("No client width requested id: " + clientId);
        }
        return client;
    }

}