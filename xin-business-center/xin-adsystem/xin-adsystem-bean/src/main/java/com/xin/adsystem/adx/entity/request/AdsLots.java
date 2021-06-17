package com.xin.adsystem.adx.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "广告展示位置信息列表， 一个bid_request中 至少有一个有效imp object",
        description = "广告展示位置信息列表， 一个bid_request中 至少有一个有效imp object")
public class AdsLots implements Serializable{

    @ApiModelProperty(value = "adunit ID", required = true)
    private String id;

    @ApiModelProperty(value = "广告banner对象", required = true)
    private List<BannerInfo> bannerInfoList;

//    @ApiModelProperty(value = "PMP交易对象")
//    private PmpInfo pmpInfo;

//    @ApiModelProperty(value = "可接受的广告类型列表", required = true)
//    private List<AdType> adTypeList;

    @ApiModelProperty(value = "千次展示出价底价，单 位为分")
    private Long bidFloor;

}


