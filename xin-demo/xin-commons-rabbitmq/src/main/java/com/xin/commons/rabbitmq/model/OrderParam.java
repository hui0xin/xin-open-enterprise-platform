package com.xin.commons.rabbitmq.model;

import lombok.Data;

/**
 * 生成订单时传入的参数
 */
@Data
public class OrderParam {
    //收货地址id
    private Long memeberAddressId;
    //优惠券id
    private Long couponId;
    //使用的积分
    private Integer useIntegration;
    //支付的方式
    private Integer payType;

}