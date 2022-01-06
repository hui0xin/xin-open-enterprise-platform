package com.xin.file.upload.config.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.xin.file.upload.core.service.oss.OssFileUploadService;
import com.xin.file.upload.core.service.oss.OssConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * oss config
 */
@Configuration
@ConditionalOnClass(OssFileUploadService.class)
@EnableConfigurationProperties(OssConfig.class)
public class OssAutoConfiguration implements BeanFactoryAware {

    private final OssConfig ossConfig;
    private final OssConfig.OssFileUploadClientConfig defaultClientConfig;
    private BeanFactory beanFactory;

    public OssAutoConfiguration(final OssConfig ossConfig) {
        this.ossConfig = ossConfig;
        this.defaultClientConfig = this.createDefaultClientConfig(ossConfig);
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private ClientConfiguration getClientConfiguration(final ClientConfiguration config) {
        return config == null ? new ClientConfiguration() : config;
    }

    @Bean(name = "ossClient")
    public OSSClient createDefaultOssClient() {
        return this.createOssClient(this.defaultClientConfig);
    }

    @Bean(name = "ossFileUploadService")
    public OssFileUploadService createDefaultOssFileService(@Qualifier("ossClient") final OSSClient ossClient) {
        return this.createOssFileUploadService(this.defaultClientConfig, ossClient);
    }

    @Bean
    public List<OssFileUploadService> createOssFileUploadServices() {
        final ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) this.beanFactory;
        final List<OssFileUploadService> ossFileUploadServices = new LinkedList<>();
        for (final Map.Entry<String, OssConfig.OssFileUploadClientConfig> entry :
                this.ossConfig.getClients().entrySet()) {
            final String clientBeanName = "oss" + entry.getKey();
            final String fileUploadServiceBeanName = clientBeanName + "FileUploadService";
            final OssConfig.OssFileUploadClientConfig clientConfig = entry.getValue();
            final OSSClient ossClient = this.createOssClient(clientConfig);
            final OssFileUploadService fileUploadService = this.createOssFileUploadService(clientConfig, ossClient);
            configurableBeanFactory.registerSingleton(clientBeanName, ossClient);
            configurableBeanFactory.registerSingleton(fileUploadServiceBeanName, fileUploadService);
            ossFileUploadServices.add(fileUploadService);
        }
        return ossFileUploadServices;
    }

    private OssConfig.OssFileUploadClientConfig createDefaultClientConfig(final OssConfig ossProperties) {
        final OssConfig.OssFileUploadClientConfig client = new OssConfig.OssFileUploadClientConfig();
        client.setAccessKeyId(ossProperties.getAccessKeyId());
        client.setAccessKeySecret(ossProperties.getAccessKeySecret());
        client.setVpcEndpoint(ossProperties.getVpcEndpoint());
        client.setEndpoint(ossProperties.getEndpoint());
        client.setBucketName(ossProperties.getBucketName());
        client.setPath(ossProperties.getPath());
        client.setConfig(ossProperties.getConfig());
        client.setUrlScheme(ossProperties.getUrlScheme());
        client.setCacheControlMaxAge(ossProperties.getCacheControlMaxAge());
        client.setExpiredTime(ossProperties.getExpiredTime());
        return client;
    }

    private OssFileUploadService createOssFileUploadService(final OssConfig.OssFileUploadClientConfig clientConfig,
                                                            final OSSClient ossClient) {
        final OssFileUploadConfig config = new OssFileUploadConfig();
        config.setVpcEndpoint(clientConfig.getVpcEndpoint());
        config.setEndpoint(clientConfig.getEndpoint());
        config.setBucketName(clientConfig.getBucketName());
        config.setCacheControlMaxAge(clientConfig.getCacheControlMaxAge());
        config.setPath(clientConfig.getPath());
        config.setExpiredTime(clientConfig.getExpiredTime());
        config.setUrlScheme(clientConfig.getUrlScheme());
        return new OssFileUploadService(config, ossClient);
    }

    private OSSClient createOssClient(final OssConfig.OssFileUploadClientConfig client) {
        return new OSSClient(
                client.getVpcEndpoint(),
                client.getAccessKeyId(),
                client.getAccessKeySecret(),
                this.getClientConfiguration(client.getConfig())
        );
    }
}
