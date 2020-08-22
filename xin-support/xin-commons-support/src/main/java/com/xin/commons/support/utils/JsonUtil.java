package com.xin.commons.support.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;
import java.util.Map;

/**
 * Json数据处理工具类
 * @author: xin
 */
public class JsonUtil {

    /**
     * 初始化ObjectMapper，由于ObjectMapper线程安全，可以在不同线程复用
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper
                // 日期不转换为Timestamp，转换为字符串
                //.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                //.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                //.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)
                //.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                // 允许序列化空的POJO类
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                // 在遇到未知属性的时候不抛出异常
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 在遇到忽略属性的时候不抛出异常
                .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    }

    public static ObjectMapper defaultObjectMapper() {
        return objectMapper;
    }

    /**
     * 将对象转换为json
     */
    public static String objectToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 将json转换为对象
     */
    public static <T> T jsonToObject(String json, Class<T> clz) throws IOException {
        return objectMapper.readValue(json, clz);
    }

    /**
     * 将map转换为json
     */
    public static String mapToJson(Map<String, Object> map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }

    /**
     * 将json转换为map
     */
    public static Map<String, String> jsonToMap(String json) throws IOException {
        return objectMapper.readValue(json, new TypeReference<Map<String, String>>() {
        });
    }

    /**
     * 将对象转换为map
     */
    public static Map<String, Object> objectToMap(Object object) throws IOException {
        String json = objectToJson(object);
        return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
    }


    /**
     * 将json转换为map
     *
     * @param json       反序列化的json字符串
     * @param keyClazz   key的对象类型
     * @param valueClazz value的对象类型
     * @return 反序列化后的map
     * @throws IOException
     */
//    public static <K, T> Map<K, T> jsonToMap(String json, Class<K> keyClazz, Class<T> valueClazz){
//        return jsonToMap(defaultObjectMapper(), json, keyClazz, valueClazz);
//    }
//
//    public static <K, T> Map<K, T> jsonToMap(ObjectMapper objectMapper, String json, Class<K> keyClazz, Class<T> valueClazz) throws IOException {
//        JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz);
//        return objectMapper.readValue(json, javaType);
//    }

    /**
     * 将对象转换为json
     *
     * @param object 被序列化的对象
     * @return 序列化对象之后的json字符串
     */
//    public static <T> String objectToJson(T object) {
//        return objectToJson(defaultObjectMapper(), object);
//    }
//
//    public static <T> String objectToJson(ObjectMapper objectMapper, T object) {
//        try {
//            return objectMapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            throw new JsonException("object序列化为json出错", e);
//        }
//    }

    /**
     * 将json转换为对象
     *
     * @param json 反序列化的json字符串
     * @return 相应类型的对象
     */
//    public static <T> T jsonToObject(String json, Class<T> clazz) {
//        return jsonToObject(defaultObjectMapper(), json, clazz);
//    }
//
//    public static <T> T jsonToObject(ObjectMapper objectMapper, String json, Class<T> clazz) {
//        try {
//            return objectMapper.readValue(json, clazz);
//        } catch (IOException e) {
//            throw new JsonException("json反序列化为object出错", e);
//        }
//    }

    /**
     * 将list转换为json
     *
     * @param list 对象列表
     * @return 序列化对象之后的json字符串
     */
//    public static <T> String listToJson(List<T> list) {
//        return listToJson(defaultObjectMapper(), list);
//    }
//
//    public static <T> String listToJson(ObjectMapper objectMapper, List<T> list) {
//        try {
//            return objectMapper.writeValueAsString(list);
//        } catch (JsonProcessingException e) {
//            throw new JsonException("list序列化为json出错", e);
//        }
//    }

    /**
     * 将json转换为list
     *
     * @param json       反序列化的json字符串
     * @param listClazz  列表类型
     * @param valueClazz 对象类型
     * @return 相应类型的对象列表
     */
//    public static <T> List<T> jsonToList(String json, Class<?> listClazz, Class<T> valueClazz) {
//        return jsonToList(defaultObjectMapper(), json, listClazz, valueClazz);
//    }
//
//    public static <T> List<T> jsonToList(ObjectMapper objectMapper, String json, Class<?> listClazz, Class<T> valueClazz) {
//        try {
//            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(listClazz, valueClazz);
//            return objectMapper.readValue(json, javaType);
//        } catch (IOException e) {
//            throw new JsonException("json反序列化为list出错", e);
//        }
//    }

    /**
     * 将set转换为json
     *
     * @param set 对象列表
     * @return 序列化对象之后的json字符串
     * @throws JsonProcessingException
     */
//    public static <T> String setToJson(Set<T> set) {
//        return setToJson(defaultObjectMapper(), set);
//    }
//
//    public static <T> String setToJson(ObjectMapper objectMapper, Set<T> set) {
//        try {
//            return objectMapper.writeValueAsString(set);
//        } catch (JsonProcessingException e) {
//            throw new JsonException("set序列化为json出错", e);
//        }
//    }

    /**
     * 将json转换为set
     *
     * @param json       反序列化的json字符串
     * @param setClazz   列表类型
     * @param valueClazz 对象类型
     * @return 相应类型的对象列表
     */
//    public static <T> Set<T> jsonToSet(String json, Class<?> setClazz, Class<T> valueClazz) {
//        return jsonToSet(defaultObjectMapper(), json, setClazz, valueClazz);
//    }
//
//    public static <T> Set<T> jsonToSet(ObjectMapper objectMapper, String json, Class<?> setClazz, Class<T> valueClazz) {
//        try {
//            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(setClazz, valueClazz);
//            return objectMapper.readValue(json, javaType);
//        } catch (IOException e) {
//            throw new JsonException("json反序列化为set出错", e);
//        }
//    }

    /**
     * 将map转换为json
     *
     * @param map 待序列化的map
     * @return json字符串
     * @throws JsonProcessingException
     */
//    public static <K, T> String mapToJson(Map<K, T> map) {
//        return mapToJson(defaultObjectMapper(), map);
//    }
//
//    public static <K, T> String mapToJson(ObjectMapper objectMapper, Map<K, T> map) {
//        try {
//            return objectMapper.writeValueAsString(map);
//        } catch (JsonProcessingException e) {
//            throw new JsonException("map序列化为json出错", e);
//        }
//    }



//    public static JsonNode jsonToNode(String json) {
//        try {
//            return objectMapper.readTree(json);
//        } catch (IOException e) {
//            throw new JsonException("json反序列化为node出错", e);
//        }
//    }

//    public static <T> T apply(UncheckedFunction<ObjectMapper, T> f) {
//        return apply(f, "json操作出错");
//    }
//
//    public static <T> T apply(UncheckedFunction<ObjectMapper, T> f, String errorMessage) {
//        try {
//            return f.apply(defaultObjectMapper());
//        } catch (Exception e) {
//            throw new JsonException(errorMessage, e);
//        }
//    }
//
//    public static void accept(UncheckedConsumer<ObjectMapper> f) {
//        accept(f, "json操作出错");
//    }
//
//    public static void accept(UncheckedConsumer<ObjectMapper> f, String errorMessage) {
//        try {
//            f.accept(defaultObjectMapper());
//        } catch (Exception e) {
//            throw new JsonException(errorMessage, e);
//        }
//    }

}
