package com.xin.security.oauth2.core.service;

import com.xin.security.oauth2.bean.DO.RoleDo;
import com.xin.security.oauth2.bean.DO.UserDo;
import com.xin.security.oauth2.core.mapper.RoleMapper;
import com.xin.security.oauth2.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //查数据库
        UserDo user = userMapper.loadUserByUsername( userName );
        if (null != user) {
            List<RoleDo> roles = roleMapper.getRolesByUserId( user.getId() );
            user.setAuthorities(roles);
        }
        return user;
    }


}
