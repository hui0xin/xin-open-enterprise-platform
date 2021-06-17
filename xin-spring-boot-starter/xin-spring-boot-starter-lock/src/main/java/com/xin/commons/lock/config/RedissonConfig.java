package com.xin.commons.lock.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

/**
 * Redisson配置
 * @author: xin
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.database}")
    private Integer database;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxTotal;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Integer maxWait;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;

    /**
     * 单机模式自动装配
     *
     * @return
     */
    @Bean
    RedissonClient redissonSingle() {
        //单机模式
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setTimeout(timeout)
                .setDatabase(database)
                .setConnectionPoolSize(maxTotal)
                .setConnectionMinimumIdleSize(minIdle);
        if (StringUtils.isNotBlank(password)) {
            serverConfig.setPassword(password);
        }

        //哨兵方式
//        Config config = new Config();
//        config.useSentinelServers().addSentinelAddress(
//                "redis://172.29.3.245:26378","redis://172.29.3.245:26379", "redis://172.29.3.245:26380")
//                .setMasterName("mymaster")
//                .setPassword("a123456").setDatabase(0);

        //集群方式
//        Config config = new Config();
//        config.useClusterServers().addNodeAddress(
//                "redis://172.29.3.245:6375","redis://172.29.3.245:6376", "redis://172.29.3.245:6377",
//                "redis://172.29.3.245:6378","redis://172.29.3.245:6379", "redis://172.29.3.245:6380")
//                .setPassword("a123456").setScanInterval(5000);

        return Redisson.create(config);
    }

}










