package com.xin.file.upload.core.service.s3;

import com.amazonaws.ClientConfiguration;
import com.google.common.collect.Maps;
import com.xin.file.upload.core.config.AbstractFileUploadConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * @author okcoin-team
 * @date 2017/8/20
 */
@ConfigurationProperties(prefix = "xin.fileupload.s3")
public class S3Config extends AbstractFileUploadConfig {
    /**
     * S3Client配置项
     * 该配置项为(可选)
     * oss client config
     */
    @NestedConfigurationProperty
    private ClientConfiguration config;

    /**
     * S3文件上传client配置项
     * 如果有多个client需要配置，则启用该选项，否则不需要使用
     * 该配置项为(可选)
     */
    @NestedConfigurationProperty
    private Map<String, S3FileUploadClientConfig> clients = Maps.newHashMap();

    /**
     * 获取当前S3Client配置项
     *
     * @return ClientConfiguration
     * @see ClientConfiguration
     */
    public ClientConfiguration getConfig() {
        return this.config;
    }

    /**
     * 设置当前S3Client配置项
     *
     * @param config S3Client的配置项
     * @see ClientConfiguration
     */
    public void setConfig(final ClientConfiguration config) {
        this.config = config;
    }

    /**
     * 获取其他S3Client配置项
     *
     * @return @see S3ClientConfig
     */
    public Map<String, S3FileUploadClientConfig> getClients() {
        return this.clients;
    }

    /**
     * 设置其他S3Client配置项
     */
    public void setClients(final Map<String, S3FileUploadClientConfig> clients) {
        this.clients = clients;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class S3FileUploadClientConfig {
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
