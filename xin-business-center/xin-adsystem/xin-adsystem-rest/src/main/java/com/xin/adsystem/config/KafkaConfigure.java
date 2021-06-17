package com.xin.adsystem.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * kafka多消费者配置
 */
@Configuration
@EnableKafka
public class KafkaConfigure {

    //--------------------------kafka1 -----------------------------------
    @Value("${spring.kafka1.bootstrap-servers}")
    private String server1;
    @Value("${spring.kafka1.listener.concurrency}")
    private Integer concurrency1;
    @Value("${spring.kafka1.consumer.group-id}")
    private String groupId1;
    @Value("${spring.kafka1.consumer.auto-commit-interval}")
    private String autoCommitInterval1;
    @Value("${spring.kafka1.consumer.auto-offset-reset}")
    private String autoOffsetReset1;
    @Value("${spring.kafka1.consumer.enable-auto-commit}")
    private String enableAutoCommit1;
    @Value("${spring.kafka1.consumer.keyDeserializer}")
    private String consumerKeyDeserializer1;
    @Value("${spring.kafka1.consumer.valueDeserializer}")
    private String consumerValueDeserializer1;
    @Value("${spring.kafka1.producer.retries}")
    private String retries1;
    @Value("${spring.kafka1.producer.batch-size}")
    private String batchSize1;
    @Value("${spring.kafka1.producer.buffer-memory}")
    private String bufferMemory1;
    @Value("${spring.kafka1.producer.keyDeserializer}")
    private String producerKeyDeserializer1;
    @Value("${spring.kafka1.producer.valueDeserializer}")
    private String producerValueDeserializer1;
    @Value("${spring.kafka1.producer.acks}")
    private String acks1;


    //--------------------------kafka2 -----------------------------------
    @Value("${spring.kafka2.bootstrap-servers}")
    private String server2;
    @Value("${spring.kafka2.listener.concurrency}")
    private Integer concurrency2;
    @Value("${spring.kafka2.consumer.group-id}")
    private String groupId2;
    @Value("${spring.kafka2.consumer.auto-commit-interval}")
    private String autoCommitInterval2;
    @Value("${spring.kafka2.consumer.auto-offset-reset}")
    private String autoOffsetReset2;
    @Value("${spring.kafka2.consumer.enable-auto-commit}")
    private String enableAutoCommit2;
    @Value("${spring.kafka2.consumer.keyDeserializer}")
    private String consumerKeyDeserializer2;
    @Value("${spring.kafka2.consumer.valueDeserializer}")
    private String consumerValueDeserializer2;
    @Value("${spring.kafka2.producer.retries}")
    private String retries2;
    @Value("${spring.kafka2.producer.batch-size}")
    private String batchSize2;
    @Value("${spring.kafka2.producer.buffer-memory}")
    private String bufferMemory2;
    @Value("${spring.kafka2.producer.keyDeserializer}")
    private String producerKeyDeserializer2;
    @Value("${spring.kafka2.producer.valueDeserializer}")
    private String producerValueDeserializer2;
    @Value("${spring.kafka2.producer.acks}")
    private String acks2;

    /**
     * 消费者1配置
     *
     * @return
     */
    public ConsumerFactory<String, String> consumerFactory1() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server1);
        //消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId1);
        //该属性指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下该作何处理：
        //latest（默认值）在偏移量无效的情况下，消费者将从最新的记录开始读取数据（在消费者启动之后生成的记录）earliest ：在偏移量无效的情况下，消费者将从起始位置读取分区的记录
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset1);
        //是否自动提交偏移量，默认值是true,为了避免出现重复数据和数据丢失，可以把它设置为false,然后手动提交偏移量
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit1);
        //键的反序列化方式
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //值的反序列化方式
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //自动提交的时间间隔 在spring boot 2.X 版本中这里采用的是值的类型为Duration 需要符合特定的格式，如1S,1M,2H,5D
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval1);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    /**
     * 生产者1配置
     *
     * @return
     */
    public ProducerFactory<String, String> producerFactory1() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server1);
        //发生错误后，消息重发的次数。
        properties.put(ProducerConfig.RETRIES_CONFIG, retries1);
        //当有多个消息需要被发送到同一个分区时，生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算。
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize1);
        //设置生产者内存缓冲区的大小。
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory1);
        //键的反序列化方式
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //值的反序列化方式
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //acks=0 ： 生产者在成功写入消息之前不会等待任何来自服务器的响应。
        //acks=1 ： 只要集群的首领节点收到消息，生产者就会收到一个来自服务器成功响应。
        //acks=all ：只有当所有参与复制的节点全部收到消息时，生产者才会收到一个来自服务器的成功响应。
        properties.put(ProducerConfig.ACKS_CONFIG, acks1);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    /**
     * 生产者1模版
     *
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate1() {
        return new KafkaTemplate<>(producerFactory1());
    }

    /**
     * 消费者1的监听器
     *
     * @return
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListener1() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory1());
        //在侦听器容器中运行的线程数。
        factory.setConcurrency(concurrency1);
        factory.getContainerProperties().setPollTimeout(3000);
        //factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }


    /**
     * 消费者2配置
     *
     * @return
     */
    public ConsumerFactory<String, String> consumerFactory2() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server2);
        //消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId2);
        //该属性指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下该作何处理：
        //latest（默认值）在偏移量无效的情况下，消费者将从最新的记录开始读取数据（在消费者启动之后生成的记录）earliest ：在偏移量无效的情况下，消费者将从起始位置读取分区的记录
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset2);
        //是否自动提交偏移量，默认值是true,为了避免出现重复数据和数据丢失，可以把它设置为false,然后手动提交偏移量
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit2);
        //键的反序列化方式
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //值的反序列化方式
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //自动提交的时间间隔 在spring boot 2.X 版本中这里采用的是值的类型为Duration 需要符合特定的格式，如1S,1M,2H,5D
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval2);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    /**
     * 生产者2工场
     *
     * @return
     */
    public ProducerFactory<String, String> producerFactory2() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server2);
        //发生错误后，消息重发的次数。
        properties.put(ProducerConfig.RETRIES_CONFIG, retries2);
        //当有多个消息需要被发送到同一个分区时，生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算。
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize2);
        //设置生产者内存缓冲区的大小。
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory2);
        //键的反序列化方式
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //值的反序列化方式
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //acks=0 ： 生产者在成功写入消息之前不会等待任何来自服务器的响应。
        //acks=1 ： 只要集群的首领节点收到消息，生产者就会收到一个来自服务器成功响应。
        //acks=all ：只有当所有参与复制的节点全部收到消息时，生产者才会收到一个来自服务器的成功响应。
        properties.put(ProducerConfig.ACKS_CONFIG, acks2);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    /**
     * 生产者2模版
     *
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate2() {
        return new KafkaTemplate<>(producerFactory2());
    }

    /**
     * 消费者2的监听器
     *
     * @return
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListener2() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2());
        //在侦听器容器中运行的线程数。
        factory.setConcurrency(concurrency2);
        factory.getContainerProperties().setPollTimeout(3000);
        //factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

}
