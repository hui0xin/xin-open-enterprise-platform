package com.xin.api.gateway.filters.custom;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 很多时候我们更希望在配置文件中配置Gateway Filter,所以我们可以自定义过滤器工厂实现。
 * 自定义过滤器工厂需要继承AbstractGatewayFilterFactory
 */
@Slf4j
@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {
 

    private static final String AUTHORIZE_TOKEN = "token";
    private static final String AUTHORIZE_UID = "uid";
 
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
 
    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
        log.info("Loaded GatewayFilterFactory [Authorize]");
    }
 
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }
 
    @Override
    public GatewayFilter apply(AuthorizeGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
 
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(AUTHORIZE_TOKEN);
            String uid = headers.getFirst(AUTHORIZE_UID);
            if (token == null) {
                token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            }
            if (uid == null) {
                uid = request.getQueryParams().getFirst(AUTHORIZE_UID);
            }
 
            ServerHttpResponse response = exchange.getResponse();
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(uid)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            String authToken = stringRedisTemplate.opsForValue().get(uid);
            if (authToken == null || !authToken.equals(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            return chain.filter(exchange);
        };
    }
 
    public static class Config {
        // 控制是否开启认证
        private boolean enabled;
 
        public Config() {}
 
        public boolean isEnabled() {
            return enabled;
        }
 
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

/**
* 具体的使用需要在yml中配置
# 网关路由配置
spring:
  cloud:
     gateway:
        routes:
           - id: user-service
             uri: http://localhost:8077/api/user/list
             predicates:
                - Path=/user/list
             filters:
                # 关键在下面一句，值为true则开启认证，false则不开启
                # 这种配置方式和spring cloud gateway内置的GatewayFilterFactory一致
                - Authorize=true
*/

}