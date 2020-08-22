package com.xin.api.gateway.bean;

import lombok.Data;

/**
 * 重定向类
 */
@Data
public class RedirectEntity {
    /**
     * 请求路径，
     * 原来请求接口 ，跳转到其他接口
     * /user/hh/** , /auth/mm/hh1/**
     */
    private String paths;
    /**
     * serverId
     * 原始请求服务serverId  ，调转的新的服务的serverId
     */
    private String serviceIds;

}