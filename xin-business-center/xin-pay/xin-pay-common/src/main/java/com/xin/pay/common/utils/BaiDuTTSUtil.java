package com.xin.pay.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.xin.commons.support.utils.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 百度语言合成util
 * https://ai.baidu.com/ai-doc/SPEECH/Qk38y8lrl
 **/
@Slf4j
public class BaiDuTTSUtil {

    /**
     * 配置申请的KEY
     */
    public static final String apiKey = "po7WiNkrlQvP8LXfLV2oNZOI";
    public static final String secretKey = "WbZjRBG2qIR8GjYKMDf4XRG9YIZg36pm";

    /**
     * 获取tonken url
     */
    private static final String tokenUrl = "https://openapi.baidu.com/oauth/2.0/token";

    /**
     * 合成mp3 url
     */
    private static final String ttsUrl = "https://tsn.baidu.com/text2audio";

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        String getTokenURL = tokenUrl + "?grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + secretKey;
        String result = OkHttpClientUtil.get(getTokenURL);
        return JSONObject.parseObject(result).getString("access_token");
    }

    /**
     * 从HttpURLConnection 获取返回的字符串
     *
     * @param conn
     * @return
     */
    public static String getResponseString(HttpURLConnection conn) throws Exception {
        return new String(getResponseBytes(conn));
    }

    /**
     * 从HttpURLConnection 获取返回的bytes
     * 注意 HttpURLConnection自身问题， 400类错误，会直接抛出异常。不能获取conn.getInputStream();
     *
     * @param conn
     * @return
     */
    public static byte[] getResponseBytes(HttpURLConnection conn) throws Exception {
        int responseCode = conn.getResponseCode();
        InputStream inputStream = conn.getInputStream();
        if (responseCode != 200) {
            System.err.println("http 请求返回的状态码错误，期望200， 当前是 " + responseCode);
            if (responseCode == 401) {
                System.err.println("可能是appkey appSecret 填错");
            }
            System.err.println("response headers" + conn.getHeaderFields());
            if (inputStream == null) {
                inputStream = conn.getErrorStream();
            }
            byte[] result = getInputStreamContent(inputStream);
            System.err.println(new String(result));
        }
        byte[] result = getInputStreamContent(inputStream);
        return result;
    }

    /**
     * 将InputStream内的内容全部读取，作为bytes返回
     *
     * @param is
     * @return
     * @throws IOException @see InputStream.read()
     */
    public static byte[] getInputStreamContent(InputStream is) throws Exception {
        byte[] b = new byte[1024];
        // 定义一个输出流存储接收到的数据
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 开始接收数据
        int len = 0;
        while (true) {
            len = is.read(b);
            if (len == -1) {
                // 数据读完
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 获取token
     *
     * @return
     */
    public static void getAudio(String context) throws Exception {
        String params = getParms(context);
        System.out.println(params);
        HttpURLConnection conn = (HttpURLConnection) new URL(ttsUrl).openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
        printWriter.write(params);
        printWriter.close();
        String contentType = conn.getContentType();
        if (contentType.contains("audio/")) {
            byte[] bytes = getResponseBytes(conn);
            String format = "mp3";
            File file = new File("/Users/hx/result." + format);
            System.out.println("-----------------------------------" + file.getPath());
            FileOutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
            System.out.println("audio file write to " + file.getAbsolutePath());
        } else {
            System.err.println("ERROR: content-type= " + contentType);
            String res = getResponseString(conn);
            System.err.println(res);
        }

        return;
    }


    public static void main(String[] args) {
        try {
            getAudio("付凌晖表示，中国经济发展阶段发生了很大变化，经济增速不会像过去那样高。未来，按照高质量发展要求，如果就业较充分，物价保持稳定，" +
                    "居民收入持续增加，生态环境改善，发展质量和效益提高，经济增速高一点或者低一点，只要符合发展阶段要求，都是可以接受的。从未来发展看，中国有近14亿人口、" +
                    "4亿左右中等收入群体，市场规模巨大。按照世界银行标准，中国国民收入水平在中上等收入国家行列，2018年我国人均国民总收入水平为9700多美元，" +
                    "未来将向高收入水平迈进。前三季度，居民人均可支配收入同比增长6.1%。此外，就业形势比较稳定，社会保障覆盖面扩大，有利于稳定消费预期、" +
                    "增强消费信心。未来，中国发展以内需和消费拉动为主的趋势将更加明显，经济增长的可持续性将进一步提高");
        } catch (Exception e) {

        }
    }

    /**
     * UrlEncode， UTF-8 编码
     *
     * @param str 原始字符串
     * @return
     */
    public static String urlEncode(String str) {
        String result = null;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取参数
     *
     * @return
     */
    private static String getParms(String context) {
        StringBuffer buffer = new StringBuffer();
        //合成的文本，使用UTF-8编码。小于2048个中文字或者英文数字。（文本在百度服务器内转换为GBK后，长度必须小于4096字节）
        buffer.append("tex=");
        buffer.append(urlEncode(urlEncode(context)));
        //固定值zh。语言选择,目前只有中英文混合模式，填写固定值zh
        buffer.append("&lan=");
        buffer.append("zh");
        //客户端类型选择，web端填写固定值1
        buffer.append("&ctp=");
        buffer.append(1);
        //开放平台获取到的开发者access_token（见上面的“鉴权认证机制”段落）
        buffer.append("&tok=");
        buffer.append(getToken());
        //用户唯一标识，用来计算UV值。建议填写能区分用户的机器 MAC 地址或 IMEI 码，
        buffer.append("&cuid=");
        buffer.append("xinwanglian_sunflower");
        //语速，取值0-15，默认为5中语速
        buffer.append("&spd=");
        buffer.append(5);
        //音调，取值0-15，默认为5中语调
        buffer.append("&pit=");
        buffer.append(5);
        //音量，取值0-15，默认为5中音量
        buffer.append("&vol=");
        buffer.append(5);
        //基础音库 度小宇=1，度小美=0，度逍遥=3，度丫丫=4 ,   精品音库 度博文=106，度小童=110，度小萌=111，度米朵=103，度小娇=5
        buffer.append("&per=");
        buffer.append(1);
        //3为mp3格式(默认)； 4为pcm-16k；5为pcm-8k；6为wav（内容同pcm-16k）;
        // 注意aue=4或者6是语音识别要求的格式，但是音频内容不是语音识别要求的自然人发音，所以识别效果会受影响。
        buffer.append("&aue=");
        buffer.append(3);
        return buffer.toString();
    }

}
