package com.xin.commons.support.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * http操作类，对cookie设置，获取body，获取ip等操作
 * @author: xin
 */
@Slf4j
public class HttpUtil {

    public final static String METHOD_GET = "GET";
    public final static String METHOD_POST = "POST";
    public final static String METHOD_PUT = "PUT";
    public final static String METHOD_DELETE = "DELETE";

    public static String getRequestRealPath(HttpServletRequest request) {
        if(request == null){
            return "";
        }

        String requestUrl = request.getRequestURL() != null ? request.getRequestURL().toString() : "";
        return request.getQueryString() != null ? requestUrl + "?" + request.getQueryString() : requestUrl;
    }

    /**
     * 获取cookie
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //迭代时如果发现与指定cookieName相同的cookie，就修改相关数据
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 添加cookie
     */
    public static void addCookie(HttpServletResponse response, String domain, String key,
                                 String value, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 修改cookie
     */
    public static void editCookie(HttpServletRequest request, HttpServletResponse response,
                                  String domain, String key, String value, int maxAge) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //迭代时如果发现与指定cookieName相同的cookie，就修改相关数据
                if (cookie.getName().equals(key)) {
                    cookie.setValue(value);
                    if (StringUtils.isNotBlank(domain)) {
                        cookie.setDomain(domain);
                    }
                    cookie.setPath("/");
                    cookie.setMaxAge(maxAge);
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * 删除cookie
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
                                    String domain, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //如果找到同名cookie，就将value设置为null，将存活时间设置为0
                if (cookie.getName().equals(key)) {
                    if (StringUtils.isNotBlank(domain)) {
                        cookie.setDomain(domain);
                    }
                    cookie.setPath("/");
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * 获取http body
     */
    public static String requestBody(ServletRequest request) throws IOException {
        try (InputStream is = request.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            int rc = 0;
            byte[] buff = new byte[100];
            while ((rc = is.read(buff, 0, 100)) > 0) {
                os.write(buff, 0, rc);
            }
            byte[] b = os.toByteArray();
            return new String(b, "utf8");
        }
    }

    /**
     * 获取ip地址
     */
    public static String getIpAddr(ServletRequest request) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return request.getRemoteAddr();
        }
        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        return ipAddrStr;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            return requestAttributes.getRequest();
        }
        return null;
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
