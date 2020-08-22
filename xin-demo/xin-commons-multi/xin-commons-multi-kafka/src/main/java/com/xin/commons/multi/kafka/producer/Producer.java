package com.xin.commons.multi.kafka.producer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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

}
