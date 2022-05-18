//package com.xin.api.gateway.filter.pre;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
//import javax.servlet.http.HttpServletRequest;
//import com.xin.api.gateway.client.AuthClient;
//import com.xin.api.gateway.common.consts.Constants;
//import com.xin.api.gateway.common.errorcode.ApiGateWayErrorCodeEnum;
//import com.xin.api.gateway.common.utils.ExceptionJsonUtil;
//import com.xin.api.gateway.common.utils.HttpUtils;
//import com.xin.api.gateway.service.IgnoredServices;
//import com.xin.auth.jwt.entity.JwtUserInfo;
//import com.xin.commons.support.response.ResponseResult;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import lombok.extern.slf4j.Slf4j;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 对token验证
// */
//@Slf4j
//@Component
//public class TokenZuulFilter extends ZuulFilter {
//
//    @Autowired
//    private IgnoredServices ignoredServices;
//
//    @Autowired
//    private AuthClient authClient;
//
//    /**
//     * 过滤器的类型 pre表示请求在路由之前被过滤 ERROR_TYPE = "error"; POST_TYPE = "post"; PRE_TYPE
//     * = "pre"; ROUTE_TYPE = "route";
//     *
//     * @return 类型
//     */
//    @Override
//    public String filterType() {
//        return PRE_TYPE;
//    }
//
//    /**
//     * 过滤器的执行顺序 数字越大表示优先级越低，越后执行 0 靠前执行 在spring cloud zuul提供的pre过滤器之后执行，默认的是小于0的
//     * 除了参数校验类的过滤器 一般上直接放在 PreDecoration前 即：PRE_DECORATION_FILTER_ORDER - 1;
//     * 常量类都在：org.springframework.cloud.netflix.zuul.filters.support.FilterConstants
//     * 下
//     */
//    @Override
//    public int filterOrder() {
//        return 1;
//    }
//
//    /**
//     * 指定需要执行该Filter的规则 返回true则执行run() 返回false则不执行run() 此方法可以根据请求的url进行判断是否需要拦截
//     */
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    /**
//     * 过滤逻辑（主逻辑）
//     *
//     * @return 过滤结果
//     */
//    @Override
//    public Object run() {
//        HttpServletRequest request = HttpUtils.getRequest();
//        String token = request.getHeader(Constants.AUTH_HEADER_KEY);
//        boolean isToken = ignoredServices.isTokenIgnored(request);
//        // 忽略token
//        if (isToken) {
//            log.info("TokenZuulFilter 忽略token和sn，直接放行, 请求方式：{}, 地址：{}, token：{}", request.getMethod(), request.getRequestURI(), token);
//            // 直接方行
//            return null;
//        }
//        // token都为空 阻止
//        if (StringUtils.isBlank(token)) {
//            log.error("TokenZuulFilter sn和token都为空，被阻拦, 请求方式：{}, 地址：{}, token：{}", request.getMethod(), request.getRequestURI(), token);
//            failed(ExceptionJsonUtil.getExceptionToJson(ApiGateWayErrorCodeEnum.TOKEN_IS_EMPTY));
//            return null;
//        }
//        //验证token
//        checkToken(token,request);
//        return null;
//    }
//
//    /**
//     * 验证Token
//     */
//    private void checkToken(String token,HttpServletRequest request) {
//        log.info("TokenZuulFilter-checkToken 验证, 请求方式：{}, 地址：{}, token：{}", request.getMethod(), request.getRequestURI(), token);
//        //如果只是验证token的有效性，使用这个
//        //ResponseResult<Boolean> ResponseResult = authServerClient.verifyToken(token);
//        //验证token和 获取用户的id，头像，用户名称
//        ResponseResult<JwtUserInfo> userInfoRestResult = authClient.getUserInfo(token);
//        if (ObjectUtils.isEmpty(userInfoRestResult)) {
//            log.error("AuthGlobalFilter-checkToken, getUserInfo 为空, 请求方式：{}, 地址：{}, token：{}", request.getMethod(), request.getRequestURL(), token);
//            failed(ExceptionJsonUtil.getExceptionToJson(ApiGateWayErrorCodeEnum.USER_IS_EMPTY));
//        }
//        //注意：业务错误码和成功码都必须自己定义，不能和http码一样
//        if (userInfoRestResult.getCode() != 0) {
//            log.error("AuthGlobalFilter-checkToken, 请求方式：{}, 请求地址：{}, token：{}, getUserInfo 返回 code: {}, message: {}",
//                    request.getMethod(), request.getRequestURL(),token, userInfoRestResult.getCode(), userInfoRestResult.getMsg());
//            failed(ExceptionJsonUtil.getExceptionToJson(ApiGateWayErrorCodeEnum.TOKEN_AUTH_FAILED));
//        }
//
//        JwtUserInfo userDto = userInfoRestResult.getData();
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", userDto.getUserId());
//        map.put("userName", userDto.getUserName());
//        map.put("headImage", userDto.getHeadImage());
//        //将用户信息设置到param上
//        HttpUtils.setParameterParams(map);
//        //将用户信息设置到hearder上
//        //HttpUtils.setRequestHeader(map);
//    }
//
//    // 失败后的处理
//    private void failed(String exceptBody) {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        // 避免中文乱码
//        ctx.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
//        ctx.getResponse().setCharacterEncoding("UTF-8");
//        ctx.setSendZuulResponse(false);
//        ctx.setResponseBody(exceptBody);
//        ctx.setResponseStatusCode(200);
//    }
//}