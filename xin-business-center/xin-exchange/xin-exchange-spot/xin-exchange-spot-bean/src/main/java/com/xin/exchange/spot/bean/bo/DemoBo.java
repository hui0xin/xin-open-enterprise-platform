package com.xin.exchange.spot.bean.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: Bo 接受前端请求的参数
 * @author: 系统
 */
@Data
@ApiModel(value = "DemoBo", description = "说明")
public class DemoBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "主键")
    private Long id;

    @ApiModelProperty(name = "userType", value = "1普通用户，2虚拟普通用户，3虚拟商家用户")
    private String userType;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(name = "updateTime", value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}