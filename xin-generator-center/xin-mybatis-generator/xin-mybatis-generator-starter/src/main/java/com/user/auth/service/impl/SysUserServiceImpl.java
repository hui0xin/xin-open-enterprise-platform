package com.user.auth.service.impl;

import com.user.auth.bean.DO.SysUserDo;
import com.user.auth.mapper.SysUserMapper;
import com.user.auth.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @class: SysUserServiceImpl
 * @Description:  系统用户信息 接口实现
 * @author: 系统
 * @created: 2021-03-22
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * @explain: 添加SysUserDo对象
     * @param:   SysUserDo 对象参数
     * @return:  int
     */
    @Override
    public int insert(SysUserDo model) {
        return sysUserMapper.insert(model);
    }

    /**
     * @explain: 删除SysUserDo对象
     * @param:   id  对象参数
     * @return:  int
     */
    @Override
    public int deleteById(Long id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改SysUserDo对象
     * @param:   SysUserDo  对象参数
     * @return:  int
     */
    @Override
    public int update(SysUserDo model) {
        return sysUserMapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询SysUserDo对象
     * @param:   id  对象参数
     * @return:  SysUserDo
     */
    @Override
    public SysUserDo selectByPrimaryKey(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    /**
     * @explain: 查询SysUserDo对象
     * @param:   SysUserDo  对象参数
     * @return:  SysUserDo 对象
     */
    @Override
    public SysUserDo selectByObject(SysUserDo model) {
        return sysUserMapper.selectByObject(model);
    }

    /**
     * @explain: 查询列表
     * @param:  SysUserDo  对象参数
     * @return: list
     */
    @Override
    public List<SysUserDo> listByObject(SysUserDo model) {
        return sysUserMapper.listByObject(model);
    }

}
