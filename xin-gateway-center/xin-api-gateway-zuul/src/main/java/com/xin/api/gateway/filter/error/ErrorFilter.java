package com.xin.api.gateway.filter.error;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xin.api.gateway.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * 异常过滤器
 */
@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_ERROR_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
}

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = HttpUtils.getRequest();
        Throwable throwable = getCause(context.getThrowable());
        // 获取response状态码
        int status = context.getResponseStatusCode();
        JSONObject info = new JSONObject();
        info.put("code",status);
        info.put("message", throwable.getMessage());
        // 记录日志
        log.error("请求异常，被errorFilter拦截 请求方式：{},  地址：{}， 失败信息 : {}, 错误信息 : {}",request.getMethod(), request.getRequestURI(), context.getClass(),getCause(throwable));
        // 设置response
        context.setResponseBody(info.toJSONString());
        context.getResponse().setContentType("application/json;charset=UTF-8");
        context.getResponse().setStatus(HttpStatus.OK.value());
        // 处理了异常之后清空异常
        context.remove("throwable");
        return null;
    }

    private Throwable getCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }
}