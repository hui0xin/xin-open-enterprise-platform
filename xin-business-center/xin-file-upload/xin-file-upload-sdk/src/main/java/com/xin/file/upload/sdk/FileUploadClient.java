package com.xin.file.upload.sdk;

import com.xin.commons.support.response.ResponseResult;
import com.xin.file.upload.bean.bo.Base64Bo;
import com.xin.file.upload.bean.bo.FileBitBo;
import com.xin.file.upload.common.config.MultipartSupportConfig;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * name：指定FeignClient的名称，如果项目使用了Ribbon，name属性会作为微服务的名称，用于服务发现
 * url: url一般用于调试，可以手动指定@FeignClient调用的地址 比如 http://localhost:8080/
 * decode404:当发生http 404错误时，如果该字段为true，会调用decoder进行解码，否则抛出FeignException
 * configuration: Feign配置类，可以自定义Feign的Encoder、Decoder、LogLevel、Contract
 * fallback: 定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口
 * fallbackFactory: 工厂类，用于生成fallback类示例，通过这个属性我们可以实现每个接口通用的容错逻辑，减少重复的代码
 * path: 定义当前FeignClient的统一前缀
 */
@FeignClient(value = "file-upload-server",path = "/fileupload/common",configuration = MultipartSupportConfig.class)
public interface FileUploadClient {

    @ApiOperation(value = "imageUploads", notes = "图片批量上传，解析出图片长，宽")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/image_uploads",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult imageUploads(@RequestPart(value = "files") MultipartFile[] files, @RequestParam(value = "filePath") String filePath);

    @ApiOperation(value = "imageUpload", notes = "单个图片上传，解析出图片长，宽")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/image_upload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult imageUpload(@RequestPart("mFile") MultipartFile mFile, @RequestParam(value = "filePath") String filePath);

    @ApiOperation(value = "multUpload", notes = "单个图片文件上传，解析出图片长，宽,对文件进行md5加密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/multUpload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult multUpload(@RequestPart("file") MultipartFile file, @RequestParam("filePath") String filePath);

    @ApiOperation(value = "fileupload", notes = "单个文件上传,不进行MD5加密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/fileupload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult fileupload(@RequestPart("file") MultipartFile file, @RequestParam("filePath") String filePath);

    @ApiOperation(value = "文件上传", notes = "以文件base64字符串上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileBase64Entity", value = "图片base64字符串", required = true, dataType = "base64Bo", paramType = "query") })
    @PostMapping(value = "v1/base64Upload")
    ResponseResult base64Upload(@RequestBody Base64Bo base64Bo) ;

    @ApiOperation(value = "以文件的bit数组上传", notes = "以文件的bit数组上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileBitEntity", value = "文件bit数组实体", required = true, dataType = "fileBitBo", paramType = "query")
    })
    @PostMapping(value = "v1/bitFileUpload")
    ResponseResult bitFileUpload(@RequestBody FileBitBo fileBitBo);

    @ApiOperation(value = "文件下载", notes = "文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "downFile", value = "保存到本地的地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "filePathName", value = "文件在cos上的地址", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/downFile")
    ResponseResult downFile(@RequestParam("downFile") String downFile, @RequestParam("filePathName") String filePathName) ;

    @ApiOperation(value = "删除文件", notes = "删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePathName", value = "文件在阿里云oss路径,包括名称", required = true, dataType = "String", paramType = "query") })
    @PostMapping(value = "v1/deleteFile")
    ResponseResult deleteFile(@RequestParam("filePathName") String filePathName) ;

}
