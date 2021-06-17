package com.xin.adsystem.adx.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "广告请求参数定义", description = "广告请求参数定义")
public class BidRequest implements Serializable{

    @ApiModelProperty(value = "每次请求的唯一标识", required = true)
    private String requestId;

    @ApiModelProperty(value = "请求API的版本号", required = true)
    private String apiVersion;

    @ApiModelProperty(value = "广告展示位置信息列表", required = true)
    private List<AdsLots> adsLotsList;

    @ApiModelProperty(value = "广告展示设备信息", required = true)
    private DeviceInfo deviceInfo;

    @ApiModelProperty(value = "本地应用信息", required = true)
    private AppInfo appInfo;

    @ApiModelProperty(value = "用户信息", required = true)
    private UserInfo userInfo;


}


