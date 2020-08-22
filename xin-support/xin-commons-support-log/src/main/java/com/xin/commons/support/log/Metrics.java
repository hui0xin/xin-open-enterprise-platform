package com.xin.commons.support.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 日志打印的内容实体
 * @author: xin
 */
@Data
class Metrics {

    private String requestId;

    private String userId;

    private String remoteHost;

    private String operation;

    private String service;

    private Long time;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date startTime;

    private String httpMethod;

    private int httpCode;

    private int code;

    private String path;

    private String spanId;

    private Object inputParams;

    private Object inputBody;

    private Object outputBody;

    private Object headers;

    private String department;

    private String msg;
}
