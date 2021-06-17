package com.xin.api.gateway.filters.global;

import com.xin.api.gateway.common.errorcode.GateWayErrorCodeEnum;
import com.xin.api.gateway.common.utils.MonoResult;
import com.xin.api.gateway.common.utils.SignUtil;
import com.xin.api.gateway.service.IgnoredServices;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Map;

/**
 * 全局过滤器：不需要在配置文件中制定，就可以执行
 * 验证签名
 */
@Slf4j
@Component
public class SignGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private SignUtil signUtil;

    @Autowired
    private IgnoredServices ignoredServices;

    /**
     * 过滤器的执行顺序是从小到大执行，较高的值被解释为较低的优先级。
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 执行逻辑
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //返回true 需要验证
        if (ignoredServices.isSigIgnored(request)) {
            // 直接方行
            return chain.filter(exchange);
        }
        Map<String, Object> paramsMap;
        try {
            //从request中获取参数
            paramsMap = signUtil.getParams(exchange);
        } catch (Exception e) {
            log.error("SignZuulFilter-exception, 请求方式：{}, 地址：{}，验证失败: {}", request.getMethod(), request.getURI().getPath(), ExceptionUtils.getStackTrace(e));
            return MonoResult.getFailedVoidMono(exchange, GateWayErrorCodeEnum.SIG_AUTH_PARMTS_FAILED);
        }
        log.info("SignZuulFilter, 请求方式：{}, 地址：{}，showParams: {}", request.getMethod(), request.getURI().getPath(), paramsMap);
        boolean status = signUtil.auth(paramsMap);
        if (!status) {
            log.error("SignZuulFilter-error, 请求方式：{}, 地址：{}，showParams: {}", request.getMethod(), request.getURI().getPath(), paramsMap);
            return MonoResult.getFailedVoidMono(exchange, GateWayErrorCodeEnum.SIG_AUTH_FAILED);
        }
        //放行
        return chain.filter(exchange);
    }

}
