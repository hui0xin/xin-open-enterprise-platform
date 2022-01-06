package com.xin.file.upload.config;//package com.xin.file.upload.common.utils;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import com.okcoin.commons.fileupload.s3.S3FileUploadService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 文件上传工具类。
// */
//@Slf4j
//@Service
//public class S3ObjectUtil {
//    @Autowired
//    private S3FileUploadService s3FileUploadService;
//
//    /**
//     * 上传文件。
//     * @param key
//     * @param in
//     * @return
//     */
//    public int uploadToPrivateImageService(final String key, final InputStream in) {
//        long length = 0;
//        try {
//            length = Integer.valueOf(in.available()).longValue();
//        }catch (IOException e){
//            log.error("上传图片，获取长度异常，使用不指定长度的方式上传：key={}", key);
//        }
//
//        if(length == 0){
//            this.s3FileUploadService.uploadFile(key, in);
//        }else {
//            this.s3FileUploadService.uploadFile(key, in, length, "application/octet-stream");
//        }
//        return 1;
//    }
//
//    /**
//     * 获取图片查看链接。
//     *
//     * @param key
//     * @return
//     */
//    public String getPrivateImageUrl(final String key) {
//        return this.s3FileUploadService.getSignedUrl(key);
//    }
//
//    /**
//     * 获取图片路径。
//     *
//     * @param key
//     * @return
//     */
//    public String getFilePath(final String key) {
//        return this.s3FileUploadService.getDatePathFileName(key);
//    }
//
//    /**
//     * 删除指定的文件。
//     *
//     * @param key
//     * @return
//     */
//    public boolean deleteImage(final String key) {
//        return this.s3FileUploadService.delete(key);
//    }
//
//}
