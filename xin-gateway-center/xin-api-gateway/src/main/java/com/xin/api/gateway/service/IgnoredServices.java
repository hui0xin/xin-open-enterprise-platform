package com.xin.api.gateway.service;

import com.xin.api.gateway.config.IgnoredPatternsProps;
import com.xin.api.gateway.config.ServicesConfigProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IgnoredServices {

    @Autowired
    private ServicesConfigProps servicesConfigProps;

    @Autowired
    private IgnoredPatternsProps ignoredPatternsProps;

    /**
     * 是否含有忽略token的url
     *
     * @return
     */
    public boolean isTokenIgnored(ServerHttpRequest request) {
        String requestURI = request.getURI().getPath();
        List<String> values = servicesConfigProps.getTokenIgnoredUrls();
        if (CollectionUtils.isEmpty(values)) {
            return true;
        }
        return values.stream().anyMatch(requestURI::startsWith);
    }

    /**
     * 是否含有忽略sig的url
     *
     * @param request
     * @return
     */
    public boolean isSigIgnored(ServerHttpRequest request) {
        String requestURI = request.getURI().getPath();
        List<String> values = servicesConfigProps.getSigIgnoredUrls();
        if (CollectionUtils.isEmpty(values)) {
            return true;
        }
        return values.stream().anyMatch(requestURI::startsWith);
    }

    /**
     * 忽略接口验证
     */
    public synchronized boolean isIgnoredPatterns(ServerHttpRequest request) {
        String requestURI = request.getURI().getPath();
        List<String> values = ignoredPatternsProps.getIgnoredPatterns();
        if (CollectionUtils.isEmpty(values)) {
            return false;
        }
        boolean result = false;
        Map<Integer, String> map = new ConcurrentHashMap<>();
        String[] strs = requestURI.split("/");
        for (int i = 1; i < strs.length; i++) {
            String str = strs[i];
            if (!"**".equals(str)) {
                map.put(i, str);
            }
        }
        for (String str : values) {
            String[] ignstrs = str.split("/");
            for (int j = 1; j < ignstrs.length; j++) {
                String ignstr = ignstrs[j];
                if (!"**".equals(ignstr)) {
                    String mapstr = map.get(j);
                    if (ignstr.equals(mapstr)) {
                        result = true;
                    } else {
                        result = false;
                        break;
                    }
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }
}