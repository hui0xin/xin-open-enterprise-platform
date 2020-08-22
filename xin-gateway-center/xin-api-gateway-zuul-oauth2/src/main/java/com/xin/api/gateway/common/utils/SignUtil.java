package com.xin.api.gateway.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * sign 工具类
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
     * @param request
     * @return
     */
    public static Map<String, Object> getParams(HttpServletRequest request) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        if ("DELETE".equalsIgnoreCase(request.getMethod()) || "POST".equalsIgnoreCase(request.getMethod())) {
            HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
            HttpServletRequest realRequest = httpServletRequestWrapper.getRequest();
            String contentType = realRequest.getContentType();
            if (!StringUtils.isEmpty(contentType)) {
                if (contentType.startsWith("application/json")) {
                    String s = getRequestPostStr(request);
                    if (!StringUtils.isEmpty(s)) {
                        map = JSONObject.parseObject(s, new TypeReference<Map<String, Object>>() {
                        });
                    }
                } else if (contentType.startsWith("application/x-www-form-urlencoded")) {
                    map = getParam(realRequest);
                } else if (contentType.startsWith("multipart/form-data")) {
                    map = getParam(realRequest);
                }
            }
        }
        map.putAll(getParam(request));
        return map;
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        if (Objects.isNull(buffer) || buffer.length <= 0) {
            return "";
        }
        String charEncoding = request.getCharacterEncoding();
        if (StringUtils.isEmpty(charEncoding)) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

    /**
     * 获取参数内容
     *
     * @param request
     * @return
     */
    private final static Map<String, Object> getParam(HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (!StringUtils.isEmpty(paramValue)) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

}
