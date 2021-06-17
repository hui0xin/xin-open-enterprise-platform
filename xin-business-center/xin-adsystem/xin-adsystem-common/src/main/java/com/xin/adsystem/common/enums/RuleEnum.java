package com.xin.adsystem.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by xin
 * 状态
 */
@Getter
@AllArgsConstructor
public enum RuleEnum {

    //每天可领取最大次数
    DAILYCOLLECTIONSUM("listen-news-reward-limit", "dailycollectionsum"),
    //每次领取的最大金额
    MAXIMUM_AMOUNT_FOR_EACH_CLAIM("listen-news-reward-limit", "maximum_amount_for_each_claim"),

    ;

    private final String type;
    private final String key;

}
