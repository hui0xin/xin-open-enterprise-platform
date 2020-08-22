package com.xin.commons.support.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 和数据库交互的实体
 * 阿里规范：
 * DO（ Data Object）：与数据库表结构一一对应。
 * DTO（ Data Transfer Object）：数据传输对象，Service向外传输的对象。
 * BO（ Business Object）：前端传回到后端的参数（requestbody中的参数）。
 * VO（ View Object）：显示层对象，通常是Web向模板渲染引擎层传输的对象。
 * @author: xin
 */
@Data
@ApiModel(value="公共实体",description="公共实体")
public class BaseDO implements Serializable {
    /**
     * required=true 表示是否是必填参数
     */
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "版本号", hidden = true)
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}