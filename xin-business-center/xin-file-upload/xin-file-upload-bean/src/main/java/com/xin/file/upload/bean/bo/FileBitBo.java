package com.xin.file.upload.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "文件上传实体", description = "bit数组 封装成实体，便于传输")
public class FileBitBo implements Serializable {

    @ApiModelProperty(value = "文件序列化后的base64字节数组", required = true)
    private byte[] byteFile;

    @ApiModelProperty(value = "文件后缀名，比如jpg，png，mp4，xlsx。。。", required = true)
    private String fileSuffix;

    @ApiModelProperty(value = "文件上传位置: 比如：/activity/newYearGreetingCards/image/", required = true)
    private String filePath;
}
