package com.xin.file.upload.config;//package com.xin.file.upload.common.utils;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import com.okcoin.commons.fileupload.oss.OssFileUploadService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * 文件上传工具类。
// */
//@Slf4j
////@Service
//public class OSSObjectUtil {
//    @Autowired
//    private OssFileUploadService ossFileUploadService;
//
//    /**
//     * 上传文件。
//     * @param key
//     * @param in
//     * @param size
//     * @param format
//     * @return
//     */
//    public int uploadToPrivateImageService(final String key, final InputStream in, final long size,
//                                           final String format) {
//        this.ossFileUploadService.uploadFile(key,in);
//        //this.ossFileUploadService.uploadFile(key, in, size, format);
//        return 1;
//    }
//
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
//            this.ossFileUploadService.uploadFile(key, in);
//        }else {
//            this.ossFileUploadService.uploadFile(key, in, length, "application/octet-stream");
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
//        return this.ossFileUploadService.getSignedUrl(key);
//    }
//
//    /**
//     * 获取图片查看链接。
//     * 兼容来自中国站的图片，通过originalName区分来自中国站，使用原来的oss来生成url
//     *
//     * @param key
//     * @return
//     */
//    public String getPrivateImageUrl(final String key, String originalName) {
//        return this.ossFileUploadService.getSignedUrl(key);
//    }
//
//
//    /**
//     * 获取图片路径。
//     *
//     * @param key
//     * @return
//     */
//    public String getFilePath(final String key) {
//        return this.ossFileUploadService.getDatePathFileName(key);
//    }
//
//    /**
//     * 删除指定的文件。
//     *
//     * @param key
//     * @return
//     */
//    public long deleteImage(final String key) {
//        return 0;
//    }
//
//}
