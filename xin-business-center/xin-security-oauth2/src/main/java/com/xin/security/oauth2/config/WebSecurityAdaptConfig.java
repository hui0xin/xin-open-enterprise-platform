//package com.xin.security.oauth2.config;
//
//import com.xin.oauth2.utils.PasswordEncoderUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import javax.annotation.Resource;
//
///**
// * @EnableGlobalMethodSecurity 开启三种可以在方法上面加权限控制的注解
// * 是默认情况下spring security的http配置 优于ResourceServerConfigurerAdapter的配置
// * @Author xin
// */
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
//public class WebSecurityAdaptConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 获取用户的验证配置类
//     */
//    @Resource(name = "clientUserDetailService")
//    private UserDetailsService userDetailsService;
//
//    /**
//     * 密码验证处理器
//     */
//    @Resource(name = "clientAuthenticationProvider")
//    private ClientAuthenticationProvider clientAuthenticationProvider;
//
//    @Autowired
//    private PasswordEncoderUtil passwordEncoderUtil;
//
//    /**
//     * spring security设置
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()//禁用csrf （csrf会拦截所有post请求）
//                .headers().frameOptions().disable()//允许使用iframe
//                .and().authorizeRequests()
//                //定义这两个链接不需要鉴权
//                .antMatchers("/oauth/token" , "/oauth/check_token").permitAll()
//                .anyRequest().authenticated() //其他的都需要登录
//                //.antMatchers("/sys/**").hasRole("admin")///sys/**下的请求 需要有admin的角色
//                .and()
//                //如果未登录则跳转登录的页面   这儿可以控制登录成功和登录失败跳转的页面
//                .formLogin().loginPage("/login")
//                .usernameParameter("username").passwordParameter("password").permitAll()//定义号码与密码的parameter
//                .and()
//                .csrf().disable();//防止跨站请求  spring security中默认开启
//
//    }
//
//    /**
//     * 权限管理器  AuthorizationServerConfigurerAdapter认证中心需要的AuthenticationManager需要
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //目的是为了前端获取数据时获取到整个form-data的数据,提供验证器
//        auth
//                .authenticationProvider(clientAuthenticationProvider)
//                //配置登录user验证处理器  以及密码加密器  好让认证中心进行验证
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoderUtil);
//    }
//
//    /**
//     * 需要配置这个支持password模式
//     * support password grant type
//     *
//     * @return
//     * @throws Exception
//     */
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return authenticationManager();
//    }
//}