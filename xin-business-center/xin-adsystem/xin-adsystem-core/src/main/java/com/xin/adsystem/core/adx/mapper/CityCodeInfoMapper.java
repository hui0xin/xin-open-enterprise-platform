//package com.xin.adsystem.core.adx.mapper;
//
//import com.xin.innovate.sunflower.common.DO.CityCodeInfo;
//import org.apache.ibatis.annotations.*;
//import org.apache.ibatis.type.JdbcType;
//
//
//@Mapper
//public interface CityCodeInfoMapper {
//    /**
//     * 根据主键删除数据
//     *
//     * @param id
//     */
//    @Delete({
//            "delete from city_code_info",
//            "where id = #{id,jdbcType=BIGINT}"
//    })
//    int deleteByPrimaryKey(Long id);
//
//    /**
//     * 插入数据库记录
//     *
//     * @param record
//     */
//    @Insert({
//            "insert into city_code_info (id, province_code, ",
//            "province_name, city_code, ",
//            "city_name, county_name, ",
//            "county_code, create_time, ",
//            "update_time)",
//            "values (#{id,jdbcType=BIGINT}, #{provinceCode,jdbcType=INTEGER}, ",
//            "#{provinceName,jdbcType=VARCHAR}, #{cityCode,jdbcType=INTEGER}, ",
//            "#{cityName,jdbcType=VARCHAR}, #{countyName,jdbcType=VARCHAR}, ",
//            "#{countyCode,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
//            "#{updateTime,jdbcType=TIMESTAMP})"
//    })
//    int insert(CityCodeInfo record);
//
//    /**
//     * 插入数据库记录
//     *
//     * @param record
//     */
//    @InsertProvider(type = CityCodeInfoSqlProvider.class, method = "insertSelective")
//    int insertSelective(CityCodeInfo record);
//
//    /**
//     * 根据主键id查询
//     *
//     * @param id
//     */
//    @Select({
//            "select",
//            "id, province_code, province_name, city_code, city_name, county_name, county_code, ",
//            "create_time, update_time",
//            "from city_code_info",
//            "where id = #{id,jdbcType=BIGINT}"
//    })
//    @Results({
//            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "province_code", property = "provinceCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "province_name", property = "provinceName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "city_code", property = "cityCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "city_name", property = "cityName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "county_name", property = "countyName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "county_code", property = "countyCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
//            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
//    })
//    CityCodeInfo selectByPrimaryKey(Long id);
//
//    /**
//     * 根据cityCode查询
//     *
//     * @param cityCode
//     */
//    @Select({
//            "select",
//            "id, province_code, province_name, city_code, city_name, county_name, county_code, ",
//            "create_time, update_time",
//            "from city_code_info",
//            "where city_code = #{cityCode,jdbcType=INTEGER} limit 1"
//    })
//    @Results({
//            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "province_code", property = "provinceCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "province_name", property = "provinceName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "city_code", property = "cityCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "city_name", property = "cityName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "county_name", property = "countyName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "county_code", property = "countyCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
//            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
//    })
//    CityCodeInfo selectByCityCode(Integer cityCode);
//
//    /**
//     * 根据countyCode查询
//     *
//     * @param countyCode
//     */
//    @Select({
//            "select * from city_code_info",
//            "where county_code = #{countyCode,jdbcType=INTEGER} limit 1"
//    })
//    @Results({
//            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "province_code", property = "provinceCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "province_name", property = "provinceName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "city_code", property = "cityCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "city_name", property = "cityName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "county_name", property = "countyName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "county_code", property = "countyCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
//            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
//    })
//    CityCodeInfo selectByConuntyCode(Integer countyCode);
//
//    /**
//     * 根据主键cityName查询
//     *
//     * @param cityName
//     */
//    @Select({
//            "select * from city_code_info",
//            "where city_name = #{cityName,jdbcType=VARCHAR} limit 1"
//    })
//    @Results({
//            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "province_code", property = "provinceCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "province_name", property = "provinceName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "city_code", property = "cityCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "city_name", property = "cityName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "county_name", property = "countyName", jdbcType = JdbcType.VARCHAR),
//            @Result(column = "county_code", property = "countyCode", jdbcType = JdbcType.INTEGER),
//            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
//            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
//    })
//    CityCodeInfo selectByCityName(String cityName);
//
//    /**
//     * 修改数据
//     *
//     * @param record
//     */
//    @UpdateProvider(type = CityCodeInfoSqlProvider.class, method = "updateByPrimaryKeySelective")
//    int updateByPrimaryKeySelective(CityCodeInfo record);
//
//    /**
//     * 修改数据
//     *
//     * @param record
//     */
//    @Update({
//            "update city_code_info",
//            "set province_code = #{provinceCode,jdbcType=INTEGER},",
//            "province_name = #{provinceName,jdbcType=VARCHAR},",
//            "city_code = #{cityCode,jdbcType=INTEGER},",
//            "city_name = #{cityName,jdbcType=VARCHAR},",
//            "county_name = #{countyName,jdbcType=VARCHAR},",
//            "county_code = #{countyCode,jdbcType=INTEGER},",
//            "create_time = #{createTime,jdbcType=TIMESTAMP},",
//            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
//            "where id = #{id,jdbcType=BIGINT}"
//    })
//    int updateByPrimaryKey(CityCodeInfo record);
//}