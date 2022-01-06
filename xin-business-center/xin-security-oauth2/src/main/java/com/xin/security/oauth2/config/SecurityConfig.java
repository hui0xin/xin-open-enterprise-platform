package com.xin.security.oauth2.config;

import com.xin.security.oauth2.core.service.MyUserDetailsService;
import com.xin.security.oauth2.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @EnableGlobalMethodSecurity 开启三种可以在方法上面加权限控制的注解
 * 是默认情况下spring security的http配置 优于ResourceServerConfigurerAdapter的配置
 * @Author xin
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userService;
//    /**
//     * 获取用户的验证配置类
//     */
//    @Resource(name = "clientUserDetailService")
//    private UserDetailsService userDetailsService;

    /**
     * 密码验证处理器
     */
//    @Resource(name = "clientAuthenticationProvider")
//    private ClientAuthenticationProvider clientAuthenticationProvider;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //校验用户
        auth.userDetailsService( userService ).passwordEncoder( new PasswordEncoder() {
            //对密码进行加密
            @Override
            public String encode(CharSequence charSequence) {
                System.out.println(charSequence.toString());
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }
            //对密码进行判断匹配
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                boolean res = s.equals( encode );
                return res;
            }
        } );

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf （csrf会拦截所有post请求）
        http.csrf().disable();
        //允许使用iframe
        http.headers().frameOptions().disable();
        http.requestMatchers()
                .antMatchers("/oauth/**","/login","/login-error")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .formLogin().loginPage( "/login" ).failureUrl( "/login-error" );
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return Objects.equals(charSequence.toString(),s);
            }
        };
    }

}
