package com.xin.file.upload.config.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.xin.file.upload.core.service.s3.S3Config;
import com.xin.file.upload.core.service.s3.S3FileUploadService;
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
 *
 */
@Configuration
@ConditionalOnClass(AmazonS3.class)
@EnableConfigurationProperties(S3FileUploadService.class)
public class S3AutoConfiguration implements BeanFactoryAware {

    private final S3Config s3Config;
    private final S3Config.S3FileUploadClientConfig defaultClientConfig;
    private BeanFactory beanFactory;

    public S3AutoConfiguration(final S3Config aa) {
        this.s3Config = aa;
        this.defaultClientConfig = this.createDefaultClientConfig(s3Config);
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private ClientConfiguration getClientConfiguration(final ClientConfiguration config) {
        return config == null ? new ClientConfiguration() : config;
    }

    @Bean(name = "s3Client")
    public AmazonS3 createDefaultS3Client() {
        return this.createS3Client(this.defaultClientConfig);
    }

    @Bean(name = "s3FileUploadService")
    public S3FileUploadService createDefaultS3FileService(@Qualifier("s3Client") final AmazonS3 s3Client) {
        return this.createS3FileUploadService(this.defaultClientConfig, s3Client);
    }

    @Bean
    public List<S3FileUploadService> createS3FileUploadServices() {
        final ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) this.beanFactory;
        final List<S3FileUploadService> s3FileUploadServices = new LinkedList<>();
        for (final Map.Entry<String, S3Config.S3FileUploadClientConfig> entry :
                this.s3Config.getClients().entrySet()) {
            final String clientBeanName = "s3" + entry.getKey();
            final String fileUploadServiceBeanName = clientBeanName + "FileUploadService";
            final S3Config.S3FileUploadClientConfig clientConfig = entry.getValue();
            final AmazonS3 s3Client = this.createS3Client(clientConfig);
            final S3FileUploadService fileUploadService = this.createS3FileUploadService(clientConfig, s3Client);
            configurableBeanFactory.registerSingleton(clientBeanName, s3Client);
            configurableBeanFactory.registerSingleton(fileUploadServiceBeanName, fileUploadService);
            s3FileUploadServices.add(fileUploadService);
        }
        return s3FileUploadServices;
    }

    private S3Config.S3FileUploadClientConfig createDefaultClientConfig(final S3Config s3Properties) {
        final S3Config.S3FileUploadClientConfig client = new S3Config.S3FileUploadClientConfig();
        client.setAccessKeyId(s3Properties.getAccessKeyId());
        client.setAccessKeySecret(s3Properties.getAccessKeySecret());
        client.setVpcEndpoint(s3Properties.getVpcEndpoint());
        client.setEndpoint(s3Properties.getEndpoint());
        client.setBucketName(s3Properties.getBucketName());
        client.setPath(s3Properties.getPath());
        client.setConfig(s3Properties.getConfig());
        client.setUrlScheme(s3Properties.getUrlScheme());
        client.setCacheControlMaxAge(s3Properties.getCacheControlMaxAge());
        client.setExpiredTime(s3Properties.getExpiredTime());
        return client;
    }

    private S3FileUploadService createS3FileUploadService(final S3Config.S3FileUploadClientConfig clientConfig,
                                                          final AmazonS3 s3Client) {
        final S3FileUploadConfig config = new S3FileUploadConfig();
        config.setVpcEndpoint(clientConfig.getVpcEndpoint());
        config.setEndpoint(clientConfig.getEndpoint());
        config.setBucketName(clientConfig.getBucketName());
        config.setCacheControlMaxAge(clientConfig.getCacheControlMaxAge());
        config.setPath(clientConfig.getPath());
        config.setExpiredTime(clientConfig.getExpiredTime());
        config.setUrlScheme(clientConfig.getUrlScheme());
        return new S3FileUploadService(config, s3Client);
    }

    private AmazonS3 createS3Client(final S3Config.S3FileUploadClientConfig client) {
        final AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard();
        s3ClientBuilder.setClientConfiguration(this.getClientConfiguration(client.getConfig()));
        s3ClientBuilder.withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(this.s3Config.getAccessKeyId(), this.s3Config.getAccessKeySecret()))
        );
        final AmazonS3 s3Client = s3ClientBuilder.build();
        s3Client.setEndpoint(this.s3Config.getVpcEndpoint());
        return s3Client;
    }
}
