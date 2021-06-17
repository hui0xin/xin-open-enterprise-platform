package com.xin.adsystem.adx.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "广告展示设备信息", description = "广告展示设备信息")
public class DeviceInfo implements Serializable{

    @ApiModelProperty(value = "0即允许追踪，1为禁 止追踪", required = true)
    private Integer dnt;

    @ApiModelProperty(value = "浏览器的user agent string", required = true)
    private String ua;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "设备地理位置信息", required = true)
    private GeoInfo geoInfo;

    @ApiModelProperty(value = "设备标示号，Android 为IMEI，iOS为IDFA")
    private String deviceId;

    @ApiModelProperty(value = "MD5加密后的设备标 示号")
    private String deviceIdMd5;

    @ApiModelProperty(value = "设备运营商名称")
    private String carrier;

    @ApiModelProperty(value = "浏览器语言")
    private String language;

    @ApiModelProperty(value = "设备厂商(Apple)")
    private String make;

    @ApiModelProperty(value = "手机型号(iPhone 5)")
    private String model;

    @ApiModelProperty(value = "操作系统(iOS)")
    private String os;

    @ApiModelProperty(value = "操作系统版本(7 1)")
    private String osv;

    @ApiModelProperty(value = "1为支持JavaScript，0 为不支持")
    private Integer js;

    @ApiModelProperty(value = "网络连接信息枚举")
    private Integer connectionType;

    @ApiModelProperty(value = "设备类型枚举")
    private Integer deviceType;

}


