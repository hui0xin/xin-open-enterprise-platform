package com.xin.commons.dynamic.datasource.redis.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

/**
 * 配置文件配置参数注入
 */
@ConfigurationProperties(prefix = "spring")
public class CustomRedisProperties {

	// 多数据源所以是 map 了, RedisProperties 这个是 spring boot 本身的一个redis 配置类 可以直接用
	private Map<String, RedisProperties> multipleRedis;

	public Map<String, RedisProperties> getMultipleRedis() {
		return multipleRedis;
	}

	public void setMultipleRedis(Map<String, RedisProperties> multipleRedis) {
		this.multipleRedis = multipleRedis;
	}

}