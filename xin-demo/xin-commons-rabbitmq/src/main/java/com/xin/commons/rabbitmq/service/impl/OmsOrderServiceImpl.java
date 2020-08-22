package com.xin.commons.rabbitmq.service.impl;

import com.xin.commons.rabbitmq.model.OrderParam;
import com.xin.commons.rabbitmq.sender.CancelOrderSender;
import com.xin.commons.rabbitmq.service.OmsOrderService;
import com.xin.commons.support.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单管理接口实现类
 */
@Slf4j
@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Override
    public ResponseResult generateOrder(OrderParam orderParam) {
        // TODO: 2019/12/17 下单生成订单
        log.info("下单成功，获取到订单id：{}", 1L);
        //设置延迟发送时间，测试设置为30秒
        long delayTimes = 30*1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(1L, delayTimes);
        return ResponseResult.success("下单成功");
    }

    @Override
    public void cancelOrder(Long orderId) {
        // TODO: 2019/12/17 取消单个超时订单
        log.info("根据orderId取消超时订单：{}", orderId);
    }

}