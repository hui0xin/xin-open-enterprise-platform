package com.xin.commons.multi.shardingjdbc.mapper;

import com.xin.commons.multi.shardingjdbc.bean.PerpetualCoin;
import org.apache.ibatis.annotations.*;
import java.util.Date;
import java.util.List;

@Mapper
public interface PerpetualCoinDao {

    @Results(id = "perpetualCoinModel", value = {
            @Result(column = "id", property = "id", javaType = Long.class),
            @Result(column = "symbol", property = "symbol", javaType = Integer.class),
            @Result(column = "symbol_sum", property = "symbolSum", javaType = Long.class),
            @Result(column = "symbol_name", property = "symbolName", javaType = String.class),
            @Result(column = "index_market_from", property = "indexMarketFrom", javaType = Integer.class),
            @Result(column = "symbol_mark", property = "symbolMark", javaType = String.class),
            @Result(column = "market_from", property = "marketFrom", javaType = String.class),
            @Result(column = "futures_market_from", property = "futuresMarketFrom", javaType = String.class),
            @Result(column = "pricePoint", property = "pricePoint", javaType = Integer.class),
            @Result(column = "seqencing", property = "seqencing", javaType = Integer.class),
            @Result(column = "config_type", property = "configType", javaType = Integer.class),
            @Result(column = "invalid_switch", property = "invalidSwitch", javaType = Byte.class),
            @Result(column = "created_date", property = "createdDate", javaType = Date.class),
            @Result(column = "modify_date", property = "modifyDate", javaType = Date.class)
    })

    @Select("select * from perpetual_coin")
    List<PerpetualCoin> getPerpetualCoinList();

    @Insert("insert into perpetual_coin (" +
            "id,symbol,symbol_sum,symbol_name,index_market_from,symbol_mark, " +
            "market_from,futures_market_from,pricePoint,seqencing, " +
            "config_type,invalid_switch,created_date,modify_date " +
            ") values(" +
            "#{perpetualCoin.id},#{perpetualCoin.symbol},#{perpetualCoin.symbolSum},#{perpetualCoin.symbolName}," +
            "#{perpetualCoin.indexMarketFrom},#{perpetualCoin.symbolMark}, #{perpetualCoin.marketFrom}," +
            "#{perpetualCoin.futuresMarketFrom},#{perpetualCoin.pricePoint},#{perpetualCoin.seqencing}," +
            "#{perpetualCoin.configType},#{perpetualCoin.invalidSwitch}," +
            "#{perpetualCoin.createdDate},#{perpetualCoin.modifyDate})")

    int insertPerpetualCoin(@Param("perpetualCoin") PerpetualCoin perpetualCoin);

    @Update("update perpetual_coin set " +
            "symbol = #{perpetualCoin.symbol}, " +
            "symbol_sum = #{perpetualCoin.symbolSum}, " +
            "symbol_name = #{perpetualCoin.symbolName}, " +
            "index_market_from = #{perpetualCoin.indexMarketFrom},  " +
            "symbol_mark = #{perpetualCoin.symbolMark},  " +
            "market_from = #{perpetualCoin.marketFrom}, " +
            "futures_market_from = #{perpetualCoin.futuresMarketFrom},  " +
            "pricePoint = #{perpetualCoin.pricePoint},  " +
            "seqencing = #{perpetualCoin.seqencing},  " +
            "config_type = #{perpetualCoin.configType},  " +
            "invalid_switch = #{perpetualCoin.invalidSwitch},  " +
            "created_date = #{perpetualCoin.createdDate},  " +
            "modify_date = #{perpetualCoin.modifyDate}  " +
            "where id=#{perpetualCoin.id}")
    int updatePerpetualCoin(@Param("perpetualCoin") PerpetualCoin perpetualCoin);

    @Delete("DELETE FROM perpetual_coin where id = #{id}")
    int deletePerpetualCoin(@Param("id") Long id);

    @Select("SELECT * FROM perpetual_coin where symbol = #{id}")
    @ResultMap("perpetualCoinModel")
    PerpetualCoin getPerpetualCoinById(@Param("id") Long id);


}
