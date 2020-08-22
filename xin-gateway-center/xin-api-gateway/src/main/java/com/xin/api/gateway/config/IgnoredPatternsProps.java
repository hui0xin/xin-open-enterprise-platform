package com.xin.api.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 以下接口进不了网关，
 * 直接拒绝
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "spring.cloud.gateway")
public class IgnoredPatternsProps {

    private List<String> ignoredPatterns;
}
