package com.xin.api.gateway.filters.global;

import com.xin.api.gateway.common.consts.Constants;
import com.xin.api.gateway.common.errorcode.GateWayErrorCodeEnum;
import com.xin.api.gateway.common.utils.HttpUtils;
import com.xin.api.gateway.common.utils.MonoResult;
import com.xin.api.gateway.service.IgnoredServices;
import com.xin.auth.jwt.entity.JwtUserInfo;
import com.xin.auth.jwt.sdk.AuthServerClient;
import com.xin.commons.support.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局过滤器：不需要在配置文件中制定，就可以执行
 * 验证token
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private IgnoredServices ignoredServices;

    @Autowired
    private AuthServerClient authServerClient;

    /**
     * 过滤器的执行顺序是从小到大执行，较高的值被解释为较低的优先级。
     */
    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 执行逻辑
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getURI().getPath();
        //从请求头获取参数
        String token = exchange.getRequest().getHeaders().getFirst(Constants.AUTH_HEADER_KEY);
        //是否需要验证token 为true，不需要验证
        boolean isToken = ignoredServices.isTokenIgnored(request);
        // 忽略token
        if (isToken) {
            log.info("AuthGlobalFilter 忽略token，直接放行, 请求方式：{}, 地址：{}, token：{}", request.getMethod(), requestUri, token);
            // 直接方行
            return chain.filter(exchange);
        }
        // token为空 阻止
        if (ObjectUtils.isEmpty(token) ) {
            log.error("AuthGlobalFilter token为空，被阻拦, 请求方式：{}, 地址：{}, token：{}", request.getMethod(), requestUri, token);
            return MonoResult.getFailedVoidMono(exchange, GateWayErrorCodeEnum.TOKEN_IS_EMPTY);
        }
        // token 不为空 验证token
        return checkToken(exchange, chain, token, request);
    }

    /**
     * 验证Token
     */
    private Mono<Void> checkToken(ServerWebExchange exchange, GatewayFilterChain chain, String token, ServerHttpRequest request) {
        String requestUri = request.getURI().getPath();
        log.info("AuthGlobalFilter-checkToken 验证, 请求方式：{}, 地址：{}, token：{}", request.getMethod(), requestUri, token);
        //如果只是验证token的有效性，使用这个
        //RestResult<Boolean> restResult = authServerClient.verifyToken(token);

        //验证token和 获取用户的id，头像，用户名称
        ResponseResult<JwtUserInfo> userInfoRestResult = authServerClient.getUserInfo(token);
        if (ObjectUtils.isEmpty(userInfoRestResult)) {
            log.error("AuthGlobalFilter-checkToken, getUserInfo 为空, 请求方式：{}, 地址：{}, token：{}", request.getMethod(), requestUri, token);
            return MonoResult.getFailedVoidMono(exchange, GateWayErrorCodeEnum.GET_USER_FAIL);
        }
        //注意：业务错误码和成功码都必须自己定义，不能和http码一样
        if (userInfoRestResult.getCode() != 0) {
            log.error("AuthGlobalFilter-checkToken, 请求方式：{}, 请求地址：{}, token：{}, getUserInfo 返回 code: {}, message: {}",
                    request.getMethod(), requestUri, token, userInfoRestResult.getCode(), userInfoRestResult.getMsg());
            // 为了推出登陆
            return MonoResult.getFailedVoidMono(exchange, GateWayErrorCodeEnum.TOKEN_AUTH_FAILED);
        }
        JwtUserInfo userDto = userInfoRestResult.getData();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userDto.getUserId());
        map.put("userName", userDto.getUserName());
        map.put("headImage", userDto.getHeadImage());
        //向uri上添加参数,生成新的URI
        URI newUri = HttpUtils.setParameterParams(exchange.getRequest(), map);
        //TODO 向headers中添加参数，这里也可以不用 网Header中添加参数，根据业务需要来实现
        //ServerHttpRequest host = exchange.getRequest().mutate().uri(newUri).build();
        ServerHttpRequest host = exchange.getRequest().mutate()
                .header("userId", userDto.getUserId().toString())
                .header("userName", userDto.getUserName())
                .header("headImage", userDto.getHeadImage())
                .uri(newUri).build();

        //将现在的request 变成 change对象
        ServerWebExchange build = exchange.mutate().request(host).build();
        return chain.filter(build);
    }
}