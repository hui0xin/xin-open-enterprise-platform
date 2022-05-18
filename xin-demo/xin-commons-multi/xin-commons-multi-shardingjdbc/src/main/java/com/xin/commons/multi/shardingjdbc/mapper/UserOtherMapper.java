package com.xin.commons.multi.shardingjdbc.mapper;


import com.xin.commons.multi.shardingjdbc.bean.UserOtherDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserOtherMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     * @param record
     */
    int insert(UserOtherDo record);

    /**
     * 根据主键id查询
     * @param id
     */
    UserOtherDo selectByPrimaryKey(Long id);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKeySelective(UserOtherDo record);

    /**
     * 根据条件查询对象
     * @param record
     */
    UserOtherDo selectByPrimaryKeySelective(UserOtherDo record);

    /**
     * 根据条件查询列表
     * @param record
     */
    List<UserOtherDo> selectByPrimaryKeySelectiveList(UserOtherDo record);
}