package com.xin.security.oauth2.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xin.commons.support.response.ResponseResult;
import com.xin.security.oauth2.constatns.CommonConstant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AuthExceptionEntryPoint implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Throwable cause = authException.getCause();
        int code =  CommonConstant.INVALID_TOKEN;
        String msg = null;
        String data = "请求路径："+request.getServletPath()+" | 请求时间："+String.valueOf(new Date().getTime())+" | 异常信息："+authException.getMessage();
        if(cause instanceof InvalidTokenException) {
            msg = "无效的token";
            code =  CommonConstant.INVALID_TOKEN;
        }else{
            msg = "访问此资源需要完全的身份验证";
            code =  CommonConstant.UN_LOGIN;
        }
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), ResponseResult.failure(code,msg,data));
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}