//package com.xin.adsystem.core.adx.mapper;
//
//import com.xin.innovate.sunflower.common.DO.CityCodeInfo;
//import org.apache.ibatis.jdbc.SQL;
//
//public class CityCodeInfoSqlProvider {
//
//    public String insertSelective(CityCodeInfo record) {
//        SQL sql = new SQL();
//        sql.INSERT_INTO("city_code_info");
//
//        if (record.getId() != null) {
//            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
//        }
//
//        if (record.getProvinceCode() != null) {
//            sql.VALUES("province_code", "#{provinceCode,jdbcType=INTEGER}");
//        }
//
//        if (record.getProvinceName() != null) {
//            sql.VALUES("province_name", "#{provinceName,jdbcType=VARCHAR}");
//        }
//
//        if (record.getCityCode() != null) {
//            sql.VALUES("city_code", "#{cityCode,jdbcType=INTEGER}");
//        }
//
//        if (record.getCityName() != null) {
//            sql.VALUES("city_name", "#{cityName,jdbcType=VARCHAR}");
//        }
//
//        if (record.getCountyName() != null) {
//            sql.VALUES("county_name", "#{countyName,jdbcType=VARCHAR}");
//        }
//
//        if (record.getCountyCode() != null) {
//            sql.VALUES("county_code", "#{countyCode,jdbcType=INTEGER}");
//        }
//
//        if (record.getCreateTime() != null) {
//            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
//        }
//
//        if (record.getUpdateTime() != null) {
//            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
//        }
//
//        return sql.toString();
//    }
//
//    public String updateByPrimaryKeySelective(CityCodeInfo record) {
//        SQL sql = new SQL();
//        sql.UPDATE("city_code_info");
//
//        if (record.getProvinceCode() != null) {
//            sql.SET("province_code = #{provinceCode,jdbcType=INTEGER}");
//        }
//
//        if (record.getProvinceName() != null) {
//            sql.SET("province_name = #{provinceName,jdbcType=VARCHAR}");
//        }
//
//        if (record.getCityCode() != null) {
//            sql.SET("city_code = #{cityCode,jdbcType=INTEGER}");
//        }
//
//        if (record.getCityName() != null) {
//            sql.SET("city_name = #{cityName,jdbcType=VARCHAR}");
//        }
//
//        if (record.getCountyName() != null) {
//            sql.SET("county_name = #{countyName,jdbcType=VARCHAR}");
//        }
//
//        if (record.getCountyCode() != null) {
//            sql.SET("county_code = #{countyCode,jdbcType=INTEGER}");
//        }
//
//        if (record.getCreateTime() != null) {
//            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
//        }
//
//        if (record.getUpdateTime() != null) {
//            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
//        }
//
//        sql.WHERE("id = #{id,jdbcType=BIGINT}");
//
//        return sql.toString();
//    }
//}