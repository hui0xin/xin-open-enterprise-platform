package com.xin.api.gateway.config;

import com.xin.api.gateway.bean.RedirectEntity;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;

/**
 * 重定向配置
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "redirect")
public class RedirectConfigProps {

    private LinkedHashMap<String, RedirectEntity> routes;

}
