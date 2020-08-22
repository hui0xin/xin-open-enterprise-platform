package com.xin.commons.redis.service;

import com.xin.commons.redis.bean.GeoHashVo;
import org.springframework.data.geo.Point;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    // ============================common=============================
    Boolean expire(String key, Long time);

    Long getExpire(String key);

    Boolean hasKey(String key);

    Set<String> keys(final String pattern);

    Boolean del(String key);

    Long del(List<String> keys);

    Long delPattern(final String pattern);

    // ============================String=============================

    Object get(String key);

    Boolean set(String key, String value);

    Boolean set(String key, String value, Long time);

    Boolean set(String key, String value, Long time, TimeUnit timeUnit);

    Long incr(String key, Long delta);

    Long decr(String key, Long delta);

    // ================================hash=================================

    Object hget(String key, String field);

    Map<Object, Object> hmget(String key);

    Boolean hmset(String key, Map<String, Object> map);

    Boolean hmset(String key, Map<String, Object> map, Long time);

    Boolean hset(String key, String field, Object value);

    Boolean hset(String key, String field, Object value, Long time);

    Long hdel(String key, Object... field);

    Boolean hHasKey(String key, String field);

    Double hincr(String key, String field, Double by);

    Double hdecr(String key, String field, Double by);

    // ============================set=============================

    Set<Object> sGet(String key);

    Boolean sHasKey(String key, Object value);

    Long sSet(String key, Object... values);

    Long sSetAndTime(String key, Long time, Object... values);

    Long sGetSetSize(String key);

    Long setRemove(String key, Object... values);

    Set<Object> members(String key);

    Boolean multiAdd(String key, List<Object> c, Long expireTime);

    // ============================zset=============================

    Boolean zSetAdd(String key ,Object value, Double score);

    Set<Object> zSetRange(String key ,Long start, Long end);

    Set<Object> zSetByScore(String key ,Double min, Double max);

    Set<Object> zSetByScore(String key, Double min, Double max, Long offset, Long count);

    Set zSetRangeByScoreWithScores(String key, Double min, Double max);

    Set zSetRangeByScoreWithScores(String key, Double min, Double max, Long offset, Long count);

    Set zSetRangeWithScores(String key ,Long start, Long end);

    Set<Object> zSetReverseRange(String key ,Long start, Long end);

    Set<Object> zSetReverseRangeByScore(String key ,Double min, Double max);

    Set<Object> zSetReverseRangeByScore(String key ,Double min, Double max,Long offset,Long count);

    Long zSetRank(String key ,Object value);

    Long zSetCount(String key ,Double min, Double max);

    Long zSetRemove(String key,Object... values);

    Long zSetRemoveRange(String key,Long start,Long end);

    Long zSetRemoveRangeByScore(String key,Double min,Double max);

    Long zSetSize(String key);

    Long zSetReverseRank(String key,Object values);

    Double zSetScore(String key,Object values);

    // ===============================list=================================

    List<Object> lGet(String key, Long start, Long end);

    Long lGetListSize(String key);

    Object lGetIndex(String key, Long index);

    Boolean lSet(String key, Object value);

    Boolean lSet(String key, Object value, Long time);

    Boolean lSet(String key, List<Object> value);

    Boolean lSet(String key, List<Object> value, Long time);

    Boolean lUpdateIndex(String key, Long index, Object value);

    Long lRemove(String key, Long count, Object value);

    // =========================== GeoHash =================================

    Long addAllGeo(String key, Map<Object, Point> points, Object value);

    Long addGeo(String key,String name,Point point);

    Long addGeo(String key,String name,Double lng, Double lat);

    Double getDistance(String key,String fromMember, String toMember);

    List<Point> getLocations(String key,String member);

    List<String> getPositionHash(String key,String member);

    List<GeoHashVo> getRangeDistance(String key, Double lng, Double lat, Double range, int count);

    List<GeoHashVo> getRangeDistanceByMember(String key,String member, Double range, int count);

    Long deleteGeo(String key,String member);
}