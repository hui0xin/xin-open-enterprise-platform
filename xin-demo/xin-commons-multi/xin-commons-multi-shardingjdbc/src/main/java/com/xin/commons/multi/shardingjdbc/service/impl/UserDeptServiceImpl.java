package com.xin.commons.multi.shardingjdbc.service.impl;

import com.xin.commons.multi.shardingjdbc.bean.UserDeptDo;
import com.xin.commons.multi.shardingjdbc.mapper.UserDeptMapper;
import com.xin.commons.multi.shardingjdbc.service.UserDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @class: UserDeptServiceImpl
 * @Description:  用户部门表 接口实现
 * @author: 系统
 * @created: 2022-05-12
 */
@Slf4j
@Service
public class UserDeptServiceImpl implements UserDeptService {

    @Autowired
    private UserDeptMapper userDeptMapper;

    /**
     * @explain: 添加UserDeptDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int insert(UserDeptDo model) {
        return userDeptMapper.insert(model);
    }

    /**
     * @explain: 删除UserDeptDo对象
     * @param:   id
     * @return:  int
     */
    @Override
    public int deleteById(Long id) {
        return userDeptMapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改UserDeptDo对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int update(UserDeptDo model) {
        return userDeptMapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询UserDeptDo对象
     * @param:   id
     * @return:  UserDeptDo
     */
    @Override
    public UserDeptDo selectById(Long id) {
        return userDeptMapper.selectByPrimaryKey(id);
    }

    /**
     * @explain: 查询UserDeptDo对象
     * @param:   model 对象参数
     * @return:  UserDeptDo 对象
     */
    @Override
    public UserDeptDo selectByObject(UserDeptDo model) {
        return userDeptMapper.selectByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    @Override
    public List<UserDeptDo> listByObject(UserDeptDo model) {
        return userDeptMapper.selectByPrimaryKeySelectiveList(model);
    }

}
