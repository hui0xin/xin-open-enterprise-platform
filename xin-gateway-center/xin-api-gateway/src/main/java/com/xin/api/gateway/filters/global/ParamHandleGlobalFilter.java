package com.xin.api.gateway.filters.global;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import com.xin.api.gateway.common.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 参数处理：
 * 将请求参数 写入 Attribute，
 * 用于之后的没有拦截器使用
 */
@Slf4j
@Component
public class ParamHandleGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 过滤器的执行顺序是从小到大执行，较高的值被解释为较低的优先级。
     */
    @Override
    public int getOrder() {
        return -99;
    }

    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

    /**
     * 执行逻辑
     * 这的处理很重要，解决了获取body数据不全的问题
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String method = serverHttpRequest.getMethod().toString();
        // 获取原参数
        URI uri = serverHttpRequest.getURI();
        // 如果为fase 不走处理，直接拦截
        MediaType contentType = serverHttpRequest.getHeaders().getContentType();
        if ("POST".equalsIgnoreCase(method)) {
            if (null != contentType) {
                if (contentType.includes(MediaType.APPLICATION_JSON)) {
                    return DataBufferUtils.join(serverHttpRequest.getBody()).map(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);
                        return bytes;
                    }).flatMap(bodyBytes -> {
                        String parmStr = new String(bodyBytes, StandardCharsets.UTF_8);
                        HttpUtils.setAttributeByCommonParm(exchange, settingParmByJSONObject(parmStr));
                        log.info("ParamHandleGlobalFilter-APPLICATION_JSON method = {}, uri = {}, contentType = {},  parms = {}",
                                method, uri, contentType, parmStr);
                        return chain.filter(exchange.mutate().request(generateNewRequest(exchange.getRequest(), bodyBytes)).build());
                    });
                }
                if (contentType.includes(MediaType.APPLICATION_FORM_URLENCODED)) {
                    return DataBufferUtils.join(serverHttpRequest.getBody()).map(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);
                        return bytes;
                    }).flatMap(bodyBytes -> {
                        String parmStr = new String(bodyBytes, StandardCharsets.UTF_8);
                        HttpUtils.setAttributeByCommonParm(exchange, settingParmByParm(parmStr));
                        log.info("ParamHandleGlobalFilter-x-www-form-urlencoded  method = {}, uri = {}, contentType = {},  parms = {}",
                                method, uri, contentType, parmStr);
                        return chain.filter(exchange.mutate().request(generateNewRequest(exchange.getRequest(), bodyBytes)).build());
                    });
                }
                if (contentType.includes(MediaType.MULTIPART_FORM_DATA)) {
                    return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
                        DataBufferUtils.retain(dataBuffer);
                        final Flux<DataBuffer> cachedFlux = Flux
                                .defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
                        final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };
                        final ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                        return cacheBody(mutatedExchange, chain);
                    });
                }
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> cacheBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        final HttpHeaders headers = exchange.getRequest().getHeaders();
        if (headers.getContentLength() == 0) {
            return chain.filter(exchange);
        }
        final ResolvableType resolvableType;
        if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(headers.getContentType())) {
            resolvableType = ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class);
        } else {
            resolvableType = ResolvableType.forClass(String.class);
        }
        return MESSAGE_READERS.stream()
                .filter(reader -> reader.canRead(resolvableType, exchange.getRequest().getHeaders().getContentType()))
                .findFirst().orElseThrow(() -> new IllegalStateException("no suitable HttpMessageReader."))
                .readMono(resolvableType, exchange.getRequest(), Collections.emptyMap()).flatMap(resolvedBody -> {
                    JSONObject object = new JSONObject();
                    if (resolvedBody instanceof MultiValueMap) {
                        MultiValueMap<String, Part> multiMap = (MultiValueMap<String, Part>) resolvedBody;
                        multiMap.forEach((key, value) -> {
                            if (!key.equals("files")) {
                                object.put(key, resolveBodyFromRequest(value.get(0).content()));
                            }
                        });
                    }
                    HttpUtils.setAttributeByCommonParm(exchange, object);
                    return chain.filter(exchange);
                });
    }

    private DataBuffer stringBuffer(byte[] bytes) {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        return nettyDataBufferFactory.wrap(bytes);
    }

    private ServerHttpRequest generateNewRequest(ServerHttpRequest request, byte[] bytes) {
        URI uri = request.getURI();
        ServerHttpRequest newRequest = request.mutate().uri(uri).build();
        DataBuffer dataBuffer = stringBuffer(bytes);
        Flux<DataBuffer> flux = Flux.just(dataBuffer);
        newRequest = new ServerHttpRequestDecorator(newRequest) {
            @Override
            public Flux<DataBuffer> getBody() {
                return flux;
            }
        };
        return newRequest;
    }

    public static JSONObject settingParmByParm(String args) {
        JSONObject object = null;
        if (!StringUtils.isBlank(args)) {
            object = new JSONObject();
            String[] parms = args.split("&");
            for (String str : parms) {
                String[] strs = str.split("=");
                String key = strs[0];
                String value = strs[1];
                object.put(key, value);
            }
        }
        return object;
    }

    public static JSONObject settingParmByJSONObject(String args) {
        JSONObject object = null;
        if (!StringUtils.isBlank(args)) {
            object = JSONObject.parseObject(args);
        }
        return object;
    }

    /**
     * 获取的字符串内容
     *
     * @param body
     * @return
     */
    private String resolveBodyFromRequest(Flux<DataBuffer> body) {
        // 获取请求体
        StringBuilder sb = new StringBuilder();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return sb.toString();
    }
}