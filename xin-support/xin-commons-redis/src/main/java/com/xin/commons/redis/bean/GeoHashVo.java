package com.xin.commons.redis.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.io.Serializable;

/**
 * GeoHashVo 返回的geo实体
 * @author: xin
 */
@Data
@ApiModel(value="公共返回实体",description="公共返回实体")
public class GeoHashVo implements Serializable {

    @ApiModelProperty(value = "member名称  如:天津")
    private String member;
    
    @ApiModelProperty(value = "对应的经纬度坐标 类")
    private Point point;
    
    @ApiModelProperty(value = "距离中心点的距离")
    private Distance distance;
}