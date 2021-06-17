package com.xin.commons.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xin.commons.support.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
public class MetricsManager {

    private static final ThreadLocal<Metrics> threadLocalMetrics = new ThreadLocal<>();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static class staticHolder {

        private static final MetricsManager MANAGER = new MetricsManager();
    }

    private Metrics getMetrics() {
        Metrics metrics = threadLocalMetrics.get();
        if (metrics == null) {
            metrics = new Metrics();
            threadLocalMetrics.set(metrics);
        }
        return metrics;
    }

    public Metrics getMetricsCopy() {
        Metrics metrics = threadLocalMetrics.get();
        if (metrics == null) {
            metrics = new Metrics();
            threadLocalMetrics.set(metrics);
            return metrics;
        } else {
            Metrics metricsNew = new Metrics();
            BeanUtils.copyProperties(metrics, metricsNew);
            return metricsNew;
        }
    }

    public Metrics createNewMetrics() {
        threadLocalMetrics.set(new Metrics());
        return threadLocalMetrics.get();
    }

    public void close() {
        threadLocalMetrics.set(null);
    }

    public static MetricsManager getInstance() {
        return staticHolder.MANAGER;
    }

    public void setSpanId(String spanId) {
        getMetrics().setSpanId(spanId);
    }

    public void setRequestId(String requestId) {
        getMetrics().setRequestId(requestId);
    }

    public String getRequestId() {
        return getMetrics().getRequestId();
    }

    public void setPath(String path) {
        getMetrics().setPath(path);
    }

    public void setCode(int code) {
        getMetrics().setCode(code);
    }

    public void setUserId(String userId) {
        getMetrics().setUserId(userId);
    }

    public void setRemoteHost(String remoteHost) {
        getMetrics().setRemoteHost(remoteHost);
    }

    public String getRemoteHost() {
        return getMetrics().getRemoteHost();
    }

    public void setOperation(String operation) {
        getMetrics().setOperation(operation);
    }

    public String getOperation() {
        return getMetrics().getOperation();
    }

    public void setService(String service) {
        getMetrics().setService(service);
    }

    public void setTime(Long time) {
        getMetrics().setTime(time);
    }

    public void setStartTime(Date startTime) {
        getMetrics().setStartTime(startTime);
    }

    public void setHttpMethod(String httpMethod) {
        getMetrics().setHttpMethod(httpMethod);
    }

    public void setHttpCode(int httpCode) {
        getMetrics().setHttpCode(httpCode);
    }

    public void setInputParams(Object inputParams) {
        getMetrics().setInputParams(inputParams);
    }

    public void setHeaders(Map<String, String> headerMap) {
        getMetrics().setHeaders(headerMap);
    }

    public void setInputBody(Object inputBody) {
        getMetrics().setInputBody(inputBody);
    }

    public void setOutputBody(Object outputBody) {
        getMetrics().setOutputBody(outputBody);
    }

    public void setDepartment(String department) {
        getMetrics().setDepartment(department);
    }

    public void saveLog() {
        getMetrics().setTime(getSpanTime());
        getMetrics().setCode(getCode());
        getMetrics().setMsg(getMessage());
        Metrics metrics = getMetrics();
        try {
            String logStr = objectMapper.writeValueAsString(metrics);
            log.info("-[IO]" + logStr);
        } catch (JsonProcessingException e) {
        }
        threadLocalMetrics.remove();
    }

    private String getMessage() {
        Object outputBody = getMetrics().getOutputBody();
        if (outputBody instanceof ResponseResult) {
            ResponseResult result = (ResponseResult) outputBody;
            return result.getMsg();
        }
        return "";
    }

    private int getCode() {
        Object outputBody = getMetrics().getOutputBody();
        if (outputBody instanceof ResponseResult) {
            ResponseResult result = (ResponseResult) outputBody;
            return result.getCode();
        }
        return 0;
    }

    private long getSpanTime() {
        if (getMetrics().getStartTime() != null) {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(getMetrics().getStartTime());
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(new Date());
            return endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        } else {
            return 0l;
        }
    }
}
