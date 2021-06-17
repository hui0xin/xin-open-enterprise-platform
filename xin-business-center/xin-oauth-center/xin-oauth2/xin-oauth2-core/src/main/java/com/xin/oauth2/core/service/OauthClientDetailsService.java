package com.xin.oauth2.core.service;

import com.github.pagehelper.PageInfo;
import com.xin.oauth2.bean.DO.OauthClientDetails;

/**
* @interface: OauthClientDetailsService
* @Description:  oauth客户端信息 接口
* @author: 系统
* @created: 2020-08-03
*/
public interface OauthClientDetailsService {

    /**
     * @explain: 添加OauthClientDetails对象
     * @param:   OauthClientDetails 对象参数
     * @return:  int
     */
    int insertSelective(OauthClientDetails model);

    /**
     * @explain: 删除OauthClientDetails对象
     * @param:   id  对象参数
     * @return:  int
     */
    int deleteByPrimaryKey(Long id);

    int deleteByClientId(String clientId);

    /**
     * @explain: 修改OauthClientDetails对象
     * @param:   OauthClientDetails  对象参数
     * @return:  int
     */
    int updateByPrimaryKeySelective(OauthClientDetails model);

    /**
     * @explain: 查询OauthClientDetails对象
     * @param:   id  对象参数
     * @return:  OauthClientDetails
     */
    OauthClientDetails selectByPrimaryKey(Long id);

    OauthClientDetails getByClientId(String clientId);

    PageInfo<OauthClientDetails> getList(Integer pageIndex, Integer pageSize);

}
