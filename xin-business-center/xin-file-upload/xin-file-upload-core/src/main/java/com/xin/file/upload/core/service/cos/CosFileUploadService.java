package com.xin.file.upload.core.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * FileName: FileUploadService
 * Author: xin
 * Date: 2019/1/10 14:24
 * Description: cos文件上传
 */
@Slf4j
@Service
public class CosFileUploadService{

    private String secretId = "xxx";
    private String secretKey = "xxx";
    private String regionName = "ap-beijing";
    private String bucketName = "xxx-1255510688";

    private COSCredentials cred;
    private ClientConfig clientConfig;

    public CosFileUploadService() {
        cred = new BasicCOSCredentials(secretId, secretKey);
        clientConfig = new ClientConfig(new Region(regionName));
    }

    /**
     * 文件上传 将本地文件上传到 COS
     *
     * @param localFile     本地上传文件路径
     * @param fileName 文件名称
     */
    public Boolean uploadFile(File localFile, String fileName) {
        Boolean result = false;
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            cosClient.putObject(bucketName, fileName, localFile);
            result = true;
        } catch (Exception e) {
            log.error("文件上传失败：{}", ExceptionUtils.getStackTrace(e));
        } finally {
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }
        return result;

    }

    /**
     * 文件上传 输入流上传到 COS
     *
     * FileInputStream fileInputStream = new FileInputStream(localFile);
     * ObjectMetadata objectMetadata = new ObjectMetadata();
     * objectMetadata.setContentLength(500);  // 设置输入流长度为500
     * objectMetadata.setContentType("application/pdf"); // 设置 Content type, 默认是 application/octet-stream
     * @param input    输入流
     */
    public Boolean uploadFile(String fileName, InputStream input) {
        MultipartFile imageFile =null;

        Boolean result = false;
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            //文件的元信息
            ObjectMetadata metadata = new ObjectMetadata();
            // input.available() 获取文件大小，从输入流上传必须指定content length,
            // 否则http客户端可能会缓存所有数据，存在内存OOM的情况
            metadata.setContentLength(input.available());
            cosClient.putObject(bucketName, fileName, input, metadata);
            result = true;
        } catch (Exception e) {
            log.error("文件上传失败：{}", ExceptionUtils.getStackTrace(e));
        } finally {
            cosClient.shutdown();
        }
        return result;
    }
    /**
     * 文件上传 输入流上传到 COS
     *
     * FileInputStream fileInputStream = new FileInputStream(localFile);
     * ObjectMetadata objectMetadata = new ObjectMetadata();
     * objectMetadata.setContentLength(500);  // 设置输入流长度为500
     * objectMetadata.setContentType("application/pdf"); // 设置 Content type, 默认是 application/octet-stream
     * @param input    输入流
     * @param metadata  注意，这里需要设置它的长度 ，从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
     */
    public Boolean uploadFile(String fileName, InputStream input,ObjectMetadata metadata) {
        Boolean result = false;
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            cosClient.putObject(bucketName, fileName, input, metadata);
            result = true;
        } catch (Exception e) {
            log.error("文件上传失败：{}", ExceptionUtils.getStackTrace(e));
        } finally {
            cosClient.shutdown();
        }
        return result;
    }

    /**
     * 文件上传
     * 对以上两个方法的包装, 支持更细粒度的参数控制, 如 content-type,  content-disposition 等
     * PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, localFile);
     * putObjectRequest.setStorageClass(StorageClass.Standard_IA);// 设置存储类型为低频
     * ObjectMetadata objectMetadata = new ObjectMetadata();// 设置自定义属性(如 content-type, content-disposition 等)
     * objectMetadata.setContentType("image/jpeg");// 设置 Content type, 默认是 application/octet-stream
     * putObjectRequest.setMetadata(objectMetadata);
     * @param putObjectRequest    上传文件请求体
     */
    public Boolean uploadFile(String fileName, PutObjectRequest putObjectRequest) {

        Boolean result = false;
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            cosClient.putObject(putObjectRequest);
            result = true;
        } catch (Exception e) {
            log.error("文件上传失败：{}", ExceptionUtils.getStackTrace(e));
        } finally {
            cosClient.shutdown();
        }
        return result;

    }

    /**
     * 下载文件
     *
     * @param downFile 本地存放文件路径
     * @param fileName cos上文件名称
     */
    public Boolean downFile(File downFile, String fileName) {
        Boolean result = false;
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            //file中截取文件名称
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
            cosClient.getObject(getObjectRequest, downFile);
            result = true;
        } catch (Exception e) {
            log.error("文件下载失败：{}", ExceptionUtils.getStackTrace(e));
        } finally {
            cosClient.shutdown();
        }
        return result;
    }


    /**
     * 获取下载输入流
     *
     * @param fileName cos上文件名称
     */
    public COSObjectInputStream COSObjectInputStream(String fileName) {

        COSObjectInputStream cosObjectInput = null;
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
            COSObject cosObject = cosClient.getObject(getObjectRequest);
            cosObjectInput = cosObject.getObjectContent();
        } catch (Exception e) {
            log.error("获取下载流失败：{}", ExceptionUtils.getStackTrace(e));
        } finally {
            cosClient.shutdown();
        }
        return cosObjectInput;
    }

    /**
     * 删除文件
     *
     * @param fileName cos上文件名称
     */
    public Boolean deleteFile(String fileName) {
        Boolean result = false;
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            //file中截取文件名称
            cosClient.deleteObject(bucketName, fileName);
            result = true;
        } catch (Exception e) {
            log.error("文件下载失败：{}", ExceptionUtils.getStackTrace(e));
        } finally {
            cosClient.shutdown();
        }
        return result;
    }

}

