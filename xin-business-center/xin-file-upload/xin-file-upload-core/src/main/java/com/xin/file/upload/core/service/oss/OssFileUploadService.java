package com.xin.file.upload.core.service.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.xin.file.upload.bean.dto.FileUploadMetadata;
import com.xin.file.upload.bean.vo.FileUploadVo;
import com.xin.file.upload.core.config.AbstractFileUploadConfig;
import com.xin.file.upload.core.service.AbstractFileUploadService;
import com.xin.file.upload.core.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * Aliyun OSS 文件上传服务类
 */
@Slf4j
public class OssFileUploadService extends AbstractFileUploadService implements FileUploadService {
    
    private final OSSClient ossClient;

    public OssFileUploadService(final AbstractFileUploadConfig config, final OSSClient ossClient) {
        super(config);
        this.ossClient = ossClient;
    }

    @Override
    public FileUploadVo uploadFile(final String fileName, final File file, final FileUploadMetadata uploadMetadata) {
        final String key = this.getKey(fileName);
        final ObjectMetadata objectMetadata = this.createObjectMetadata(uploadMetadata);
        this.ossClient.putObject(this.properties.getBucketName(), key, file, objectMetadata);
        return FileUploadVo.builder()
            .fileName(fileName)
            .key(key)
            .url(this.getSignedUrl(fileName))
            .build();
    }

    @Override
    public FileUploadVo uploadFile(final String fileName, final InputStream inputStream,
                                         final FileUploadMetadata uploadMetadata) {
        final String key = this.getKey(fileName);
        final ObjectMetadata objectMetadata = this.createObjectMetadata(uploadMetadata);
        this.ossClient.putObject(this.properties.getBucketName(), key, inputStream, objectMetadata);
        return FileUploadVo.builder()
            .fileName(fileName)
            .key(key)
            .url(this.getSignedUrl(fileName))
            .build();
    }

    @Override
    public boolean delete(final String fileName) {
        try {
            this.ossClient.deleteObject(this.properties.getBucketName(), this.getKey(fileName));
            return true;
        } catch (final Exception ex) {
            log.error("delete oss object failure", ex);
            return false;
        }
    }

    @Override
    protected String generatePresignedUrl(final String bucketName, final String fileName, final Date expiration) {
        final URL url = this.ossClient.generatePresignedUrl(bucketName, fileName, expiration);
        return url.toString();
    }

    /**
     * @param uploadMetadata
     * @return
     */
    private ObjectMetadata createObjectMetadata(final FileUploadMetadata uploadMetadata) {
        if (uploadMetadata == null) {
            return null;
        }
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(uploadMetadata.getContentType());
        objectMetadata.setContentLength(uploadMetadata.getContentLength());
        objectMetadata.setCacheControl("max-age=" + this.properties.getCacheControlMaxAge());
        return objectMetadata;
    }
}
