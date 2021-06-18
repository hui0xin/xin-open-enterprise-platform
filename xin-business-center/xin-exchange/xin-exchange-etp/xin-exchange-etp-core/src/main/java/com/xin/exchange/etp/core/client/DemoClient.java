//package com.xin.exchange.etp.core.client;

//import com.user.sdk.api.UserInfoApi;
//import org.springframework.cloud.openfeign.FeignClient;

/**
 * 通过用户id查询用户信息
 * 为了解决一些项目扫描不到FeignClient，所以这里采用继承，直接扫描本项目的FeignClient
 */
//@FeignClient(value = "userserver")
//public interface DemoClient extends UserInfoApi {
//}