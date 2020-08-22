package com.xin.commons.support.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 返回给前端的公共实体
 * @author: xin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="公共返回实体",description="公共返回实体")
public class BaseVO<T> implements Serializable {

    @ApiModelProperty(value = "返回的List数据")
    private List<T> dataList;

}