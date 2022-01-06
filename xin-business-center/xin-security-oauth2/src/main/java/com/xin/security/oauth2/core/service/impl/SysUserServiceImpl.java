package com.xin.security.oauth2.core.service.impl;

import com.xin.security.oauth2.bean.DO.SysUser;
import com.xin.security.oauth2.bean.vo.SysUserVo;
import com.xin.security.oauth2.core.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {


    /**
     * @explain: 添加SysUser对象
     * @param:   SysUser 对象参数
     * @return:  int
     */
    @Override
    public int insertSelective(SysUser model) {


        return 0;
    }

    /**
     * @explain: 删除SysUser对象
     * @param:   id  对象参数
     * @return:  int
     */
    @Override
    public int deleteByPrimaryKey(Long id) {

        return 0;
    }

    /**
     * @explain: 修改SysUser对象
     * @param:   SysUser  对象参数
     * @return:  int
     */
    @Override
    public int updateByPrimaryKeySelective(SysUser model) {

        return 0;
    }

    /**
     * @explain: 查询SysUser对象
     * @param:   id  对象参数
     * @return:  SysUser
     */
    @Override
    public SysUser selectByPrimaryKey(Long id) {

        return null;
    }

    @Override
    public SysUserVo getSysUserVo(String username) {
        SysUser sysUser = null;
        if (sysUser == null) {
            return null;
        }
        //设置角色列表
        List<String> roleCodes = new ArrayList<>();
        roleCodes.add("admin");
        //设置权限列表（menu.permission）
        Set<String> permissions = new HashSet<>();
        permissions.add("all");
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(null);
        sysUserVo.setPermissions(null);
        return sysUserVo;
    }

}
