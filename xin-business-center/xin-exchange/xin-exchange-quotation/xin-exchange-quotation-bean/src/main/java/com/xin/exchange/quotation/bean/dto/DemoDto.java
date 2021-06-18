package com.xin.exchange.quotation.bean.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: dto 用于中途处理的 中间状态，比如：controller <————> service 的中间数据类
 *               注意：这个不需要swagger
 * @author: 系统
 */
@Data
public class DemoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 1普通用户，2虚拟普通用户，3虚拟商家用户
     */
    private String userType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}