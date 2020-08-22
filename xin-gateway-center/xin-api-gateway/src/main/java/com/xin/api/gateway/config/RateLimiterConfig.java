package com.xin.api.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 路由限流配置
 * 这里可以实现多个（这是ip限流的两种实现）
 * KeyResolver用于计算某一个类型的限流的KEY也就是说，
 * 可以通过KeyResolver来指定限流的Key。
 * 注意，具体的使用要在 yaml中配置
 *  args:
 *      # SPEL 表达式获取 Spring 中的 Bean，这个参数表示根据什么来限流
 *      key-resolver: '#{@ipKeyResolver}'
 *      # 允许用户每秒执行多少请求（令牌桶的填充速率）
 *      redis-rate-limiter.replenishRate: 10
 *      # 允许用户在一秒内执行的最大请求数。（令牌桶可以保存的令牌数）。将此值设置为零将阻止所有请求。
 *      redis-rate-limiter.burstCapacity: 20
 */
@Configuration
public class RateLimiterConfig {

    /**
     * 限流的键定义：
     * <p>
     * 1. Spring Cloud Gateway 默认实现 Redis限流，如果扩展只需要实现 Ratelimter 接口即可。
     * 2. 可以通过自定义KeyResolver来指定限流的Key,比如我们需要根据用户、IP、URI来做限流等等，通过exchange对象可以获取到请求信息。
     */
    @Bean(value = "remoteAddrKeyResolver")
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    /**
     * ip限流
     * .getRemoteAddress().getAddress().getHostAddress() 也可以
     * @return
     */
    @Bean(value = "ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**
     * ip限流 地第二种实现
     * .getRemoteAddress().getAddress().getHostAddress() 也可以
     * @return
     */
    @Bean(value = "ipKeyResolver2")
    public KeyResolver ipKeyResolver2() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
            }
        };
    }

    /**
     * 用户限流
     *
     * @return
     */
    @Bean(value = "userKeyResolver")
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }

    /**
     * 接口限流
     *
     * @return
     */
    @Bean(value = "apiKeyResolver")
    KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

}