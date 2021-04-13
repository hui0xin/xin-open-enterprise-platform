package com.xin.commons.multi.kafka.producer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * kafkaTemplate2 表示使用第二个kafka数据源
 * @author xin
 */
@Slf4j
@Component
public class Producer {
    /**
     * tpoic 主题
     */
    private static final String TPOIC = "TOPIC-SDK-WELFARE-LOG";

    @Autowired
    private KafkaTemplate kafkaTemplate1;
    @Autowired
    private KafkaTemplate kafkaTemplate2;

    public void send() {
        JSONObject object = new JSONObject();
        //发功给kafka1
        kafkaTemplate1.send(TPOIC,object);
        //发送给kafka2
        kafkaTemplate2.send(TPOIC,object);
    }

    /**
     * 发送消息,失败和返回后的处理
     * @param obj
     */
    public void send(JSONObject obj) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate1.send(TPOIC, obj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.info(TPOIC + " - 生产者 发送消息失败：" + throwable.getMessage());

            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                //成功的处理
                log.info(TPOIC + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });
    }

}
