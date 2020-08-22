package com.xin.commons.rabbitmq.receiver;

import com.xin.commons.rabbitmq.service.OmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的接受者
 * 用于从取消订单的队列里（shop.order.cancel）接收消息
 */
@Slf4j
@Component
@RabbitListener(queues = "shop.order.cancel")
public class CancelOrderReceiver {

    @Autowired
    private OmsOrderService omsOrderService;

    @RabbitHandler
    public void handle(Long orderId){
        log.info("接收队列取消订单的消息：{}", orderId);
        omsOrderService.cancelOrder(orderId);
    }

}