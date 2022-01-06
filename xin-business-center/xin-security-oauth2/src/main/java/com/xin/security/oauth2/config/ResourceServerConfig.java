package com.xin.security.oauth2.config;

import com.xin.security.oauth2.exception.AuthExceptionEntryPoint;
import com.xin.security.oauth2.exception.MyAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * OAuth 资源服务器配置
 * 开启资源提供服务的配置
 * 是默认情况下spring security oauth2的http配置
 * 会被WebSecurityConfigurerAdapter的配置覆盖
 * @author xin
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 这里设置需要token验证的url
     * 这些url可以在WebSecurityConfigurerAdapter中排查掉，
     * 对于相同的url，如果二者都配置了验证
     * 则优先进入ResourceServerConfigurerAdapter,进行token验证。而不会进行
     * WebSecurityConfigurerAdapter 的 basic auth或表单认证。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/api")
                .and()
                .authorizeRequests()
                .antMatchers("/api").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(new MyAccessDeniedHandler());
    }
}
