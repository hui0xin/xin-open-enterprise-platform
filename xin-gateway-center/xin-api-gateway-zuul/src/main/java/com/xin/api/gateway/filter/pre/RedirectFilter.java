package com.xin.api.gateway.filter.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xin.api.gateway.bean.RedirectEntity;
import com.xin.api.gateway.common.utils.HttpUtils;
import com.xin.api.gateway.config.RedirectConfigProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 重定向 拦截器
 */
@Slf4j
@Component
public class RedirectFilter extends ZuulFilter {

    @Autowired
    private RedirectConfigProps redirectConfig;

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
        return 6;
    }

    /**
     * 指定需要执行该Filter的规则
     * 返回true则执行run()
     * 返回false则不执行run()
     * 此方法可以根据请求的url进行判断是否需要拦截
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = HttpUtils.getRequest();
        // /user/hh/cc/dd
        String uri = request.getRequestURI();
        //拿到所有的配置规则
        Map<String, RedirectEntity> routesMap = redirectConfig.getRoutes();
        if(ObjectUtils.isEmpty(routesMap)){
            //直接放行
            return null;
        }
        String uriStr = null;
        String serviceIdStr = null;
        for (Map.Entry<String, RedirectEntity> entry : routesMap.entrySet()) {
            RedirectEntity mapValue = entry.getValue();
            String paths = mapValue.getPaths();
            String serviceIds = mapValue.getPaths();
            // /user/hh/** , /auth/mm/hh1/**
            String oldUri = paths.split(",")[0] ;
            String newUri = paths.split(",")[1] ;;
            // /user/hh 去掉"/**"
            String uriH = oldUri.split("\\/*\\*")[0];
            String uriM = newUri.split("\\/*\\*")[0];
            if(uri.contains(uriH)){
                serviceIdStr = serviceIds.split(",")[1];
                uriStr = uriM + uri.split(uriH)[1];
            }
        }
        if(StringUtils.isEmpty(serviceIdStr)){
            //直接放行
            return null;
        }
        //避免中文乱码
        context.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
        context.getResponse().setCharacterEncoding("UTF-8");
        //重新组装请求信息
        context.put(FilterConstants.REQUEST_URI_KEY, uriStr);
        // serverID
        context.put(FilterConstants.PROXY_KEY, serviceIdStr);
        //新的serverId
        context.put(FilterConstants.SERVICE_ID_KEY, serviceIdStr);
        log.info("RedirectFilter-结束, 请求方式：{}, 地址：{}, path: {}",request.getMethod(), request.getRequestURI(),uriStr);
        return null;
    }

}
