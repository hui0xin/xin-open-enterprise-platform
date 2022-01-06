package com.xin.security.oauth2.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xin.commons.support.response.ResponseResult;
import com.xin.security.oauth2.constatns.CommonConstant;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component("customAccessDeniedHandler")
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
        throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        int code =  CommonConstant.UNAUTHORIZED;
        String msg = "权限不足";
        String data = "请求路径："+request.getServletPath()+" | 请求时间："+String.valueOf(new Date().getTime())+" | 异常信息："+accessDeniedException.getMessage();

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(ResponseResult.failure(code,msg,data)));
    }
}