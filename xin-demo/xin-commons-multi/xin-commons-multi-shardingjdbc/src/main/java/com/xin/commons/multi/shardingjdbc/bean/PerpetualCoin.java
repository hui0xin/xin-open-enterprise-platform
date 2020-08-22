package com.xin.commons.multi.shardingjdbc.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 币种合约
 * PerpetualCoinEntity
 */
@Data
public class PerpetualCoin {

    /**
     *
     */
    private Long id;
    /**
     * 该币种的全球数量
     */
    private Long symbolSum;
    /**
     * 币种数字值
     */
    private Integer symbol;
    /**
     * 币种英文缩写
     */
    private String symbolName;
    /**
     * 币指数的marketFrom
     */
    private Integer indexMarketFrom;
    /**
     * 排序字段
     */
    private Integer seqencing;
    /**
     * 区分配置的类型，可以是币，指数，。。。
     */
    private Integer configType;
    /**
     * 币种符号
     */
    private String symbolMark;

    /**
     * 币种对应的marketFrom列表
     */
    private String marketFrom;
    /**
     * 币指数的marketFrom（合约）
     */
    private String futuresMarketFrom;
    /**
     * 币种对应的marketFrom列表
     */
    private int[] marketFromArray;

    /**
     * 币种对应的marketFrom列表（合约）
     */
    private int[] futuresMarketFromArray;
    /**
     * 交易小数位
     */
    private Integer pricePoint;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * 无效运算开关 0默认关、1开
     */
    private Byte invalidSwitch;

}