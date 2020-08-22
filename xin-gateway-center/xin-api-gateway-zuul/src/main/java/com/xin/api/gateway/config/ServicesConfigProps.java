package com.xin.api.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 服务需要忽略验证的接口，
 * 忽略 toke
 * 忽略 sign
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "services")
public class ServicesConfigProps {

    //忽略token urls
    private List<String> tokenIgnoredUrls;

    //忽略sig认证 urls
    private List<String> sigIgnoredUrls;

}
