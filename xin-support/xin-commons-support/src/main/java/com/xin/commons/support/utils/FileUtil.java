package com.xin.commons.support.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;


/**
 * 文件处理
 * @author: xin
 */
@Slf4j
public class FileUtil {

    //安卓所有的图片后缀名为 temp
    public static final String IMAGESUFFIXLIST = "jpg,gif,png,ico,bmp,jpeg,heic";

    public static final String VIDEOSUFFIXLIST = "rm,rmvb,mpeg,mov,wmv,avi,3gp,amv,dmv,flv,mp4";

    /**
     * 获取文件后缀名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        String extensionName = "";
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                extensionName = filename.substring(dot + 1);
            }
        }
        return extensionName;
    }

    /**
     * 生成存储目录
     * heic 是iphone拍的照片
     * @param diskName
     * @return
     */
    public static String getDiskName(String diskName, String extensionName) {
        String datatime = DateUtil.formatToShort();
        String result = "";
        if (StringUtils.isBlank(extensionName)) {
            return diskName + "/file/" + datatime + "/";
        }
        if (IMAGESUFFIXLIST.contains(extensionName.trim().toLowerCase())) {
            result = "image";
        } else if (VIDEOSUFFIXLIST.contains(extensionName.trim().toLowerCase())) {
            result = "video";
        } else {
            result = "file";
        }
        return diskName + "/" + result + "/" + datatime + "/";
    }

    /**
     * 生成文件名称
     *
     * @param
     * @return
     */
    public static String getFileName(String extensionName) {
        String fileName = "";
        if (StringUtils.isBlank(extensionName)) {
            fileName = UUID.randomUUID().toString();
        } else {
            fileName = UUID.randomUUID().toString() + "." + extensionName;
        }
        return fileName;
    }

    /**
     * 截取文件名称
     * filePath -->https://ok-public-hk.oss-cn-shenzhen.aliyuncs.com/blockchain/image/2018-08-03/63df0100-1d65-459e-926d-3239f843d655.png
     *
     * @param fileUrl
     * @param url     https://ok-public-hk.oss-cn-shenzhen.aliyuncs.com/
     * @return 63df0100-1d65-459e-926d-3239f843d655.png
     */
    public static String getFileNameByURL(String fileUrl, String url) {
        String fileName = "";
        if (StringUtils.isBlank(fileUrl)) {
            return "";
        }
        fileName = fileUrl.split(url)[1].split("/")[3];
        return fileName;
    }

    /**
     * 截取存储目录
     * filePath -->https://ok-public-hk.oss-cn-shenzhen.aliyuncs.com/blockchain/image/2018-08-03/63df0100-1d65-459e-926d-3239f843d655.png
     *
     * @param fileUrl
     * @param url     https://ok-public-hk.oss-cn-shenzhen.aliyuncs.com/
     * @return blockchain/image/2018-08-03/
     */
    public static String getDiskNameByURL(String fileUrl, String url) {

        String diskName = "";
        if (StringUtils.isBlank(fileUrl)) {
            return "";
        }
        String fileName = fileUrl.split(url)[1].split("/")[3];
        diskName = fileUrl.split(url)[1].split(fileName)[0];
        return diskName;
    }

    /**
     * 根据文件后缀和路径生成文件全路径
     *
     * @param filePath
     * @param fileSuffix
     * @return
     */
    public static String getFileNamePath(String filePath, String fileSuffix) {

        filePath = getDiskName(filePath, fileSuffix);

        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String fileNamePath = "";
        if (!StringUtils.substring(filePath, 0, 1).equals("/")) {
            filePath = "/" + filePath;
        }
        if (!StringUtils.substring(filePath, filePath.length() - 1, filePath.length()).equals("/")) {
            filePath = filePath + "/" + uuid;
        } else {
            filePath = filePath + uuid;
        }
        if (!StringUtils.isBlank(fileSuffix)) {
            if (!StringUtils.substring(fileSuffix, 0, 1).equals(".")) {
                fileSuffix = "." + fileSuffix;
            }
            fileNamePath = filePath + fileSuffix;
        } else {
            fileNamePath = filePath + fileSuffix;
        }
        return fileNamePath;
    }

    /**
     * 根据文件后缀和路径生成文件全路径
     * 带有宽 高
     *
     * @param filePath
     * @param fileSuffix
     * @return
     */
    public static String getImageNamePathByWeightAndHeigh(String filePath, String fileSuffix, int width, int height) {

        filePath = getDiskName(filePath, fileSuffix);

        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String fileNamePath = "";

        if (!StringUtils.substring(filePath, 0, 1).equals("/")) {
            filePath = "/" + filePath;
        }
        if (!StringUtils.substring(filePath, filePath.length() - 1, filePath.length()).equals("/")) {
            filePath = filePath + "/" + uuid;
        } else {
            filePath = filePath + uuid;
        }
        if (!StringUtils.isBlank(fileSuffix)) {
            if (!StringUtils.substring(fileSuffix, 0, 1).equals(".")) {
                fileSuffix = "." + fileSuffix;
            }
        }
        fileNamePath = filePath + "--" + width + "x" + height + fileSuffix;
        return fileNamePath;
    }

}
