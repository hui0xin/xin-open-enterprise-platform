
# mybatis 相关操作
## 批量查询
```
@Select({
        "<script>",
        "select * from gs_gd_station_info where city_code in " ,
        "<foreach collection='cityCodes' item='citycode' open='(' separator=',' close=')'>" ,
        "#{citycode} ",
        "</foreach>",
        "</script>"
})
(@Param("cityCodes") List<Integer> cityCodes)
```
##批量修改
```
@Update({
    "<script>" +
        "<foreach collection=\"list\" item=\"item\" separator=\";\">" +
        " UPDATE" +
        " ylt_listen_rank" +
        " SET rank=#{item.rank} WHERE sn = #{item.sn} " +
        "</foreach>" +
    "</script>"
})
int updateAllRankBySn(@Param("list") List<YltListenRank> list);
```
## 查询返回map
```
@Select({
     "select * from xxxx"
})
@ResultType(Map.class)
List<Map<String, Object>> selectLabelByArticle(@Param("articleIdList") String articleIdList);
```
## 查询返回Integer
```
@Select({"select count(id) from reply and type =0"})
Integer getReplyCount();

"where geo_hash like CONCAT(#{geoHash,jdbcType=BIGINT},'%')"
"<if test='content != null'> and content like CONCAT('%',#{content,jdbcType=VARCHAR},'%')</if> "

"and create_time between #{startTime} and #{endTime} order by create_time asc"
@Param("startTime") Date startTime,@Param("endTime") Date endTime

## 返回保存的Id 注意，返回的id会保存到 record 中
@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
int insert(EmPoiInfo record);
```

## 使用 @SelectProvider 来查询
```
@SelectProvider(type = AdvPositionSqlProvider.class, method = "selectListByPrimary")
List<AdvPosition> selectListByPrimary(@Param("advPositionBO") AdvPositionBO advPositionBO);
public String selectListByPrimary(@Param("advSourceBO") AdvSourceBO advSourceBO) {
    SQL sql = new SQL();
    sql.SELECT(" * ");
    sql.FROM(" adv_source ");
    if(advSourceBO.getId()!=null){
        sql.WHERE(" id = " + advSourceBO.getId());
    }
    if(advSourceBO.getAdvName()!=null){
        sql.WHERE(" adv_name like '%" + advSourceBO.getAdvName() + "%'");
    }
    sql.WHERE(" state = 0");
    if(advSourceBO.getStartTime()!=null && advSourceBO.getEndTime()!=null){
        sql.WHERE("start_time  between '" + sdf.format(advSourceBO.getStartTime())+"' and '"+ sdf.format(advSourceBO.getEndTime())+"'");
    }
    sql.ORDER_BY("start_time desc");
    return sql.toString();
}
```
## 分页
```
Integer pageIndex 第几页
Integer pageSize 每页显示多少条
Page page = PageHelper.startPage(pageIndex, pageSize);
List<BusinessLine> modelList = businessLineMapper.selectListByPrimary(blName,state);
PageInfo<BusinessLine> pageInfo = new PageInfo(page.getResult());
```

# java相关

## 1 使用DateUtil 基本的功能都有
import com.xin.commons.support.utils.DateUtil;

## 2 使用 OkHttpClientUtil 
import com.xin.commons.support.utils.OkHttpClientUtil;

```
String geoHash = GeoHashUtil.getGeoHash(lng,lat);

//距离（默认）升序排列
poiList.sort(Comparator.comparing(EnjoyMapPoiListVo::getDistance));
//多个字段排序 reversed() 降序
gasShareList.sort(Comparator.comparing(GsShare::getShareUserType).reversed().thenComparing(Comparator.comparing(GsShare::getCreateTime).reversed()));
```


