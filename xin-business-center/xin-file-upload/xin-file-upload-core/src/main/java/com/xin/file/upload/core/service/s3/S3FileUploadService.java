package com.xin.file.upload.core.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
 * AWS S3 文件上传服务类
 */
@Slf4j
public class S3FileUploadService extends AbstractFileUploadService implements FileUploadService {
    
    private final AmazonS3 s3Client;

    public S3FileUploadService(final AbstractFileUploadConfig properties, final AmazonS3 s3Client) {
        super(properties);
        this.s3Client = s3Client;
    }

    @Override
    public FileUploadVo uploadFile(final String fileName, final File file, final FileUploadMetadata uploadMetadata) {
        final String key = this.getKey(fileName);
        final ObjectMetadata objectMetadata = this.createObjectMetadata(uploadMetadata);
        this.s3Client.putObject(new PutObjectRequest(this.properties.getBucketName(), key, file)
            .withMetadata(objectMetadata));
        return FileUploadVo.builder()
            .fileName(fileName)
            .key(key)
            .url(this.getSignedUrl(fileName))
            .build();
    }

    @Override
    public FileUploadVo uploadFile(final String fileName, final InputStream inputStream, final FileUploadMetadata uploadMetadata) {
        final String key = this.getKey(fileName);
        final ObjectMetadata objectMetadata = this.createObjectMetadata(uploadMetadata);
        this.s3Client.putObject(this.properties.getBucketName(), key, inputStream, objectMetadata);
        return FileUploadVo.builder()
            .fileName(fileName)
            .key(key)
            .url(this.getSignedUrl(fileName))
            .build();
    }

    @Override
    public boolean delete(final String fileName) {
        try {
            this.s3Client.deleteObject(this.properties.getBucketName(), this.getKey(fileName));
            return true;
        } catch (final Exception ex) {
            log.error("delete s3 object failure", ex);
            return false;
        }
    }

    @Override
    protected String generatePresignedUrl(final String bucketName, final String fileName, final Date expiration) {
        final URL url = this.s3Client.generatePresignedUrl(bucketName, fileName, expiration);
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
        objectMetadata.setContentLength(uploadMetadata.getContentLength());
        objectMetadata.setContentType(uploadMetadata.getContentType());
        objectMetadata.setCacheControl("max-age=" + this.properties.getCacheControlMaxAge());
        return objectMetadata;
    }
}
