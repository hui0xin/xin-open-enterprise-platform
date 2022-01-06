package com.xin.security.oauth2.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xin.security.oauth2.bean.DO.OauthClientDetails;
import com.xin.security.oauth2.core.service.OauthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @class: OauthClientDetailsServiceImpl
* @Description:  oauth客户端信息 接口实现
* @author: 系统
* @created: 2020-08-03
*/
@Slf4j
@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    /**
     * @explain: 添加OauthClientDetails对象
     * @param:   OauthClientDetails 对象参数
     * @return:  int
     */
    @Override
    public int insertSelective(OauthClientDetails model) {

        return 0;
    }

    /**
     * @explain: 删除OauthClientDetails对象
     * @param:   id  对象参数
     * @return:  int
     */
    @Override
    public int deleteByPrimaryKey(Long id) {

        return 0;
    }

    /**
     * @explain: 修改OauthClientDetails对象
     * @param:   OauthClientDetails  对象参数
     * @return:  int
     */
    @Override
    public int updateByPrimaryKeySelective(OauthClientDetails model) {

        return 0;
    }

    /**
     * @explain: 查询OauthClientDetails对象
     * @param:   id  对象参数
     * @return:  OauthClientDetails
     */
    @Override
    public OauthClientDetails selectByPrimaryKey(Long id) {

        return null;
    }

    @Override
    public PageInfo<OauthClientDetails> getList(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        //List<OauthClientDetailsDo> yltPlates = oauthClientDetailsMapper.selectAll();
        List<OauthClientDetails> yltPlates = null;
        PageInfo<OauthClientDetails> pageInfo = new PageInfo(yltPlates);
        return pageInfo;
    }

    @Override
    public OauthClientDetails getByClientId(String clientId) {

        return null;
    }

    @Override
    public int deleteByClientId(String clientId) {

        return 0;
    }

}
