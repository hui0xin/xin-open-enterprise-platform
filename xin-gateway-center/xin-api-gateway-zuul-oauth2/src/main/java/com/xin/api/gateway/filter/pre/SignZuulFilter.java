package com.xin.api.gateway.filter.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xin.api.gateway.common.errorcode.ApiGateWayErrorCodeEnum;
import com.xin.api.gateway.common.utils.ExceptionJsonUtil;
import com.xin.api.gateway.common.utils.HttpUtils;
import com.xin.api.gateway.common.utils.SignUtil;
import com.xin.api.gateway.service.IgnoredServices;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 签名
 */
@Slf4j
@Component
public class SignZuulFilter extends ZuulFilter {

    @Autowired
    private SignUtil signUtil;

    @Autowired
    private IgnoredServices ignoredServices;

    /**
     * 过滤器的类型 pre表示请求在路由之前被过滤
     * ERROR_TYPE = "error";
     * POST_TYPE = "post";
     * PRE_TYPE = "pre";
     * ROUTE_TYPE = "route";
     *
     * @return 类型
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器的执行顺序 数字越大表示优先级越低，越后执行
     * 0 靠前执行 在spring cloud zuul提供的pre过滤器之后执行，默认的是小于0的
     * 除了参数校验类的过滤器 一般上直接放在 PreDecoration前
     * 即：PRE_DECORATION_FILTER_ORDER - 1;
     * 常量类都在：org.springframework.cloud.netflix.zuul.filters.support.FilterConstants 下
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 指定需要执行该Filter的规则
     * 返回true则执行run()
     * 返回false则不执行run()
     * 此方法可以根据请求的url进行判断是否需要拦截
     */
    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = HttpUtils.getRequest();
        return !ignoredServices.isSigIgnored(request);
    }

    /**
     * 过滤逻辑（主逻辑）
     * @return 过滤结果
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //打印日志
        Map<String, Object> paramsMap;
        try {
            paramsMap = SignUtil.getParams(request);
        } catch (Exception e) {
            log.error("SignZuulFilter-exception, 请求方式：{}, 地址：{}，验证失败: {}",request.getMethod(), request.getRequestURI(), ExceptionUtils.getStackTrace(e));
            return fail(ctx);
        }
        log.info("SignZuulFilter, 请求方式：{}, 地址：{}，showParams: {}",request.getMethod(), request.getRequestURI(), paramsMap);
        boolean status = signUtil.auth(paramsMap);
        if (!status) {
            log.error("SignZuulFilter-error, 请求方式：{}, 地址：{}，showParams: {}",request.getMethod(), request.getRequestURI(), paramsMap);
            return fail(ctx);
        }
        return null;
    }

    private Object fail(RequestContext ctx) {
        log.error(" 验证失败");
        //避免中文乱码
        ctx.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
        ctx.getResponse().setCharacterEncoding("UTF-8");
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(ExceptionJsonUtil.getExceptionToJson(ApiGateWayErrorCodeEnum.SIG_AUTH_FAILED));
        ctx.setResponseStatusCode(200);
        return null;
    }
}
