package com.xin.adsystem.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.xin.commons.support.utils.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author xin
 * @date 2019/11/18
 * 高德地图 utils
 * 使用说明：
 * 第一步，申请”Web服务API”密钥（Key）；
 * 第二步，拼接HTTP请求URL，第一步申请的Key需作为必填参数一同发送；
 * 第三步，接收HTTP请求返回的数据（JSON或XML格式），解析数据。
 * 如无特殊声明，接口的输入参数和输出数据编码全部统一为UTF-8。
 * <p>
 * https://lbs.amap.com/api/webservice/guide/api/georegeo
 * API文档 提供以下接口
 * 地理/逆地理编码
 * 路径规划
 * 行政区域查询
 * 搜索POI
 * IP定位
 * 批量请求接口
 * 静态地图
 * 坐标转换
 * 天气查询
 * 输入提示
 * 交通态势
 * 地理围栏
 * 轨迹纠偏
 */
@Slf4j
public class GaoDeUtil {

    /**
     * 获取高德地图api 的key
     * private static final String TESTAPPKEY  = "b36729bd4874db9529d691c6a334a2cf";
     */
    private static final String APPKEY = "6b9be2a6e57cc6a74a3592b4422de12b";

    /**
     * app签名 如果设置签名，就需要在参数加入 sig
     */
    private static final String TESTAPPSIG = "ab4a93f92f1b4ddc4eed179a0593dad5";

    /**
     * 关键字搜索api服务
     */
    private static final String TEXTURL = "https://restapi.amap.com/v3/place/text";
    /**
     * 搜索周边
     */
    private static final String AROUNDURL = "https://restapi.amap.com/v3/place/around";

    /**
     * 根据关键词搜索 poi
     *
     * @param word     关键词 比如：加油站
     * @param cityName 城市名称 比如：北京
     * @param types    010000为汽车服务（大类）010100 为加油站（中类） 010101为中国石化（小类）
     *                 010900为汽车租赁（中类）010901为汽车租赁还车（小类）
     *                 当指定010000，则010100等中类、010101等小类都会被包含，当指定010900，则010901等小类都会被包含
     *                 ttps://lbs.amap.com/api/webservice/download
     * @param offset   每页显示多少条 建议不要超过25条
     * @param page     当前页 最大100页
     * @return
     */
    public static JSONObject getGaoDePoiByWord(String word, String cityName, String types,
                                               String offset, String page) {
        StringBuilder builder = new StringBuilder(TEXTURL);
        builder.append("?key=");
        builder.append(APPKEY);
        builder.append("&keywords=");
        builder.append(word);
        builder.append("&types=");
        builder.append(types);
        builder.append("&city=");
        builder.append(cityName);
        //仅返回指定城市数据
        builder.append("&citylimit=");
        builder.append("true");
        //是否按照层级展示子POI数据 当为0的时候，子POI都会显示。当为1的时候，子POI会归类到父POI之中。 仅在extensions=all的时候生效
        builder.append("&children=");
        builder.append("0");
        builder.append("&offset=");
        builder.append(offset);
        //page 当前页数 最大翻页数100
        builder.append("&page=");
        builder.append(page);
        //返回结果控制 此项默认返回基本地址信息；取值为all返回地址信息、附近POI、道路以及道路交叉口信息。base/all
        builder.append("&extensions=");
        builder.append("all");
        //返回数据格式类型 可选值：JSON，XML
        builder.append("&output=");
        builder.append("JSON");
        String result = OkHttpClientUtil.get(builder.toString());
        if (StringUtils.isBlank(result)) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(result);
        } catch (Exception e) {
            log.error("GaoDeUtil.getGaoDePoiByWord 失败：{}", ExceptionUtils.getStackTrace(e));
        }
        return jsonObject;
    }

    public static void main(String[] args) {

        System.out.println(getGaoDePoiByWord("加油站", "河南省", "010100", "25", "1"));
    }
}