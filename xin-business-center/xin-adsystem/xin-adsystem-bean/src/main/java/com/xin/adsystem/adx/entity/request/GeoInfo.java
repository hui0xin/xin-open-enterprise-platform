package com.xin.adsystem.adx.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "设备地理位置信息", description = "设备地理位置信息")
public class GeoInfo implements Serializable{

    @ApiModelProperty(value = "经度")
    private Float lat;

    @ApiModelProperty(value = "纬度")
    private Float lon;

    @ApiModelProperty(value = "国家", required = true)
    private String country;

    @ApiModelProperty(value = "地区")
    private String region;

    @ApiModelProperty(value = "定位方式")
    private String type;

}


