package com.xin.adsystem.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xin.commons.support.utils.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;


/**
 * QQ音乐util
 **/
@Slf4j
public class QQMusicUtil {

    private static Long guid = 987848448L;
    /**
     * 获取排行榜类型列表url
     */
    private static String rankTypeUrl = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_opt.fcg?page=index&format=html&tpl=macv4&v8debug=1&jsonCallback=jsonCallback";

    /**
     * 获取排行榜列表歌曲url
     */
    private static String musicListUrl = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?g_tk=5381&uin=0&format=json&inCharset=utf-8&outCharset=utf-8¬ice=0&platform=h5&needNewCode=1&tpl=3&page=detail&type=top";

    /**
     * 搜索歌曲
     * p	分页 默认1
     * n	请求数量	默认10
     */
    private static String searchUrl = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=泡沫&format=json";

    /**
     * 搜索专辑
     * p	分页 默认1
     * n	请求数量	默认10
     * t	类别	是	没有默认值，如果为搜索专辑，则t为8。如果是搜索mv,t=12。
     */
    private static String searchAlbumUrl = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=邓紫棋&format=json&t=8";

    /**
     * 获取token
     * json205361747
     */
    private static String tokenUrl = "https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?format=json&platform=yqq&cid=205361747";

    /**
     * 获取播放地址
     * 说明：这个是根据歌曲songmid来获得音乐播放地址的，
     * 请求示例很长，在经过多次尝试后，很遗憾的并不能缩减，
     * 要想获取播放地址，只需要更改上面蓝色字体的songmid后的001X0PDf0W4lBq即可，
     * 至于更改方法很多，比如直接用Java的字符串拼接。请求成功后请注意两个字段purl和sip（为数组）,
     * 歌曲的播放地址就是sip数组里的其中一个加上pur，即sip[0]+purl（vip音乐或者版权音乐的purl为空）
     */
    private static String getPlayUrl1 = "https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22songmid%22%3A%5B%22";
    private static String getPlayUrl2 = "%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%2218585073516%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D";

    /**
     * 获取token
     * songmid是003lghpv0jfFXG，则filename就是前缀加上C400，后缀加上.m4a
     * 其他字段format、platform、cid、guid可以写死
     */
    public static String getToken(String songmid) {
        String result = OkHttpClientUtil.get(tokenUrl + "&guid=" + guid + "&songmid=" + songmid + "&filename=C400" + songmid + ".m4a");
        JSONObject obj = JSONObject.parseObject(result);
        Integer code = obj.getInteger("code");
        if (!(code.equals(0)) || code != 0) {
            return null;
        }
        JSONObject jsonData = obj.getJSONObject("data");
        JSONObject jsonItem = jsonData.getJSONArray("items").getJSONObject(0);
        String vkey = jsonItem.getString("vkey");
        return vkey;
    }

    /**
     * 获取音乐播放地址
     */
    public static String getMusicInfo(String songmid) {
        String result = OkHttpClientUtil.get(getPlayUrl1 + songmid + getPlayUrl2);
        JSONObject obj = JSONObject.parseObject(result).getJSONObject("req_0");
        Integer code = obj.getInteger("code");
        if (!(code.equals(0)) || code != 0) {
            return null;
        }
        JSONObject jsonData = obj.getJSONObject("data");
        JSONArray jsonSip = jsonData.getJSONArray("sip");
        String url = "";
        if (!ObjectUtils.isEmpty(jsonSip)) {
            Object sip = jsonSip.get(1);
            url = sip.toString();
        }
        JSONArray midurlinfo = jsonData.getJSONArray("midurlinfo");
        JSONObject obj2 = midurlinfo.getJSONObject(0);
        String purl = obj2.getString("purl");
        return url + purl;
    }

    /**
     * 获取首页排行榜类型累表
     * page=index  表示这是排行榜的首页
     * format=html 返回格式是html
     * debug：debug=1为调试模式，直接返回跨域json格式，其他值直接返回html页面
     */
    public static String getRankTypeList() {
        String result = OkHttpClientUtil.get(rankTypeUrl);
        System.out.println(result);
        return result;
    }

    /**
     * 获取音乐列表
     * topid = 60 抖音排行榜
     * topid = 62 飙升榜
     * topid = 58 说唱榜
     * topid = 57 电音榜
     * topid = 59 香港
     * topid = 28 巅峰榜-网络
     * topid = 5 巅峰榜-内地
     * topid = 3 巅峰榜-欧美
     * topid = 16 巅峰榜-韩国
     * topid = 27 新歌榜 top100
     * topid = 29 影视金曲
     * topid = 36 K歌金曲 随机歌曲
     * topid = 65 国风热歌榜
     * topid = 66 ACG新歌榜
     * date：查询日期
     * 如果排行榜是按天统计，日期统计到昨天，格式为“2017-09-12” 如果排行榜是按周统计，统计到上周的星期四，格式为“2017_36”,标示2017年的第36周
     * <p>
     * 具体怎么取，主要取“排行榜分类”接口返回的“update_key”字段值
     * song_begin：歌曲开始标记，从0开始
     * song_num：歌曲数量
     */
    public static JSONArray getMusicList(Integer topid) {
        String result = OkHttpClientUtil.get(musicListUrl + "+&topid=" + topid);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        JSONObject obj = JSONObject.parseObject(result);
        Integer code = obj.getInteger("code");
        if (!(code.equals(0)) || code != 0) {
            return null;
        }
        JSONArray songlistArray = obj.getJSONArray("songlist");
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < songlistArray.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObj = songlistArray.getJSONObject(i).getJSONObject("data");
            jsonObject.put("songname", jsonObj.getString("songname"));
            jsonObject.put("songmid", jsonObj.getString("songmid"));
            //专辑
            jsonObject.put("albumname", jsonObj.getString("albumname"));
            JSONObject singerObj = jsonObj.getJSONArray("singer").getJSONObject(0);
            jsonObject.put("singer", singerObj.getString("name"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public static void main(String[] args) {
        System.out.println(getMusicList(27));
        System.out.println(getMusicInfo("001jIFjx4V2D74"));

    }

}
