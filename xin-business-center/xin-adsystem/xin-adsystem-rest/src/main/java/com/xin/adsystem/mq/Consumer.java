//package com.xin.adsystem.mq;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.exception.ExceptionUtils;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Objects;
//import java.util.Optional;
//
///**
// * Consumer class
// *
// * @author hxin
// * @date 2019/11/16
// */
//@Slf4j
//@Component
//public class Consumer {
//    /**
//     * tpoic 主题
//     */
//    private static final String TPOIC = "TOPIC-SDK-WELFARE-LOG";
//
//    private static final String APP_KEY = "982fa4b5b1c4a06f6d29c9654eacb74b";
//
//    //@Autowired
//    //private ListenTimeFlowService listenTimeFlowService;
//
//    @KafkaListener(topics = {TPOIC}, containerFactory = "kafkaListener1")
//    public void listen(ConsumerRecord<?, ListenNewsPointDto> record) {
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            try {
//                Object message = kafkaMessage.get();
//                if (Objects.isNull(message)) {
//                    return;
//                }
//                String[] jsonArray = message.toString().split("\\| \\|");
//                String json = jsonArray[3];
//                ListenNewsPointDto userReportMsg = JSON.parseObject(json, ListenNewsPointDto.class);
//                String user_info = userReportMsg.getInfo_list();
//                //如果为app_key 则参与处理
//                if (userReportMsg.getApp_key().equals(APP_KEY)) {
//                    listenTimeFlowService.analysisPointData(user_info);
//                }
//            } catch (Exception ex) {
//                log.error("[listen] 转换 fail: {}", ExceptionUtils.getStackTrace(ex));
//            }
//        }
//    }
//
//}
