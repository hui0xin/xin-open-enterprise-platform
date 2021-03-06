package com.user.auth.mapper;

import com.user.auth.bean.DO.OauthAccessTokenDo;
import java.util.List;

/**
* @Interface: OauthAccessTokenMapper
* @Description: oauth访问令牌 Mapper
* @author: 系统
* @created: 2021-03-06
*/
public interface OauthAccessTokenMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     * @param record
     */
    int insert(OauthAccessTokenDo record);

    /**
     * 插入数据库记录
     * @param record
     */
    int insertSelective(OauthAccessTokenDo record);

    /**
     * 根据主键id查询
     * @param id
     */
    OauthAccessTokenDo selectByPrimaryKey(Long id);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKeySelective(OauthAccessTokenDo record);

    int updateByPrimaryKeyWithBLOBs(OauthAccessTokenDo record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(OauthAccessTokenDo record);

    /**
     * 根据主键id查询
     * @param record
     */
    OauthAccessTokenDo find(OauthAccessTokenDo record);

    /**
     * 根据主键id查询
     * @param record
     */
    List<OauthAccessTokenDo> list(OauthAccessTokenDo record);
}