package com.${packageName}.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 订单状态 demo
 * @author: 系统
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    INIT_SETTLEMENT_STATUS(0, "未结算"),
    UN_LIQUIDATED_STATUS(1, "待结算"),
    NO_MONEY_PAID_STATUS(2,"待打款"),
    SETTLEMENT_STATUS(3,"已结算");

    private final Integer code;
    private final String name;

    public static OrderStatusEnum valueOf(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderStatusEnum orderStatusEnum : values()) {
            if (orderStatusEnum.getCode() == code) {
                return orderStatusEnum;
            }
        }
        return null;
    }
}
