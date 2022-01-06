package com.xin.file.upload.core.service.oss;

import com.aliyun.oss.ClientConfiguration;
import com.google.common.collect.Maps;
import com.xin.file.upload.core.config.AbstractFileUploadConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 *
 */
@ConfigurationProperties(prefix = "xin.fileupload.oss")
public class OssConfig extends AbstractFileUploadConfig {
    /**
     * OssClient配置项
     * 该配置项为(可选)
     * oss client config
     */
    @NestedConfigurationProperty
    private ClientConfiguration config;

    /**
     * Oss文件上传client配置项
     * 如果有多个client需要配置，则启用该选项，否则不需要使用
     * 该配置项为(可选)
     */
    @NestedConfigurationProperty
    private Map<String, OssFileUploadClientConfig> clients = Maps.newHashMap();

    /**
     * 获取当前OssClient配置项
     *
     * @return @see OssClientConfig
     */
    public ClientConfiguration getConfig() {
        return this.config;
    }

    /**
     * 设置当前OssClient配置项
     *
     * @param config OssClient的配置项
     * @see ClientConfiguration
     */
    public void setConfig(final ClientConfiguration config) {
        this.config = config;
    }

    /**
     * 获取其他OssClient配置项
     *
     * @return @see OssClientConfig
     */
    public Map<String, OssFileUploadClientConfig> getClients() {
        return this.clients;
    }

    /**
     * 设置其他OssClient配置项
     */
    public void setClients(final Map<String, OssFileUploadClientConfig> clients) {
        this.clients = clients;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OssFileUploadClientConfig {
        protected String vpcEndpoint;
        protected String endpoint;
        protected String accessKeyId;
        protected String accessKeySecret;
        protected String bucketName;
        protected String path = "http";
        protected String urlScheme;
        protected long expiredTime = 1000 * 60 * 60 * 5L + 1000 * 60 * 30L;
        protected long cacheControlMaxAge = 60 * 60 * 24;
        protected ClientConfiguration config;
    }
}
