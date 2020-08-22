package com.xin.api.gateway.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * http
 * 工具类
 */
@Slf4j
@Component
public class HttpUtils {

    public static HttpServletRequest getRequest() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest realRequest = null;
        HttpServletRequest request = ctx.getRequest();
        if ("DELETE".equalsIgnoreCase(request.getMethod()) || "POST".equalsIgnoreCase(request.getMethod())) {
            HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
            HttpServletRequest realRequest2 = httpServletRequestWrapper.getRequest();
            String contentType = realRequest2.getContentType();
            if (!StringUtils.isEmpty(contentType)) {
                if (contentType.startsWith("application/json")) {
                    realRequest = request;
                } else if (contentType.startsWith("application/x-www-form-urlencoded")) {
                    realRequest = realRequest2;
                } else if (contentType.startsWith("multipart/form-data")) {
                    realRequest = realRequest2;
                } else {
                    log.error("contentType is :{}", contentType);
                }
            }else{
                realRequest = request;
            }
        }else{
            realRequest = request;
        }
        return realRequest;
    }

    /**
     * 设置单个参数
     * 将参数设置到param上
     * @param key
     */
    public static void setParameterParams(String key, String value) {
        RequestContext requestContext = RequestContext.getCurrentContext();
        Map<String, List<String>> requestQueryParams = requestContext.getRequestQueryParams();
        if (Objects.isNull(requestQueryParams)) {
            requestQueryParams = Maps.newHashMap();
        }
        ArrayList<String> arrayList = Lists.newArrayList();
        arrayList.add(value);
        requestQueryParams.put(key, arrayList);
        requestContext.setRequestQueryParams(requestQueryParams);
    }

    /**
     * 设置多个参数
     * 将参数设置到param上
     */
    public static void setParameterParams(Map<String, Object> map) {
        if(ObjectUtils.isEmpty(map)){
            return;
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取所有参数
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (requestQueryParams == null) {
            requestQueryParams = new HashMap<>();
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
            ArrayList<String> list = new ArrayList<>();
            list.add(mapValue.toString());
            requestQueryParams.put(mapKey,list);
        }
        ctx.setRequestQueryParams(requestQueryParams);
    }
    /**
     * 设置多个参数
     * 将参数设置到Hearder上
     */
    public static void setRequestHeader(String key, Object value) {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.addZuulRequestHeader(key, String.valueOf(value));
    }

    /**
     * 设置多个参数
     * 将参数设置到Hearder上
     */
    public static void setRequestHeader(Map<String, Object> map) {
        if(ObjectUtils.isEmpty(map)){
            return;
        }
        RequestContext requestContext = RequestContext.getCurrentContext();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
            requestContext.addZuulRequestHeader(mapKey, String.valueOf(mapValue));
        }
    }

    /**
     * 获取id地址
     * @return
     */
    public static String getIpAddress(){
        HttpServletRequest request = getRequest();
        String ipAddress = request.getHeader("x-forwarded-for");

        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();

            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
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
        if(null != ipAddress && ipAddress.length() > 15){
            if(ipAddress.indexOf(",") > 0){
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static void showRequest(String filterName) {
        HttpServletRequest request = getRequest();
        Enumeration<String> paramNames = request.getParameterNames();
        StringBuilder sb = new StringBuilder();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            sb.append(paramName).append(": ").append(paramValue).append(", ");
        }
        String params = "";
        if (sb.length() > 0) {
            params = sb.substring(0, sb.length() - 2);
        }
        log.info("   filterName = {},    请求地址 = {},   请求参数 = {}",filterName, request.getRequestURI(), params);
    }



    private static boolean isNumber(String str){
        String reg = "^[A-Za-z0-9]+$";
        return str.matches(reg);
    }

}
