package com.xin.commons.multi.shardingjdbc.mapper;


import com.xin.commons.multi.shardingjdbc.bean.UserAppDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAppMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     * @param record
     */
    int insert(UserAppDo record);

    /**
     * 根据主键id查询
     * @param id
     */
    UserAppDo selectByPrimaryKey(Long id);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKeySelective(UserAppDo record);

    /**
     * 根据条件查询对象
     * @param record
     */
    UserAppDo selectByPrimaryKeySelective(UserAppDo record);

    /**
     * 根据条件查询列表
     * @param record
     */
    List<UserAppDo> selectByPrimaryKeySelectiveList(UserAppDo record);
}