package com.xin.file.upload.common.config;

import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

/**
 * 上传下载相关的配置
 */
public class MultipartSupportConfig {
    /**
     * 引用配置类MultipartSupportConfig.并且实例化
     */
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringMultipartEncoder(new SpringEncoder(messageConverters));
    }

}