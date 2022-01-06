package com.xin.pay.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderProduct {
    /**
     * 
     * 表 : tb_ord_produt
     * 对应字段 : tid
     */
    private String tid;

    /**
     * 流水号
     * 表 : tb_ord_produt
     * 对应字段 : serial_no
     */
    private String serialNo;

    /**
     * 商品名
     * 表 : tb_ord_produt
     * 对应字段 : prd_name
     */
    private String prdName;

    /**
     * 商品价
     * 表 : tb_ord_produt
     * 对应字段 : prd_price
     */
    private BigDecimal prdPrice;

    /**
     * 数量
     * 表 : tb_ord_produt
     * 对应字段 : prd_qty
     */
    private Integer prdQty;

    /**
     * 
     * 表 : tb_ord_produt
     * 对应字段 : create_date
     */
    private Date createDate;

    /**
     * 
     * 表 : tb_ord_produt
     * 对应字段 : update_by
     */
    private String updateBy;

    /**
     * 
     * 表 : tb_ord_produt
     * 对应字段 : update_date
     */
    private Date updateDate;

    /**
     * 
     * 表 : tb_ord_produt
     * 对应字段 : remark
     */
    private String remark;

    /**
     * 
     * 表 : tb_ord_produt
     * 对应字段 : version
     */
    private Integer version;

    /**
     * 
     * 表 : tb_ord_produt
     * 对应字段 : state
     */
    private Integer state;


}