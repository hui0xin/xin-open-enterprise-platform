package com.xin.api.gateway.filters.global;

import java.net.URI;
import com.xin.api.gateway.common.errorcode.GateWayErrorCodeEnum;
import com.xin.api.gateway.common.utils.MonoResult;
import com.xin.api.gateway.service.IgnoredServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 全局路由 对一些特殊接口进行直接拦截
 */
@Slf4j
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private IgnoredServices ignoredServices;

    /**
     * 过滤器的执行顺序是从小到大执行，较高的值被解释为较低的优先级。
     */
    @Override
    public int getOrder() {
        return -100;
    }

    /**
     * 执行逻辑
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取原参数
        URI uri = request.getURI();
        // 如果为fase 不走处理，直接拦截
        boolean isIgnoredPatterns = ignoredServices.isIgnoredPatterns(request);
        if (uri.getPath().trim().equals("/")) {
            return MonoResult.getFailedVoidMono(exchange, GateWayErrorCodeEnum.PATH_IS_ERROR);
        }
        if (isIgnoredPatterns) {
            return MonoResult.getFailedVoidMono(exchange, GateWayErrorCodeEnum.INTERFACE_NOT_ALLOW);
        }
        return chain.filter(exchange);
    }

}