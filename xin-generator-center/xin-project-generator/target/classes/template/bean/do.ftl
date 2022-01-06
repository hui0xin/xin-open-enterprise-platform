package com.${packageName}.bean.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: Do 和数据库的字段一一对应
 *               可以通过  xin-mybatis-generator 工程生成
 * @author: 系统
 */
@Data
@ApiModel(value = "DemoDo", description = "说明")
public class DemoDo implements Serializable {

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

    @ApiModelProperty(name = "version", value = "版本号")
    private Integer version;

}