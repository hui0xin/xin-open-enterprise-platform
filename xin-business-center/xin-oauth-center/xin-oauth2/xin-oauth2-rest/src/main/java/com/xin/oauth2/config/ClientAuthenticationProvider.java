package com.xin.oauth2.config;

import com.xin.oauth2.utils.PasswordEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * AuthenticationManagerBuilder中的AuthenticationProvider是进行认证的核心
 * @Author xin
 */
@Slf4j
@Component("clientAuthenticationProvider")
public class ClientAuthenticationProvider implements AuthenticationProvider {

    @Resource(name = "clientUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    /**
     *authentication是前台拿过来的号码密码bean  主要验证流程代码  注意这儿懒得用加密验证！！！
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("用户输入的用户名是：{}, 密码是：{}" ,authentication.getName(),authentication.getCredentials());
        // 根据用户输入的用户名获取该用户名已经在服务器上存在的用户详情，如果没有则返回null
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        try {
            log.info("服务器上已经保存的用户名是：{}, 密码是：{}，权限是：{}",userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
            if(passwordEncoderUtil.encode(authentication.getCredentials().toString())
                    .equals(userDetails.getPassword())){
                log.info("LOGIN SUCCESS !!!!!!!!!!!!!!!!!!!");
                //分别返回用户实体   输入的密码   以及用户的权限
                return new UsernamePasswordAuthenticationToken(userDetails,authentication.getCredentials(),userDetails.getAuthorities());
            }
        } catch (Exception e){
            log.error("author-faileds:" , ExceptionUtils.getStackTrace(e));
            throw e;
        }
        //如果验证不同过则返回null或者抛出异常
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}