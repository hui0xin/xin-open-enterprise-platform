package com.xin.commons.redis.service.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import com.xin.commons.redis.bean.GeoHashVo;
import com.xin.commons.redis.errorcode.RedisErrorCode;
import com.xin.commons.redis.exception.RedisException;
import com.xin.commons.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

/**
 * redis操作的实现
 * @author: xin
 */
@Slf4j
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    // ========================================common========================================

    /**
     * 设置失效时间
     * @param key
     * @param time
     * @return true 设置成功
     */
    public Boolean expire(String key, Long time) {
        checkKey(key);
        checkExpireTime(time);
        try {
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("expire-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取key的过期时间
     * @param key
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        checkKey(key);
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("getExpire-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return true 存在， false不存在
     */
    public Boolean hasKey(String key) {
        checkKey(key);
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("hasKey-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 模糊查找，列出匹配的所有key
     */
    public Set<String> keys(final String pattern) {
        checkKey(pattern);
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            log.error("keys-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 删除指定key的数据
     * @return true 成功
     */
    public Boolean del(String key) {
        checkKey(key);
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("del-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 删除多个key对应的数据
     */
    public Long del(List<String> keys) {
        try {
            return redisTemplate.delete(keys);
        } catch (Exception e) {
            log.error("del-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 模糊删除key对应的数据（特别注意，小心误删别人的数据）
     * @param pattern
     */
    public Long delPattern(final String pattern) {
        checkKey(pattern);
        try {
            Set<String> keys = redisTemplate.keys(pattern);
            if (keys.size() > 0) {
                return redisTemplate.delete(keys);
            }else {
                return  0L;
            }
        } catch (Exception e) {
            log.error("delPattern-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    // =====================================String============================================

    /**
     * 获取数据
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        checkKey(key);
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("get-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 设置数据
     * @param key
     * @param value
     * @return true 成功
     */
    public Boolean set(String key, String value) {
        checkKey(key);
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("set-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 普通缓存放入并设置超时时间，单位是秒
     * @param key
     * @param value
     * @param time
     * @return true 成功
     */
    public Boolean set(String key, String value, Long time) {
        checkKey(key);
        checkExpireTime(time);
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("set-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 普通缓存放入 并设置时间 ，设置单位
     * @param key
     * @param value
     * @param time
     * @return true 成功
     */
    public Boolean set(String key, String value, Long time, TimeUnit timeUnit) {
        checkKey(key);
        checkExpireTime(time);
        try {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
            return true;
        } catch (Exception e) {
            log.error("set-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 递增操作
     * @param key
     * @param delta 要增加几(大于0)
     * @return
     */
    public Long incr(String key, Long delta) {
        checkKey(key);
        checkDelta(delta);
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("incr-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }

    }

    /**
     * 递减操作
     * @param key
     * @param delta delta 要减少几
     * @return
     */
    public Long decr(String key, Long delta) {
        checkKey(key);
        checkDelta(delta);
        try {
            return redisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            log.error("decr-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    // ==============================================hash===============================================

    /**
     * 返回哈希表 key 中给定域 field 的值
     * @param key  键 不能为null
     * @param field 指定的列  不能为null
     * @return 值
     */
    public Object hget(String key, String field) {
        checkKey(key);
        checkField(field);
        try {
            return redisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            log.error("hget-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        checkKey(key);
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("hmget-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。此命令会覆盖哈希表中已存在的域。
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功
     */
    public Boolean hmset(String key, Map<String, Object> map) {
        checkKey(key);
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("hmset-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * HashSet 并设置时间
     * @param key  键
     * @param map  对应多个键值
     * @param time true成功 false失败
     * @return
     */
    public Boolean hmset(String key, Map<String, Object> map, Long time) {
        checkKey(key);
        checkExpireTime(time);
        try {
            redisTemplate.opsForHash().putAll(key, map);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("hmset-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     * @param key   键
     * @param field  项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String field, Object value) {
        checkKey(key);
        checkField(field);
        try {
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            log.error("hset-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * hset 操作加时间
     * @param key
     * @param field
     * @param value
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String field, Object value, Long time) {
        checkKey(key);
        checkExpireTime(time);
        checkField(field);
        try {
            redisTemplate.opsForHash().put(key, field, value);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("hset-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public Long hdel(String key, Object... item) {
        checkKey(key);
        try {
            return redisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            log.error("hdel-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key  键 不能为null
     * @param field 项 不能为null
     * @return true 存在 false不存在
     */
    public Boolean hHasKey(String key, String field) {
        checkKey(key);
        checkField(field);
        try {
            return redisTemplate.opsForHash().hasKey(key, field);
        } catch (Exception e) {
            log.error("hdel-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key  键
     * @param field 项
     * @param by   要增加几(大于0)
     * @return
     */
    public Double hincr(String key, String field, Double by) {
        checkKey(key);
        checkField(field);
        try {
            return redisTemplate.opsForHash().increment(key, field, by);
        } catch (Exception e) {
            log.error("hincr-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * hash递减
     * @param key  键
     * @param field 项
     * @param by   要减少记(小于0)
     * @return
     */
    public Double hdecr(String key, String field, Double by) {
        checkKey(key);
        checkField(field);
        try {
            return redisTemplate.opsForHash().increment(key, field, by);
        } catch (Exception e) {
            log.error("hdecr-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    // ============================================set================================================

    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        checkKey(key);
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("sGet-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasKey(String key, Object value) {
        checkKey(key);
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("sHasKey-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSet(String key, Object... values) {
        checkKey(key);
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("sSet-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 将set数据放入缓存,并且设置超时时间
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSetAndTime(String key, Long time, Object... values) {
        checkKey(key);
        checkExpireTime(time);
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            expire(key, time);
            return count;
        } catch (Exception e) {
            log.error("sSetAndTime-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public Long sGetSetSize(String key) {
        checkKey(key);
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("sGetSetSize-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 移除值为value的缓存
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setRemove(String key, Object... values) {
        checkKey(key);
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("setRemove-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取key中的值
     * @param key
     * @return
     */
    public Set<Object> members(String key) {
        checkKey(key);
        try {
            Set<Object> result = redisTemplate.opsForSet().members(key);
            return result;
        }catch (Exception e) {
            log.error("members-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * set批量添加数据
     * @param key
     * @param c
     * @param expireTime
     */
    public Boolean multiAdd(String key, List<Object> c, Long expireTime) {
        checkKey(key);
        try {
            SetOperations<String, Object> set = redisTemplate.opsForSet();
            for (Object o : c) {
                set.add(key, o);
            }
            setExpire(key, expireTime);
            return true;
        }catch (Exception e) {
            log.error("multiAdd-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }

    }


    //-------------------------有序集合-Zset---------------------------------

    /**
     * 新增一个有序集合，存在为false，不存在是true
     * @param key
     * @param value
     * @param score 分数，权重
     */
    public Boolean zSetAdd(String key ,Object value, Double score){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().add(key,value,score);
        }catch (Exception e) {
            log.error("zSetAdd-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取元素之间start，并end从有序集合。
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     */
    public Set<Object> zSetRange(String key ,Long start, Long end){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().range(key,start,end);
        }catch (Exception e) {
            log.error("zSetRange-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取元素，其中得分之间min并max从有序集合。
     * @param key key
     * @param min 最小指
     * @param max 最大值
     */
    public Set<Object> zSetByScore(String key ,Double min, Double max){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,min,max);
        }catch (Exception e) {
            log.error("zSetByScore-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取范围的元素来自start于end哪里分数之间min并max从有序集合。
     * @param key key
     * @param min 最小指
     * @param max 最大值
     */
    public Set<Object> zSetByScore(String key, Double min, Double max, Long offset, Long count){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,min,max,offset,count);
        }catch (Exception e) {
            log.error("zSetByScore-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取设置的RedisZSetCommands.TupleS其中得分之间min并max从有序集合。
     * @param key key
     * @param min 最小指
     * @param max 最大值
     */
    public Set zSetRangeByScoreWithScores(String key, Double min, Double max){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key,min,max);
        }catch (Exception e) {
            log.error("zSetRangeByScoreWithScores-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取设置的RedisZSetCommands.Tuple从s的范围内start，以end其中得分之间min并 max从有序集合。
     * @param key key
     * @param min 最小指
     * @param max 最大值
     */
    public Set zSetRangeByScoreWithScores(String key, Double min, Double max, Long offset, Long count){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key,min,max,offset,count);
        }catch (Exception e) {
            log.error("zSetRangeByScoreWithScores-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取设置的RedisZSetCommands.Tuples之间start以及end从有序集合。
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     */
    public Set zSetRangeWithScores(String key ,Long start, Long end){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().rangeWithScores(key,start,end);
        }catch (Exception e) {
            log.error("zSetRangeWithScores-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取范围的元素来自start于end从下令从高分到低分排序集。
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     */
    public Set<Object> zSetReverseRange(String key ,Long start, Long end){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().reverseRange(key,start,end);
        }catch (Exception e) {
            log.error("zSetReverseRange-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取得分介于其间的元素，min以及max从高到低排序的排序集。
     * @param key key
     * @param min 最小值
     * @param max 最大值
     */
    public Set<Object> zSetReverseRangeByScore(String key ,Double min, Double max){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max);
        }catch (Exception e) {
            log.error("zSetReverseRangeByScore-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取范围从start以及end得分在哪里min和max从有序集合中排列的元素- >低。
     * @param key key
     * @param min 最小值
     * @param max 最大值
     */
    public Set<Object> zSetReverseRangeByScore(String key ,Double min, Double max,Long offset,Long count){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max,offset,count);
        }catch (Exception e) {
            log.error("zSetReverseRangeByScore-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * value在排序集中确定元素的索引。
     * @param key key
     * @param value
     */
    public Long zSetRank(String key ,Object value){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().rank(key,value);
        }catch (Exception e) {
            log.error("zSetRank-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 计算排序集内元素的数量，其中得分为min和max。
     * @param key
     * @param min
     * @param max
     */
    public Long zSetCount(String key ,Double min, Double max){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().count(key,min,max);
        }catch (Exception e) {
            log.error("zSetCount-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * values从排序集中删除。
     * @param key key
     * @param values
     */
    public Long zSetRemove(String key,Object... values){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().remove(key,values);
        }catch (Exception e) {
            log.error("zSetRemove-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 删除有序集之间start和end之间的元素
     * @param key key
     * @param start
     * @param end
     */
    public Long zSetRemoveRange(String key,Long start,Long end){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().removeRange(key,start,end);
        }catch (Exception e) {
            log.error("zSetRemoveRange-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 删除带有分数的元素min和max分类集之间的分数key。
     * @param key key
     * @param min
     * @param max
     */
    public Long zSetRemoveRangeByScore(String key,Double min,Double max){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
        }catch (Exception e) {
            log.error("zSetRemoveRangeByScore-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 返回使用给定存储的有序集的元素数key。
     * @param key key
     */
    public Long zSetSize(String key){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().size(key);
        }catch (Exception e) {
            log.error("zSetSize-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * value在评分为高到低时，在排序集中确定元素的索引。
     * @param key key
     * @param values
     */
    public Long zSetReverseRank(String key,Object values){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().reverseRank(key,values);
        }catch (Exception e) {
            log.error("zSetReverseRank-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * value从带有键的排序集中获取元素的分数key。
     * @param key key
     * @param values
     */
    public Double zSetScore(String key,Object values){
        checkKey(key);
        try {
            return redisTemplate.opsForZSet().score(key,values);
        }catch (Exception e) {
            log.error("zSetScore-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    // ===============================list=================================
    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, Long start, Long end) {
        checkKey(key);
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("lGet-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long lGetListSize(String key) {
        checkKey(key);
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("lGetListSize-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, Long index) {
        checkKey(key);
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("lGetIndex-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean lSet(String key, Object value) {
        checkKey(key);
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("lSet-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public Boolean lSet(String key, Object value, Long time) {
        checkKey(key);
        checkExpireTime(time);
        try {
            redisTemplate.opsForList().rightPush(key, value);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("lSet-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean lSet(String key, List<Object> value) {
        checkKey(key);
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("lSet-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 将list放入缓存
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public Boolean lSet(String key, List<Object> value, Long time) {
        checkKey(key);
        checkExpireTime(time);
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("lSet-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public Boolean lUpdateIndex(String key, Long index, Object value) {
        checkKey(key);
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("lUpdataIndex-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemove(String key, Long count, Object value) {
        checkKey(key);
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("lRemove-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    // =========================== GeoHash =================================
    /**
     * 批量添加 geo 信息
     * @param key geo的键
     * @param points geo组成的map
     * 比如 points.put("北京市天通苑北一区", new Point(117.12, 39.08));
     */
    public Long addAllGeo(String key, Map<Object, Point> points, Object value) {
        checkKey(key);
        try {
            Long result = redisTemplate.boundGeoOps(key).add(points);
            return result;
        } catch (Exception e) {
            log.error("addAllGeo-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 添加 geo 信息
     * @param key geo的键
     * @param name 位置名称 :北京市
     * @param point
     * 比如: new Point(117.12, 39.08);
     */
    public Long addGeo(String key,String name,Point point) {
        checkKey(key);
        try {
            Long result = redisTemplate.boundGeoOps(key).add(point,name);
            return result;
        } catch (Exception e) {
            log.error("addGeo-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 添加 geo 信息
     * @param key geo的键
     * @param name 位置名称 :北京市
     * @param lng 经度 118.22
     * @param lat 纬度 31.14
     */
    public Long addGeo(String key,String name,Double lng, Double lat) {
        checkKey(key);
        try {
            Long result = redisTemplate.boundGeoOps(key).add(new Point(lng,lat),name);
            return result;
        } catch (Exception e) {
            log.error("addGeo-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取两个地理位置的距离,可以设置距离单位
     *     这里需要注意，前提是必须在同一个key下
     * @param key geo的键
     * @param fromMember 北京市天通苑北一区
     * @param toMember  北京市丰台区总部基地
     * @return
     */
    public Double getDistance(String key,String fromMember, String toMember) {
        checkKey(key);
        try {
            BoundGeoOperations<String, Object> boundGeoOps = redisTemplate.boundGeoOps(key);
            //默认的单位为米
            Distance geoDist = boundGeoOps.distance(fromMember, toMember, Metrics.MILES);
            return geoDist.getValue();
        } catch (Exception e) {
            log.error("getDistance-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取某个成员的地理位置的坐标
     * @param member 北京市天通苑北一区
     * @param key geo的键
     * @return
     */
    public List<Point> getLocations(String key,String member) {
        checkKey(key);
        try {
            BoundGeoOperations<String, Object> boundGeoOps = redisTemplate.boundGeoOps(key);
            List<Point> geoPos = boundGeoOps.position(member);
            return geoPos;
        } catch (Exception e) {
            log.error("getLocations-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 获取某个地理位置的geohash值
     * @param member 北京市
     * @param key geo的键
     * @return
     */
    public List<String> getPositionHash(String key,String member){
        checkKey(key);
        try {
            BoundGeoOperations<String, Object> boundGeoOps = redisTemplate.boundGeoOps(key);
            List<String> hashLists = boundGeoOps.hash(member);
            return hashLists ;
        } catch (Exception e) {
            log.error("getPositionHash-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }


    /**
     * 根据给定地理位置坐标获取指定范围内的地理位置集合
     * @param lng 经度 118.22
     * @param lat 纬度 31.14
     * @param range 多少公里
     * @param count 返回条数
     * @param key geo的键
     * @return
     */
    public List<GeoHashVo> getRangeDistance(String key, Double lng, Double lat, Double range, int count) {
        checkKey(key);
        try {
            Circle circle = new Circle(new Point(lng, lat), new Distance(range, Metrics.KILOMETERS));
            //设置geo查询参数
            RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands
                    .GeoRadiusCommandArgs
                    .newGeoRadiusArgs()
                    .includeDistance()
                    .includeCoordinates()
                    .sortAscending()
                    .limit(count);
            GeoResults<RedisGeoCommands.GeoLocation<Object>> radius = redisTemplate.opsForGeo().radius(key, circle, args);
            return generatorVo(radius);
        } catch (Exception e) {
            log.error("getRangeDistance-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 根据给定成员获取指定范围内的地理位置集合
     * @param member 北京
     * @param range 距离，多少公里
     * @param count 显示多少条
     * @param key geo的键
     * @return
     */
    public List<GeoHashVo> getRangeDistanceByMember(String key,String member, Double range, int count) {
        checkKey(key);
        try {
            //设置geo查询参数
            RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands
                    .GeoRadiusCommandArgs
                    .newGeoRadiusArgs()
                    .includeDistance()
                    .includeCoordinates()
                    .sortAscending()
                    .limit(count);
            BoundGeoOperations<String, Object> boundGeoOps = redisTemplate.boundGeoOps(key);
            GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = boundGeoOps.radius(member,new Distance(range), args);
            return generatorVo(geoResults);
        } catch (Exception e) {
            log.error("getRangeDistanceByMember-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    /**
     * 删除指定成员的goe
     * @param member 北京市
     * @param key geo的键
     * @return
     */
    public Long deleteGeo(String key,String member) {
        checkKey(key);
        try {
            return redisTemplate.boundZSetOps(key).remove(member);
        } catch (Exception e) {
            log.error("deleteGeo-失败:{}", ExceptionUtils.getStackTrace(e));
            throw new RedisException(RedisErrorCode.REDIS_EXECUTE_FAIL);
        }
    }

    //==================================私有方法======================================
    /**
     * 检测key是否为空
     * @param key
     */
    private void checkKey(String key){
        if(StringUtils.isBlank(key)){
            throw new RedisException(RedisErrorCode.REDIS_KEY_IS_EMPTY);
        }
    }

    /**
     * 检测key是否为空
     * @param field
     */
    private void checkField(String field){
        if(StringUtils.isBlank(field)){
            throw new RedisException(RedisErrorCode.REDIS_FIELD_IS_EMPTY);
        }
    }

    /**
     * 检测 递增或递减因子是否大于0
     * @param delta
     */
    private void checkDelta(Long delta){
        if(delta == null){
            throw new RedisException(RedisErrorCode.REDIS_INCR_OR_DECR_GT_0);
        }
        if (delta <= 0) {
            throw new RedisException(RedisErrorCode.REDIS_INCR_OR_DECR_GT_0);
        }
    }

    /**
     * 检测超时时间是否为空。是否大于0
     * @param time
     */
    private void checkExpireTime(Long time){
        if(time == null){
            throw new RedisException(RedisErrorCode.REDIS_EXPIRE_IS_EMPTY);
        }
        if (time <= 0) {
            throw new RedisException(RedisErrorCode.REDIS_EXPIRE_LT_0);
        }
    }

    /**
     * 设置超时时间
     */
    private Boolean setExpire(final String key, Long expireTime) {
        Boolean result = false;
        try {
            result = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("setExpire-失败:{}", ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    /**
     * 组装数据
     * @param geoResults
     * @return
     */
    private List<GeoHashVo> generatorVo(GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults){
        List<GeoHashVo> geoList = new ArrayList<>();
        if (geoResults != null) {
            geoResults.forEach(geoLocationGeoResult -> {
                RedisGeoCommands.GeoLocation<Object> content = geoLocationGeoResult.getContent();
                GeoHashVo geoHashVo = new GeoHashVo();
                //member 名称  如  tianjin
                Object name = content.getName();
                // 对应的经纬度坐标
                Point pos = content.getPoint();
                // 距离中心点的距离
                Distance dis = geoLocationGeoResult.getDistance();
                geoHashVo.setMember(name.toString());
                geoHashVo.setPoint(pos);
                geoHashVo.setDistance(dis);
                geoList.add(geoHashVo);
            });
        }
        return geoList;
    }

}