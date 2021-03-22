package com.user.auth.mapper;

import com.user.auth.bean.DO.SysUserDo;
import java.util.List;

public interface SysUserMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     * @param record
     */
    int insert(SysUserDo record);

    /**
     * 根据主键id查询
     * @param id
     */
    SysUserDo selectByPrimaryKey(Long id);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKeySelective(SysUserDo record);

    /**
     * 根据参数查询对象
     * @param record
     */
    SysUserDo selectByObject(SysUserDo record);

    /**
     * 根据参数查询列表
     * @param record
     */
    List<SysUserDo> listByObject(SysUserDo record);
}