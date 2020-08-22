package com.xin.api.gateway.common.utils;

import com.xin.api.gateway.common.errorcode.ApiGateWayErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 将异常转化为json
 * " {\"code\":\"999500\",\"msg\":\"非法访问\"} "
 */
@Slf4j
@Component
public class ExceptionJsonUtil {

   public static String getExceptionToJson(ApiGateWayErrorCodeEnum errorCodeEnum){

       StringBuffer buffer = new StringBuffer();
       buffer.append("{\"code\":");
       buffer.append(errorCodeEnum.getCode());
       buffer.append(",\"msg\":\"");
       buffer.append(errorCodeEnum.getMessage());
       buffer.append("\"}");
       return buffer.toString();
   }
   public static String getExceptionToJson(int code, String msg){

       StringBuffer buffer = new StringBuffer();
       buffer.append("{\"code\":");
       buffer.append(code);
       buffer.append(",\"msg\":\"");
       buffer.append(msg);
       buffer.append("\"}");
       return buffer.toString();
   }

}
