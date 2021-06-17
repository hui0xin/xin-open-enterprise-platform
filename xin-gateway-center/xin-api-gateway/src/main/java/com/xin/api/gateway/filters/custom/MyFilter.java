package com.xin.api.gateway.filters.custom;

import com.ctrip.framework.apollo.core.spi.Ordered;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义 Gateway Filter、
 * 自定义Gateway Filter Factory。
 *
 * 实现自定义的Gateway Filter我们需要GatewayFilter、Ordered两个接口
 * 此过滤器功能为计算请求完成时间
 */
public class MyFilter implements GatewayFilter, Ordered {

    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                    if (startTime != null) {
                        System.out.println(exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime) + "ms");
                    }
                })
        );
    }

    /**
     * 过滤器存在优先级，order越大，优先级越低
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * 定义好MyFilter以后，其需要跟Route绑定使用，
     * 不能在application.yml文件中配置使用
     * @param builder
     * @return
     */
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes().route(r ->
//                r.path("/aa")
//                        //转发路由
//                        .uri("http://localhost:8003/provider/test")
//                        //注册自定义过滤器
//                        .filters(new MyFilter())
//                        //给定id
//                        .id("user-service"))
//                .build();
//
//    }
}
