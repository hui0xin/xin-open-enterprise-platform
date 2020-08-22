package com.xin.commons.multi.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Consumer class
 * containerFactory = "kafkaListener1" 指定 用那个kafak
 * @author xin
 */
@Slf4j
@Component
public class Consumer {

    /**
     * tpoic 主题
     */
    private static final String TPOIC = "TOPIC-SDK-WELFARE-LOG";

    @KafkaListener(topics = {TPOIC}, containerFactory = "kafkaListener1")
    //ConsumerRecord<?, Object> record 可以为 ConsumerRecord<?, User> 确切对象
    public void listen(ConsumerRecord<?, Object> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            try {
                Object message = kafkaMessage.get();
                if (Objects.isNull(message)) {
                    return;
                }
                //业务逻辑处理在这里

            } catch (Exception ex) {
                log.error("[listen] 转换 fail: {}", ExceptionUtils.getStackTrace(ex));
            }
        }
    }

}
