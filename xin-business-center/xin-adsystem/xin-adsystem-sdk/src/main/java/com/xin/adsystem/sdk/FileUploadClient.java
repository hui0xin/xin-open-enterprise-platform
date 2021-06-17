//package com.xin.file.upload.sdk;
//
//import com.xin.commons.support.response.ResponseResult;
//import com.xin.file.upload.common.config.MultipartSupportConfig;
//import com.xin.adsystem.adx.entity.FileBase64Entity;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//
//@FeignClient(value = "oper-file-upload-server",path = "/file", configuration = MultipartSupportConfig.class)
//public interface FileUploadClient {
//
//    @ApiOperation(value = "imageUploads", notes = "图片批量上传，解析出图片长，宽")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "MultipartFile", paramType = "query"),
//            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
//    @PostMapping(value = "/common/file-upload/v1/no/image_uploads",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResponseResult imageUploads(@RequestPart(value = "files") MultipartFile[] files, @RequestParam(value = "filePath") String filePath);
//
//    @ApiOperation(value = "imageUpload", notes = "单个图片上传，解析出图片长，宽")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "file", value = "图片文件", required = true, dataType = "MultipartFile", paramType = "query"),
//            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
//    @PostMapping(value = "/common/file-upload/v1/no/image_upload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResponseResult imageUpload(@RequestPart("mFile") MultipartFile mFile, @RequestParam(value = "filePath") String filePath);
//
//    @ApiOperation(value = "multUpload", notes = "单个图片文件上传，解析出图片长，宽,对文件进行md5加密")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "query"),
//            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
//    @PostMapping(value = "/common/file-upload/v1/no/multUpload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResponseResult multUpload(@RequestPart("file") MultipartFile file, @RequestParam("filePath") String filePath);
//
//    @ApiOperation(value = "fileupload", notes = "单个文件上传,不进行MD5加密")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile", paramType = "query"),
//            @ApiImplicitParam(name = "filePath", value = "文件在cos上存放地址", required = true, dataType = "String", paramType = "query") })
//    @PostMapping(value = "/common/file-upload/v1/no/fileupload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResponseResult fileupload(@RequestPart("file") MultipartFile file, @RequestParam("filePath") String filePath);
//
//    @ApiOperation(value = "文件上传", notes = "以文件base64字符串上传")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "fileBase64Entity", value = "图片base64字符串", required = true, dataType = "FileBase64Entity", paramType = "query") })
//    @PostMapping(value = "/common/file-upload/v1/no/base64Upload")
//    ResponseResult base64Upload(@RequestBody FileBase64Entity fileBase64Entity) ;
//
//    @ApiOperation(value = "文件下载", notes = "文件下载")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "downFile", value = "保存到本地的地址", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "filePathName", value = "文件在cos上的地址", required = true, dataType = "String", paramType = "query") })
//    @PostMapping(value = "/common/file-upload/v1/no/downFile")
//    ResponseResult downFile(@RequestParam("downFile") String downFile, @RequestParam("filePathName") String filePathName) ;
//
//    @ApiOperation(value = "删除文件", notes = "删除文件")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "filePathName", value = "文件在阿里云oss路径,包括名称", required = true, dataType = "String", paramType = "query") })
//    @PostMapping(value = "/common/file-upload/v1/no/deleteFile")
//    ResponseResult deleteFile(@RequestParam("filePathName") String filePathName) ;
//
//}
