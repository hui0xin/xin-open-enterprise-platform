package com.xin.api.gateway.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.xin.api.gateway.common.consts.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.*;

/**
 * http操作Utils
 */
@Slf4j
public class HttpUtils {

    /**
     * url上设置参数
     */
    public static URI setParameterParams(ServerHttpRequest serverHttpRequest, Map<String, Object> requestQueryParams){
        // 获取原参数
        URI uri = serverHttpRequest.getURI();
        StringBuilder query = new StringBuilder();
        String originalQuery = uri.getRawQuery();
        if (StringUtils.hasText(originalQuery)) {
            query.append(originalQuery);
            if (originalQuery.charAt(originalQuery.length() - 1) != '&') {
                query.append('&');
            }
        }
        for (Map.Entry<String, Object> entry : requestQueryParams.entrySet()) {
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();
            //注意，这里的用户名是一些特殊自负，转码
            if(mapKey.equals("userName")){
                if(!ObjectUtils.isEmpty(mapValue)){
                    String str = mapValue.toString();
                    if(!isNumber(str)){
                        try {
                            mapValue = new String(str.getBytes("ISO8859-1"),"utf-8");
                        }catch (Exception e){
                            log.error("new String(str.getBytes() 错误:{}", ExceptionUtils.getStackTrace(e));
                        }
                    }
                }
            }
            query.append(mapKey);
            query.append("=");
            query.append(mapValue);
            query.append("&");
        }
        String queryStr = query.toString();
        // 替换查询参数
        URI newUri = UriComponentsBuilder.fromUri(uri)
                .replaceQuery(queryStr.substring(0, queryStr.length() - 1))
                .encode()
                .build(true)
                .toUri();
        return newUri;
    }



    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(ServerHttpRequest request) {
        String ipAddress = request.getHeaders().getFirst("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddress().toString();

            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡获取本机配置的IP地址
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("", e);
                }
                if (Objects.nonNull(inetAddress)) {
                    ipAddress = inetAddress.getHostAddress();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
        if (null != ipAddress && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    private static boolean isNumber(String str){
        String reg = "^[A-Za-z0-9]+$";
        return str.matches(reg);
    }

    /**
     * 将body参数设置到Attributes中，方便以后的处理，
     * @param exchange
     * @param object
     */
    public static void setAttributeByCommonParm(ServerWebExchange exchange, JSONObject object) {
        exchange.getAttributes().put(Constants.XINAPIGATEWAYBODYPARAM, object);
    }

    /**
     * 从 Attributes中获取 body数据
     * @param exchange
     * @param parmStr
     * @return
     */
    public static Object getCommonParm(ServerWebExchange exchange, String parmStr) {
        Object parm = "";
        String method = exchange.getRequest().getMethod().toString();
        if ("POST".equalsIgnoreCase(method)) {
            Object object = exchange.getAttributes().get(Constants.XINAPIGATEWAYBODYPARAM);
            if (!Objects.isNull(object)) {
                JSONObject obj = JSONObject.parseObject(object.toString());
                parm = obj.get(parmStr);
            }
            if(StringUtils.isEmpty(parm)){
                parm = exchange.getRequest().getQueryParams().getFirst(parmStr);
            }
        }
        if ("GET".equalsIgnoreCase(method)) {
            parm = exchange.getRequest().getQueryParams().getFirst(parmStr);
        }
        return parm;
    }

}
