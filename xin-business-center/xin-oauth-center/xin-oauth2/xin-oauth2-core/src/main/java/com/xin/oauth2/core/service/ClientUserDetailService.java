package com.xin.oauth2.core.service;

import com.xin.oauth2.bean.DO.SysUser;
import com.xin.oauth2.bean.vo.SysUserVo;
import com.xin.oauth2.common.constatns.CommonConstant;
import com.xin.oauth2.common.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 初始化用户权限
 * 这里重写了security认证UserDetailsService 的接口方法，添加了自定义数据库密码的查询和校验。
 * @Author xin
 */
@Component("clientUserDetailService")
public class ClientUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库查询用户信息，如果有，后续需要判断密码是否正确，
        SysUserVo info = sysUserService.getSysUserVo(username);
        if (info == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        Set<String> dbAuthsSet = new HashSet<>();
        if (info.getRoles()!=null) {
            // 获取角色
            Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(CommonConstant.ROLE + role));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUser user = info.getSysUser();
        //根据查找到的用户信息判断用户是否被冻结
        boolean enabled = true;
        if(user.getState() == StateEnum.INVALID.getStatus()){
            enabled = false;
        }
        //return new User(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        return new User(username,user.getPassword(),enabled, true, true, true,authorities);
    }
}