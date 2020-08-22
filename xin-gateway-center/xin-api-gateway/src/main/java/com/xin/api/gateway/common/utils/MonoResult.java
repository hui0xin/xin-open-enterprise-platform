package com.xin.api.gateway.common.utils;

import com.xin.api.gateway.common.errorcode.GateWayErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * 将异常转化为json
 * " {\"code\":\"999500\",\"msg\":\"非法访问\"} "
 */
@Slf4j
@Component
public class MonoResult implements Serializable {

    public static String getExceptionToJson(GateWayErrorCodeEnum errorCodeEnum) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"code\":");
        buffer.append(errorCodeEnum.getCode());
        buffer.append(",\"msg\":\"");
        buffer.append(errorCodeEnum.getMessage());
        buffer.append("\"}");
        return buffer.toString();
    }

    public static String getExceptionToJson(int code, String msg) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"code\":");
        buffer.append(code);
        buffer.append(",\"msg\":\"");
        buffer.append(msg);
        buffer.append("\"}");
        return buffer.toString();
    }

    /**
     * 网关抛异常
     */
    public static Mono<Void> getFailedVoidMono(ServerWebExchange serverWebExchange, int code, String msg) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        byte[] bytes = getExceptionToJson(code, msg).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    /**
     * 网关抛异常
     */
    public static Mono<Void> getFailedVoidMono(ServerWebExchange serverWebExchange, GateWayErrorCodeEnum errorCodeEnum) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        byte[] bytes = getExceptionToJson(errorCodeEnum).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

}
