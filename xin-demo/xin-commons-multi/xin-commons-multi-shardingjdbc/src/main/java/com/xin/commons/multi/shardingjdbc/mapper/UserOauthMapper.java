package com.xin.commons.multi.shardingjdbc.mapper;


import com.xin.commons.multi.shardingjdbc.bean.UserOauthDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserOauthMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     * @param record
     */
    int insert(UserOauthDo record);

    /**
     * 根据主键id查询
     * @param id
     */
    UserOauthDo selectByPrimaryKey(Long id);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKeySelective(UserOauthDo record);

    /**
     * 根据条件查询对象
     * @param record
     */
    UserOauthDo selectByPrimaryKeySelective(UserOauthDo record);

    /**
     * 根据条件查询列表
     * @param record
     */
    List<UserOauthDo> selectByPrimaryKeySelectiveList(UserOauthDo record);
}