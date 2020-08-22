package com.xin.commons.support.log;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志拦截器，可以在这里做定制开发
 * @author: xin
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    private Environment environment;

    /**
     * preHandle 调用时间：Controller方法处理之前
     * postHandle 调用时间：Controller方法处理完之后
     * afterCompletion 调用时间 DispatcherServlet进行视图的渲染之后
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            MetricsManager metricsManager = MetricsManager.getInstance();
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> headerMap = getHeaderMap(request);
            metricsManager.setInputParams(parameterMap);
            metricsManager.setHeaders(headerMap);
            String userId = getUserId(request);
            metricsManager.setUserId(userId);
            metricsManager.setStartTime(new Date());
            // 设置部门
            metricsManager.setDepartment(environment.getProperty("xin.department", ""));
            // 设置path
            String path = request.getRequestURI();
            if (StringUtils.isNotBlank(path)) {
                metricsManager.setPath(path);
            }
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                String name = handlerMethod.getMethod().getName();
                metricsManager.setOperation(name);
            }
        } catch (Exception e) {
            log.error("LogInterceptor 异常：{}", ExceptionUtils.getStackTrace(e));
        }
        return true;
    }

    private String getUserId(HttpServletRequest request) {
        String userId = request.getHeader("uid");
        if (userId == null) {
            userId = request.getHeader("userId");
        }
        if (userId == null) {
            userId = request.getParameter("uid");
        }
        if (userId == null) {
            userId = request.getParameter("userId");
        }
        if (userId == null) {
            Object attribute = request.getAttribute("userId");
            if (attribute != null && attribute instanceof String) {
                userId = (String) attribute;
            }
        }
        return userId;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        MetricsManager metricsManager = MetricsManager.getInstance();
        String requestId = MDC.get("PtxId");// pinpoint 事务id
        metricsManager.setRequestId(requestId);
        metricsManager.setSpanId(MDC.get("PspanId"));
        metricsManager.setService(serviceName);
        metricsManager.setHttpMethod(request.getMethod());
        if (StringUtils.isNotBlank(request.getHeader("x-forwarded-for"))) {
            metricsManager.setRemoteHost(findRealIP(request.getHeader("x-forwarded-for")));
        }
        metricsManager.setHttpCode(response.getStatus());
        MetricsManager.getInstance().saveLog();
    }

    private Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            String headValue = request.getHeader(headName);
            map.put(headName, headValue);
        }
        return map;
    }

    public String findRealIP(String xForwardedFor) {
        if (xForwardedFor == null || xForwardedFor.trim().isEmpty()) {
            return null;
        }
        String[] ips = xForwardedFor.trim().split(", ");
        for (int idx = ips.length - 1; idx >= 0; idx--) {
            if (!ips[idx].startsWith("10.")) {
                return ips[idx];
            }
        }
        return null;
    }
}
