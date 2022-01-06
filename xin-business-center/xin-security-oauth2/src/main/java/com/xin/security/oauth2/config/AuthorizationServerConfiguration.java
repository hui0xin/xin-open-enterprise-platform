package com.xin.security.oauth2.config;

import com.xin.security.oauth2.core.service.MyUserDetailsService;
import com.xin.security.oauth2.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/***
 *  OAuth 授权认证中心配置
 *  配置客户端、token存储方式等
 *  @EnableAuthorizationServer 注解开启验证服务器
 *  提供 /oauth/authorize,
 *      /oauth/token,
 *      /oauth/check_token,
 *      /oauth/confirm_access,
 *      /oauth/error
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    //SIGNING_KEY
    private static final String SIGNING_KEY = "bcrypt";
    // 密码模式授权模式
    public static final String GRANT_TYPE_PASSWORD = "password";
    //授权码模式  授权码模式使用到了回调地址，是最为复杂的方式，通常网站中经常出现的微博，qq第三方登录，都会采用这个形式。
    public static final String AUTHORIZATION_CODE = "authorization_code";
    //token 时间秒
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
    public static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;


    /**
     * 注入权限验证控制器 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    //private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    /**
     * 注入userDetailsService，开启refresh_token需要用到
     */
    @Autowired
    private MyUserDetailsService userDetailsService;

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenStore tokenStore;

    /**
     * 自定义登录或者鉴权失败时的返回信息
     * 实现在 WebResponseExceptionTranslateConfig 类中
     */
    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    /**
     * 设置保存token的方式，一共有五种
     * RedisTokenStore redis方式
     * JwtTokenStore jwt方式
     * InMemoryTokenStore 内存方式
     * JdbcTokenStore jdbc方式
     */
    @Bean
    public TokenStore tokenStore() {
        //基于jwt实现令牌（Access Token）
        //return new JwtTokenStore(accessTokenConverter());
        //redis
        //return new RedisTokenStore(redisConnectionFactory);
        return new JdbcTokenStore( dataSource );
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /**
         * 配置oauth2服务跨域
         */
        CorsConfigurationSource source = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedHeader("*");
                corsConfiguration.addAllowedOrigin(request.getHeader(HttpHeaders.ORIGIN));
                corsConfiguration.addAllowedMethod("*");
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }
        };


        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                //.checkTokenAccess("isAuthenticated()") //isAuthenticated():排除anonymous   isFullyAuthenticated():排除anonymous以及remember-me
                //允许表单认证  这段代码在授权码模式下会导致无法根据code　获取token　
                .allowFormAuthenticationForClients()
                //client_secret的加密方式
                //.passwordEncoder(passwordEncoderUtil)
                .addTokenEndpointAuthenticationFilter(new CorsFilter(source));

    }

    /**
     * 用来配置客户端详情，在这里进行初始化，
     * 数据库在进行client_id 与 client_secret验证时，会使用这个service进行验证
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 1,默认配置
        //clients.withClientDetails(new BaseClientDetails());
        // 2,通过自定义类（BaseClientDetailService）来设置多个客户端 类型，也可以直接在这里配置客户端
        //clients.withClientDetails(new BaseClientDetailService());
        // 3,这里可以通过 查询数据库表 oauth_client_details 得到(注意 oauth回去做)
        //clients.withClientDetails(new JdbcClientDetailsService(dataSource));
        // 4,默认走数据库
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //开启密码授权类型
        endpoints.authenticationManager(authenticationManager);

        //jwt
        //endpoints.accessTokenConverter(accessTokenConverter())
        //配置TokenService参数
        //endpoints.tokenServices(tokenServices(endpoints))
        //endpoints.authorizationCodeServices(authorizationCodeStore())

        //配置token存储方式
        endpoints.tokenStore(tokenStore);
        //自定义登录或者鉴权失败时的返回信息
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
        //要使用refresh_token的话，需要额外配置userDetailsService
        endpoints.userDetailsService(userDetailsService);
        //支持GET  POST  请求获取token
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST,HttpMethod.OPTIONS);

    }

    /**
     * 重写默认的资源服务token
     * 自定义TokenServices的时候，需要设置@Primary，否则报错
     * @return
     */
    public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        // token有效期自定义设置，默认12小时
        defaultTokenServices.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
        //默认30天，这里修改
        defaultTokenServices.setRefreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
        return defaultTokenServices;
    }

    /**
     * 定义authorizationCodeStore存储方式.
     * 以保存authorization_code的授权码code
     * JdbcAuthorizationCodeServices
     * AuthorizationCodeServices
     * InMemoryAuthorizationCodeServices
     * RandomValueAuthorizationCodeServices
     * @return DB存储
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeStore() {

        return new JdbcAuthorizationCodeServices(dataSource);
    }

    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            /**
             * 自定义一些token返回的信息
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String grantType = authentication.getOAuth2Request().getGrantType();
                //只有如下两种模式才能获取到当前用户信息 授权码模式 和 密码模式
                if(AUTHORIZATION_CODE.equals(grantType) || GRANT_TYPE_PASSWORD.equals(grantType)) {
                    String userName = authentication.getUserAuthentication().getName();
                    // 自定义一些token 信息 会在获取token返回结果中展示出来
                    final Map<String, Object> additionalInformation = new HashMap<>();
                    additionalInformation.put("user_name", userName);
                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                }
                OAuth2AccessToken token = super.enhance(accessToken, authentication);
                return token;
            }
        };
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

}
