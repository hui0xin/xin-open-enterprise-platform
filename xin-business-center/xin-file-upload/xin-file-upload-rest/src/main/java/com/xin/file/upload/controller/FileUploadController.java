package com.xin.file.upload.controller;

import com.xin.commons.redis.service.RedisService;
import com.xin.commons.support.errorcode.SystemErrorCode;
import com.xin.commons.support.exception.SystemException;
import com.xin.commons.support.response.ResponseResult;
import com.xin.commons.support.utils.FileUtil;
import com.xin.file.upload.bean.bo.Base64Bo;
import com.xin.file.upload.bean.bo.FileBitBo;
import com.xin.file.upload.common.errorcode.FileUploadErrorCode;
import com.xin.file.upload.common.exception.FileUploadException;
import com.xin.file.upload.common.utils.PcmUtils;
import com.xin.file.upload.core.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/common")
@Api(value = "文件上传controller", tags = { "文件上传接口" })
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private RedisService redisService;

    private final static String OPERMALLIMAGEUIL = "https://xxx.xxx.com";
    private final static String STYLE = "!s2";
    private final static String PCMPATH = "/data1/file/cpmTempFile.pcm";
    private final static String MP3PATH = "/data1/file/cpmTempFile.mp3";


    @ApiOperation(value = "图片批量上传，解析出图片长，宽", notes = "图片批量上传，解析出图片长，宽")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/image_uploads")
    public ResponseResult imageUploads(@RequestParam("files") MultipartFile[] files, @RequestParam(value = "filePath") String filePath) {
        if (ObjectUtils.isEmpty(files)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_IS_NULL);
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile multipartFile = files[i];
            if (ObjectUtils.isEmpty(multipartFile)) {
                log.error("批量上传图片接口imageUploads 的第 {} 个 multipartFile 文件为空",i);
                continue;
            }
            try {
                String fileNamePath = uploadImage(multipartFile,filePath,false);
                if(!StringUtils.isBlank(fileNamePath)){
                    list.add(OPERMALLIMAGEUIL + fileNamePath + STYLE);
                }
            }catch (FileUploadException e) {
                log.error("批量上传图片接口imageUploads 的第 {} 个 异常 : {}",i,ExceptionUtils.getStackTrace(e));
                continue;
            }
        }
        return ResponseResult.success(list);
    }

    @ApiOperation(value = "单个图片上传，解析出图片长，宽", notes = "单个图片上传，解析出图片长，宽")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/image_upload")
    public ResponseResult imageUpload(@RequestParam("mFile") MultipartFile mFile, @RequestParam(value = "filePath") String filePath) {
        if (ObjectUtils.isEmpty(mFile)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_IS_NULL);
        }
        String fileNamePath = uploadImage(mFile,filePath,false);
        if(StringUtils.isBlank(fileNamePath)){
            return ResponseResult.failure(FileUploadErrorCode.FILE_UPLOAD_FAIL);
        }
        return ResponseResult.success(OPERMALLIMAGEUIL + fileNamePath + STYLE);
    }

    @ApiOperation(value = "单个图片文件上传，解析出图片长，宽,对文件进行md5加密", notes = "单个图片文件上传，解析出图片长，宽对文件进行md5加密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/multUpload")
    public ResponseResult multUpload(@RequestParam("file") MultipartFile file, @RequestParam("filePath") String filePath) {
        if (ObjectUtils.isEmpty(file)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_IS_NULL);
        }
        String fileNamePath = uploadImage(file,filePath,true);
        if(StringUtils.isBlank(fileNamePath)){
            return ResponseResult.failure(FileUploadErrorCode.FILE_UPLOAD_FAIL);
        }
        return ResponseResult.success(OPERMALLIMAGEUIL + fileNamePath + STYLE);
    }

    @ApiOperation(value = "单个文件上传,不进行MD5加密", notes = "单个文件上传,不进行MD5加密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/fileupload")
    public ResponseResult fileupload(@RequestParam("file") MultipartFile file, @RequestParam("filePath") String filePath) {
        if (ObjectUtils.isEmpty(file)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_IS_NULL);
        }
        String fileNamePath = uploadFile(file,filePath,false);
        if(StringUtils.isBlank(fileNamePath)){
            return ResponseResult.failure(FileUploadErrorCode.FILE_UPLOAD_FAIL);
        }
        return ResponseResult.success(OPERMALLIMAGEUIL + fileNamePath + STYLE);
    }

    @ApiOperation(value = "以文件base64字符串上传", notes = "以文件base64字符串上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileBase64Entity", value = "图片base64字符串", required = true, dataType = "FileBase64Entity", paramType = "query") })
    @PostMapping(value = "v1/base64Upload")
    public ResponseResult base64Upload(@RequestBody Base64Bo base64Bo) {
        if (ObjectUtils.isEmpty(base64Bo)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_IS_NULL);
        }
        String fileNamePath;
        try {
            // 将字符串转换为byte数组
            byte[] bytes = Base64.getDecoder().decode(base64Bo.getStrBase64());
            // 将字节码转化为输入流
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            String filePath = base64Bo.getFilePath();
            String fileSuffix = base64Bo.getFileSuffix();
            if (StringUtils.isBlank(filePath)) {
                return ResponseResult.failure(FileUploadErrorCode.FILE_PATH_IS_NULL);
            }
            fileNamePath = FileUtil.getFileNamePath(filePath, fileSuffix);
            Boolean result = fileUploadService.uploadFile(fileNamePath, input);
            if (!result) {
                return ResponseResult.failure(FileUploadErrorCode.FILE_UPLOAD_FAIL);
            }
        } catch (Exception e) {
            log.error("文件上传失败：{}", ExceptionUtils.getStackTrace(e));
            return ResponseResult.failure(SystemErrorCode.SYSTEM_ERROR);
        }
        return ResponseResult.success(OPERMALLIMAGEUIL + fileNamePath);
    }

    @ApiOperation(value = "以文件的bit数组上传", notes = "以文件的bit数组上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileBitEntity", value = "文件bit数组实体", required = true, dataType = "FileBitEntity", paramType = "query") })
    @PostMapping(value = "v1/bitFileUpload")
    public ResponseResult bitFileUpload(@RequestBody FileBitBo fileBitBo) {
        if (ObjectUtils.isEmpty(fileBitBo)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_IS_NULL);
        }
        String fileNamePath;
        try {
            // 将字符串转换为byte数组
            byte[] bytes = fileBitBo.getByteFile();
            //用于存储零时文件，下次会覆盖
            byteToFile(bytes,PCMPATH);
            //将pcm文件转成mp3
            PcmUtils.convert2Wav(PCMPATH,MP3PATH,16000, 1, 16);
            String filePath = fileBitBo.getFilePath();
            String fileSuffix = fileBitBo.getFileSuffix();
            if (StringUtils.isBlank(filePath)) {
                return ResponseResult.failure(FileUploadErrorCode.FILE_PATH_IS_NULL);
            }
            fileNamePath = FileUtil.getFileNamePath(filePath, fileSuffix);
            //将本地的mp3文件上传到cos
            File file = new File(MP3PATH);
            Boolean result = fileUploadService.uploadFile(file,fileNamePath);
            if (!result) {
                return ResponseResult.failure(FileUploadErrorCode.FILE_UPLOAD_FAIL);
            }
        } catch (Exception e) {
            log.error("文件上传失败：{}", ExceptionUtils.getStackTrace(e));
            return ResponseResult.failure(SystemErrorCode.SYSTEM_ERROR);
        }
        return ResponseResult.success(OPERMALLIMAGEUIL + fileNamePath);
    }

    @ApiOperation(value = "文件下载", notes = "文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "downFile", value = "保存到本地的地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "filePathName", value = "文件在cos上的地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/downFile")
    public ResponseResult downFile(@RequestParam("downFile") String downFile, @RequestParam("filePathName") String filePathName) {
        if (StringUtils.isBlank(downFile)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_DOWN_URL_IS_NULL);
        }
        if (StringUtils.isBlank(filePathName)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_PATH_IS_NULL);
        }
        try {
            Boolean result = fileUploadService.downFile(new File(downFile), filePathName);
            if (!result) {
                return ResponseResult.failure(FileUploadErrorCode.FILE_DELETE_FAIL);
            }
        } catch (Exception e) {
            log.error("文件下载失败：{}", ExceptionUtils.getStackTrace(e));
            return ResponseResult.failure(SystemErrorCode.SYSTEM_ERROR);
        }
        return ResponseResult.success();
    }

    @ApiOperation(value = "删除文件", notes = "删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePathName", value = "文件在阿里云oss路径,包括名称", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/deleteFile")
    public ResponseResult deleteFile(@RequestParam("filePathName") String filePathName) {
        if (StringUtils.isBlank(filePathName)) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_PATH_IS_NULL);
        }
        Boolean result = fileUploadService.deleteFile(filePathName);
        if (!result) {
            return ResponseResult.failure(FileUploadErrorCode.FILE_DELETE_FAIL);
        }
        return ResponseResult.success();
    }

    /**
     * 图片上传
     * @param imageFile 图片文件
     * @param imageFilePath 图片地址，一般为项目的加功能组成的
     * @param isMd5 是否进行md5操作  true 要进行加密
     * @return
     */
    private String uploadImage(MultipartFile imageFile,String imageFilePath,Boolean isMd5){
        String fileNamePath = "";
        InputStream inputStream = null;
        try {
            // 获取文件扩展名
            String fileSuffix = FileUtil.getExtensionName(imageFile.getOriginalFilename());
            //文件高 给个默认值 兼容以前的老板
            int height = 4032;
            // 文件宽 给个默认值 兼容以前的老板
            int width = 3024;
            inputStream = imageFile.getInputStream();
            // 指定要上传到 COS 上的路径，包括名称
            fileNamePath = FileUtil.getImageNamePathByWeightAndHeigh(imageFilePath, fileSuffix, width, height);
            Boolean result = fileUploadService.uploadFile(fileNamePath,inputStream);
            if (!result) {
                log.error("图片上传到cos失败");
                throw new FileUploadException(FileUploadErrorCode.FILE_UPLOAD_FAIL);
            }
            if(isMd5){
                //文件MD5操作
                setUpFileMD5(imageFile,OPERMALLIMAGEUIL + fileNamePath + STYLE);
            }
        } catch (Exception e) {
            log.error("图片上传异常：{}", ExceptionUtils.getStackTrace(e));
            throw new SystemException(SystemErrorCode.SYSTEM_ERROR);
        } finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (Exception e){
                log.error("图片流关闭异常：{}", ExceptionUtils.getStackTrace(e));
                throw new SystemException(SystemErrorCode.SYSTEM_ERROR);
            }
        }
        return fileNamePath;
    }

    /**
     * 文件上传
     * @param file 文件
     * @param filePath 文件地址，一般为项目的加功能组成的
     * @param isMd5 是否进行md5操作 true 要进行加密
     * @return
     */
    private String uploadFile(MultipartFile file,String filePath,Boolean isMd5){
        String fileNamePath = "";
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            // 获取文件扩展名
            String fileSuffix = FileUtil.getExtensionName(file.getOriginalFilename());
            // 指定要上传到 COS 上的路径
            fileNamePath = FileUtil.getFileNamePath(filePath, fileSuffix);
            Boolean result = fileUploadService.uploadFile(fileNamePath,inputStream);
            if (!result) {
                log.error("长传文件到cos失败");
                throw new FileUploadException(FileUploadErrorCode.FILE_UPLOAD_FAIL);
            }
            if(isMd5){
                //文件MD5操作
                setUpFileMD5(file,OPERMALLIMAGEUIL + fileNamePath + STYLE);
            }
        } catch (Exception e) {
            log.error("文件上传异常：{}", ExceptionUtils.getStackTrace(e));
            throw new SystemException(SystemErrorCode.SYSTEM_ERROR);
        } finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (Exception e){
                log.error("文件流关闭异常：{}", ExceptionUtils.getStackTrace(e));
                throw new SystemException(SystemErrorCode.SYSTEM_ERROR);
            }
        }
        return fileNamePath;
    }
    /**
     * 设置文件的名称和文件的MD5值
     */
    private void setUpFileMD5(MultipartFile multipartFile,String name){
        if (!Objects.isNull(multipartFile)){
            InputStream inputStream = null;
            try {
                inputStream = multipartFile.getInputStream();
                String value = DigestUtils.md5Hex(inputStream);
                String key = DigestUtils.md5Hex(name);
                String keyPrefix = "xx:xx:xx:";
                redisService.set(keyPrefix+key,value);
                redisService.expire(keyPrefix+key,3600L);
            } catch (IOException e) {
                log.error("文件/文件名称转换MD5失败，错误信息{}",ExceptionUtils.getStackTrace(e));
            } finally {
                try {
                    if(inputStream!=null){
                        inputStream.close();
                    }
                }catch (Exception e){
                    log.error("setUpFileMD5文件流关闭异常：{}", ExceptionUtils.getStackTrace(e));
                    throw new SystemException(SystemErrorCode.SYSTEM_ERROR);
                }
            }
        }
    }

    /**
     * 将bit数组转成文件
     * @param contents
     * @param filePath
     */
    public static void byteToFile(byte[] contents, String filePath) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream output = null;
        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(contents);
            bis = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            // 获取文件的父路径字符串
            File path = file.getParentFile();
            if (!path.exists()) {
                log.info("文件夹不存在，创建。path={}", path);
                boolean isCreated = path.mkdirs();
                if (!isCreated) {
                    log.error("创建文件夹失败，path={}", path);
                }
            }
            fos = new FileOutputStream(file);
            // 实例化OutputString 对象
            output = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                output.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            output.flush();
        } catch (Exception e) {
            log.error("输出文件流时抛异常，filePath={}", filePath, ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                bis.close();
                fos.close();
                output.close();
            } catch (IOException e0) {
                log.error("文件处理失败，filePath={}", filePath,  ExceptionUtils.getStackTrace(e0));
            }
        }
    }

}
