//package com.xin.adsystem.dsp.entity;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import java.io.Serializable;
//import java.util.Date;
//
//
///**
// * dsp系统架构：
// *
// */
//
//@Data
//@ApiModel(value = "广告项目维护", description = "广告项目维护")
//public class DspProject implements Serializable {
//
//    @ApiModelProperty(value = "项目名称：例如：小米新品发布", required = true)
//    private String projectName;
//
//    @ApiModelProperty(value = "推广目的", required = true)
//    private String promotionPurpose;
//
//    @ApiModelProperty(value = "投放开始时间", required = true)
//    private Date startDate;
//
//    @ApiModelProperty(value = "投放结束时间", required = true)
//    private Date endDate;
//
//    @ApiModelProperty(value = "投放地域")
//    private String promotionRegion;
//
//    @ApiModelProperty(value = "目标人群素描")
//    private Person targetPopulation;
//
//}
//
//class Person{
//    @ApiModelProperty(value = "性别")
//    private String sex;
//
//    @ApiModelProperty(value = "年龄区间")
//    private String age;
//
//    @ApiModelProperty(value = "年龄区间")
//    private String 结算方式;
//
//    //兴趣爱好，
//}
//
//
///**
// * 目标人群分析
// * 1，基本人口属性分析：
// *  性别，年龄
// * 2，兴趣爱好分析：
// *  关注：影视，动漫，搞笑，休闲，军事，财经。。。。
// * 3，消费倾向：
// * 电脑整机，手机通信，股票，旅游，
// *
// */
//
