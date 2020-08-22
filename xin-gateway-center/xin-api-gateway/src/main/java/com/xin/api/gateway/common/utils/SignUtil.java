package com.xin.api.gateway.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xin.api.gateway.common.consts.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sign签名 Utils
 */
@Slf4j
@Component
public class SignUtil {

    /**
     * 私钥，不能泄露，和客户端使用同一套，
     * 这里的私钥也可以做成动态变化的，通过版本，每个版本使用一套签名私钥
     * 防止被破解 这里暂时不这么做
     */
    private static final String SALT = "xxxxxxx";

    /**
     * 生成新的map，
     * 排序算法
     */
    private static class StrSortTreeMap extends TreeMap<String, Object> {
        private static final long serialVersionUID = 1L;
        public StrSortTreeMap() {
            super(new Comparator<String>() {
                @Override
                public int compare(String str1, String str2) {
                    return str1.compareTo(str2);
                }
            });
        }
    }

    /**
     * 签名方法
     * @return
     */
    public boolean auth(Map<String, Object> paramsMap) {
        if (Objects.isNull(paramsMap) || paramsMap.isEmpty()) {
            return false;
        }
        //复制参数
        Map<String, Object> realParamMap = new HashMap<>(paramsMap);
        //获取参数sig
        Object clientSign = realParamMap.getOrDefault("sig", null);
        if (Objects.isNull(clientSign)) {
            log.error("验签失败-客户端传过来的签名sig为空！！！");
            return false;
        }
        //这里注意，签名sig不参与计算
        realParamMap.remove("sig");
        //计算签名
        String serverSign = createSign(realParamMap, SignUtil.SALT);
        boolean ok = serverSign.equals(clientSign);
        if (!ok) {
            log.error("验签失败-clientSign = {},serverSign = {}", clientSign, serverSign);
        }
        return ok;
    }

    /**
     * 计算签名方法
     * @param map 参与签名计算的map
     * @param key 为 定义的 SALT
     * @return
     */
    public static String createSign(Map<String, Object> map, String key) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StrSortTreeMap treeMap = new StrSortTreeMap();
        for (String k : map.keySet()) {
            treeMap.put(k, map.get(k));
        }
        try {
            StringBuilder queryStringBuilder = new StringBuilder();
            for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
                queryStringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            String sha1HexStr = DigestUtils.sha1Hex(key);
            queryStringBuilder.append("key=").append(sha1HexStr);
            String queryString = queryStringBuilder.toString();
            return DigestUtils.sha1Hex(queryString).toUpperCase();
        } catch (Exception e) {
            log.error("SignUtil-createSign异常 : {}", ExceptionUtils.getStackTrace(e));
            return "";
        }
    }


    /**
     * 将请求 参数构造成 Map
     * @param exchange
     * @return
     */
    public static Map<String, Object> getParams(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String method = exchange.getRequest().getMethod().toString();
        Map<String, Object> map = Maps.newHashMap();
        if ("POST".equalsIgnoreCase(method)) {
            //这里是前一个 ParamHandleGlobalFilter 中设置的，方便后边的filter获取参数，做具体的业务逻辑
            Object object = exchange.getAttributes().get(Constants.XINAPIGATEWAYBODYPARAM);
            if (!Objects.isNull(object)) {
                JSONObject obj = JSONObject.parseObject(object.toString());
                map.putAll(getParamByPost(obj));
            }
        }
        if ("GET".equalsIgnoreCase(method)) {
            map.putAll(getParamByGet(request));
        }
        return map;
    }



    /**
     * 获取get请求参数
     * @param request
     * @return
     */
    private final static Map<String, Object> getParamByGet(ServerHttpRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        MultiValueMap<String, String> paramNames = request.getQueryParams();
        for (Map.Entry<String, List<String>> entry : paramNames.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue().get(0);
            if (!StringUtils.isEmpty(paramValue)) {
                map.put(paramName, paramValue);
            }
        }
        return map;
    }

    /**
     * 获取post请求内容
     * @param jsonObj
     * @return
     */
    private final static Map<String, Object> getParamByPost(JSONObject jsonObj) {
        Map<String, Object> resultMap = new ConcurrentHashMap<>();
        for(Map.Entry<String, Object> map:jsonObj.entrySet()){
            String paramName = map.getKey();
            Object paramValue = map.getValue();
            resultMap.put(paramName, paramValue);
        }
        return resultMap;
    }

}
