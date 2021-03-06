package com.user.auth.service.impl;

import com.user.auth.bean.DO.OauthAccessTokenDo;
import com.user.auth.mapper.OauthAccessTokenMapper;
import com.user.auth.service.OauthAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @class: OauthAccessTokenServiceImpl
 * @Description:  oauth访问令牌 接口实现
 * @author: huixin
 * @created: 2021-03-06
 */
@Slf4j
@Service
public class OauthAccessTokenServiceImpl implements OauthAccessTokenService {

    @Autowired
    private OauthAccessTokenMapper oauthAccessTokenMapper;

    /**
     * @explain: 添加OauthAccessTokenDo对象
     * @param:   OauthAccessTokenDo 对象参数
     * @return:  int
     */
    @Override
    public int insertSelective(OauthAccessTokenDo model) {
        return oauthAccessTokenMapper.insertSelective(model);
    }

    /**
     * @explain: 删除OauthAccessTokenDo对象
     * @param:   id  对象参数
     * @return:  int
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return oauthAccessTokenMapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改OauthAccessTokenDo对象
     * @param:   OauthAccessTokenDo  对象参数
     * @return:  int
     */
    @Override
    public int updateByPrimaryKeySelective(OauthAccessTokenDo model) {
        return oauthAccessTokenMapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询OauthAccessTokenDo对象
     * @param:   id  对象参数
     * @return:  OauthAccessTokenDo
     */
    @Override
    public OauthAccessTokenDo selectByPrimaryKey(Long id) {
        return oauthAccessTokenMapper.selectByPrimaryKey(id);
    }

}
