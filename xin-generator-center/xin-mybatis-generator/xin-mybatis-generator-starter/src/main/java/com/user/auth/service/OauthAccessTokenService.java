package com.user.auth.service;

import com.user.auth.bean.DO.OauthAccessTokenDo;
import java.util.List;

/**
 * @interface: OauthAccessTokenService
 * @Description:  oauth访问令牌 接口
 * @author: huixin
 * @created: 2021-03-06
 */
public interface OauthAccessTokenService {

    /**
     * @explain: 添加OauthAccessTokenDo对象
     * @param:   OauthAccessTokenDo 对象参数
     * @return:  int
     */
    int insertSelective(OauthAccessTokenDo model);

    /**
     * @explain: 删除OauthAccessTokenDo对象
     * @param:   id  对象参数
     * @return:  int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @explain: 修改OauthAccessTokenDo对象
     * @param:   OauthAccessTokenDo  对象参数
     * @return:  int
     */
    int updateByPrimaryKeySelective(OauthAccessTokenDo model);

    /**
     * @explain: 查询OauthAccessTokenDo对象
     * @param:   id  对象参数
     * @return:  OauthAccessTokenDo
     */
    OauthAccessTokenDo selectByPrimaryKey(Long id);

}
