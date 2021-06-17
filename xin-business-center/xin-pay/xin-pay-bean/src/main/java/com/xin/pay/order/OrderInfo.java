package com.xin.pay.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    /**
     * 
     * 表 : tb_ord_inf
     * 对应字段 : tid
     */
    private String tid;

    /**
     * 流水号
     * 表 : tb_ord_inf
     * 对应字段 : serial_no
     */
    private String serialNo;

    /**
     * 订单状态
     * 表 : tb_ord_inf
     * 对应字段 : status
     */
    private String status;

    /**
     * 支付类型
     * 表 : tb_ord_inf
     * 对应字段 : pay_type
     */
    private String payType;

    /**
     * 支付状态
     * 表 : tb_ord_inf
     * 对应字段 : pay_status
     */
    private String payStatus;

    /**
     * 账户
     * 表 : tb_ord_inf
     * 对应字段 : shipping_account
     */
    private String shippingAccount;

    /**
     * 收货人
     * 表 : tb_ord_inf
     * 对应字段 : shipping_name
     */
    private String shippingName;

    /**
     * 收货地址
     * 表 : tb_ord_inf
     * 对应字段 : shipping_address
     */
    private String shippingAddress;

    /**
     * 收货电话
     * 表 : tb_ord_inf
     * 对应字段 : shipping_phone
     */
    private String shippingPhone;

    /**
     * 发货方式
     * 表 : tb_ord_inf
     * 对应字段 : post_way
     */
    private String postWay;

    /**
     * 配送费
     * 表 : tb_ord_inf
     * 对应字段 : post_fee
     */
    private BigDecimal postFee;

    /**
     * 订单总价
     * 表 : tb_ord_inf
     * 对应字段 : price
     */
    private BigDecimal price;

    /**
     * 
     * 表 : tb_ord_inf
     * 对应字段 : create_by
     */
    private String createBy;

    /**
     * 
     * 表 : tb_ord_inf
     * 对应字段 : create_date
     */
    private Date createDate;

    /**
     * 
     * 表 : tb_ord_inf
     * 对应字段 : update_by
     */
    private String updateBy;

    /**
     * 
     * 表 : tb_ord_inf
     * 对应字段 : update_date
     */
    private Date updateDate;

    /**
     * 
     * 表 : tb_ord_inf
     * 对应字段 : remark
     */
    private String remark;

    /**
     * 
     * 表 : tb_ord_inf
     * 对应字段 : version
     */
    private Integer version;

    /**
     * 
     * 表 : tb_ord_inf
     * 对应字段 : state
     */
    private Integer state;


}