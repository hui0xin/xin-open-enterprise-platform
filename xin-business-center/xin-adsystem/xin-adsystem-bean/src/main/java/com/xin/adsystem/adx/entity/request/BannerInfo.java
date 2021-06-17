package com.xin.adsystem.adx.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "Banner 对象字段信息及含义", description = "Banner 对象字段信息及含义")
public class BannerInfo implements Serializable{

    @ApiModelProperty(value = "图片素材宽度", required = true)
    private Integer width;

    @ApiModelProperty(value = "图片素材高度", required = true)
    private Integer height;

    @ApiModelProperty(value = "广告位置列表", required = true)
    private List<Long> posList;

    @ApiModelProperty(value = "广告所展示的具体坐标")
    private String sequence;


}


