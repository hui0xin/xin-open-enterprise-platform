package com.xin.api.gateway.service;

import com.xin.api.gateway.config.ServicesConfigProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class IgnoredServices {

    @Autowired
    private ServicesConfigProps servicesConfigProps;

    /**
     * 是否含有忽略token的url
     * @param request
     * @return
     */
    public boolean isTokenIgnored(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        List<String> values = servicesConfigProps.getTokenIgnoredUrls();
        if (CollectionUtils.isEmpty(values)) {
            return true;
        }
        return values.stream().anyMatch(requestURI::startsWith);
    }

    /**
     * 是否含有忽略sig的url
     * @param request
     * @return
     */
    public boolean isSigIgnored(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        List<String> values = servicesConfigProps.getSigIgnoredUrls();
        if (CollectionUtils.isEmpty(values)) {
            return true;
        }
        return values.stream().anyMatch(requestURI::startsWith);
    }

}