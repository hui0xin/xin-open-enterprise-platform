package com.xin.adsystem.common.utils;

import com.alibaba.fastjson.JSON;
import com.xin.commons.support.utils.DateUtil;
import com.xin.commons.support.utils.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 腾讯实时语言合成util
 * sdk https://cloud.tencent.com/document/product/1073/37933
 * api https://cloud.tencent.com/document/product/1073/34093
 **/
@Slf4j
public class TencentTTSStreamUtil {

    /**
     * 配置申请的KEY
     */
    public static final String secretId = "AKIDX0JVBnvYijeOlzxFAvW4RecUBrQqmHKE";
    public static final String secretKey = "wjKnBQTBpV3QeotnsxwxuwaKtIdv55uH";
    public static final Integer appId = 1255510688;

    public static final String host = "tts.cloud.tencent.com";
    public static final String region = "ap-beijing";
    public static final String action = "TextToStreamAudio";

    /**
     * 构造hearder参数
     *
     * @return
     */
    private static Map<String, Object> getHeardParms(String authorization) {
        Map<String, Object> headers = new TreeMap<>();
        headers.put("Authorization", authorization);
        headers.put("Content-Type", "application/json");
        return headers;
    }


    /**
     * @param context
     * @param volume    音量大小，范围：[0，10]，分别对应11个等级的音量，默认值为0，代表正常音量。没有静音选项。输入除以上整数之外的其他参数不生效，按默认值处理
     * @param speed     speed 语速，范围：[-2，2]，分别对应不同语速： -2代表0.6倍 -1代表0.8倍 0代表1.0倍（默认）1代表1.2倍  2代表1.5倍 输入除以上整数之外的其他参数不生效，按默认值处理
     * @param voiceType 音色： 0：亲和女声（默认）1：亲和男声 2：成熟男声 4：温暖女声 5：情感女声 6：情感男声
     * @return
     * @throws Exception
     */
    public static byte[] getBitMp3(String context, Integer volume, Integer speed, Integer voiceType) throws Exception {
        // TreeMap可以自动排序
        TreeMap<String, Object> params = new TreeMap<>();
        Long time = System.currentTimeMillis() / 1000;
        params.put("Nonce", new Random().nextInt(Integer.MAX_VALUE));
        params.put("Timestamp", time);
        params.put("Expired", time + 10000);
        params.put("SecretId", secretId);
        params.put("Action", action);
        params.put("AppId", appId);
        params.put("Version", DateUtil.formatToShort());
        params.put("Region", region);
        params.put("Text", context);

        params.put("Volume", volume);
        params.put("Speed", speed);
        params.put("VoiceType", voiceType);

        params.put("Codec", "pcm");
        params.put("SessionId", UUID.randomUUID().toString());
        String signStr = getStringToSign(params);
        String signature = sign(signStr, secretKey, "HmacSHA1");
        String jsonStr = JSON.toJSONString(params);
        byte[] result = OkHttpClientUtil.postJSONHearder("https://" + host + "/stream", jsonStr, getHeardParms(signature));
        return result;
    }

    public static String sign(String s, String key, String method) throws Exception {
        Mac mac = Mac.getInstance(method);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(s.getBytes("UTF-8"));
        return DatatypeConverter.printBase64Binary(hash);
    }

    public static String getStringToSign(TreeMap<String, Object> params) {
        StringBuilder s2s = new StringBuilder("POST" + host + "/stream?");
        // 签名时要求对参数进行字典排序，此处用TreeMap保证顺序
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k).toString()).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }

    /**
     * 将bit数组转成文件
     *
     * @param contents
     * @param filePath
     */
    public static void byteToFile(byte[] contents, String filePath) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream output = null;
        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(contents);
            bis = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            // 获取文件的父路径字符串
            File path = file.getParentFile();
            if (!path.exists()) {
                log.info("文件夹不存在，创建。path={}", path);
                boolean isCreated = path.mkdirs();
                if (!isCreated) {
                    log.error("创建文件夹失败，path={}", path);
                }
            }
            fos = new FileOutputStream(file);
            // 实例化OutputString 对象
            output = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                output.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            output.flush();
        } catch (Exception e) {
            log.error("输出文件流时抛异常，filePath={}", filePath, ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                bis.close();
                fos.close();
                output.close();
            } catch (IOException e0) {
                log.error("文件处理失败，filePath={}", filePath, ExceptionUtils.getStackTrace(e0));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] result = getBitMp3("华为法国官方推特上有关石墨烯电池的消息引起了网友的广泛关注，似乎预示着华为P40系列有可能会首次配备石墨烯电池。不过，华为法国官方很快删除了这条推文，并表示仅仅是谣言，并非官方信息。由此看来所谓石墨烯电池的说法更多坊间的揣测，但有可能会为电池采用新材料来提升安全性和转换率，据传华为P40系列还会用上陶瓷机身，或将于3月18日在法国巴黎正式登场。", 0, 0, 0);
        byteToFile(result, "/data1/file/cpmTempFile.pcm");
        //将pcm文件转成mp3
        PcmTestUtils.convert2Wav("/data1/file/cpmTempFile.pcm", "/data1/file/cpmTempFile.mp3", 16000, 1, 16);
    }

}
