package com.xin.file.upload.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadVo{

    /**
     * 存储到oss或s3上的文件名(带相对路径)
     */
    private String fileName;

    /**
     * 存储到oss或s3上的key
     */
    private String key;

    /**
     * 上传后在oss或s3上可访问的url地址
     */
    private String url;
}
