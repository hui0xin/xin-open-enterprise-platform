package com.xin.commons.support.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * OKhttp客户端
 * final：
 * 　　　　final修饰的属性的初始化可以在编译期，也可以在运行期，初始化后不能被改变。
 * 　　　　final修饰的属性跟具体对象有关，在运行期初始化的final属性，不同对象可以有不同的值。
 * 　　　　final修饰的属性表明是一个常数（创建后不能被修改）。
 * 　　　　final修饰的方法表示该方法在子类中不能被重写；
 * 　　　　final修饰的类表示该类不能被继承。
 *        对于基本类型数据，final会将值变为一个常数（创建后不能被修改）
 * static：
 * 　　　　static修饰的属性的初始化在编译期（类加载的时候），初始化后能改变。
 * 　　　　static修饰的属性所有对象都只有一个值。
 * 　　　　static修饰的属性强调它们只有一个。
 * 　　　　static修饰的属性、方法、代码段跟该类的具体对象无关，不创建对象也能调用static修饰的属性、方法等
 * 　　　　static不可以修饰局部变量。
 * @author: xin
 */
@Slf4j
public class OkHttpClientUtil {

    private final static OkHttpClient okHttpClientFinal = new OkHttpClient();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");


    public static X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s){
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s){
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static SSLSocketFactory sslSocketFactory() {
        try {
            //信任任何链接
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化okhttpclient
     */
    private static OkHttpClient okHttpClient = okHttpClientFinal.newBuilder()
            .sslSocketFactory(sslSocketFactory(),x509TrustManager())
            //连接池
            .connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
            .retryOnConnectionFailure(true)
            .connectTimeout(8, TimeUnit.SECONDS)
            .writeTimeout(8, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();

    /**
     * 根据map获取get请求参数
     * @param queries
     * @return
     */
    public static StringBuffer getQueryString(String url,Map<String,Object> queries){
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return sb;
    }

    /**
     * 调用okhttp的newCall方法
     * @param request
     * @return
     */
    private static String executeHttp(Request request){
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }else{
                log.error("okhttp3 send error");
                return null;
            }
        } catch (Exception e) {
            log.error("okhttp3 send error >> ex = {}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * 调用okhttp的newCall方法
     * @param request
     * @return
     */
    private static byte[] executeHttpByByte(Request request){
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                return response.body().bytes();
            }else{
                log.error("okhttp3 send error");
                return null;
            }
        } catch (Exception e) {
            log.error("okhttp3 send error >> ex = {}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }



    /**
     * 发送get请求
     *
     * @param url 必穿参数
     * @return
     */
    public static String get(String url) {
        if (StringUtils.isEmpty(url)) {
            log.error("sendGet：url为空");
            return null;
        }
        Request request = new Request.Builder()
                .url(url)
                //关闭长链接
                .addHeader("Connection","close")
                .build();
        return executeHttp(request);
    }

    /**
     * 发送get请求
     *
     * @param url 必穿参数
     * @return
     */
    public static String getResultByHearder(String url, JSONObject hearders) {
        if (StringUtils.isEmpty(url)) {
            log.error("sendGet：url为空");
            return null;
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        Request.Builder requestBuilder = request.newBuilder();
        for(Map.Entry<String, Object> entry : hearders.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            requestBuilder.addHeader(key,value);
        }
        return executeHttp(requestBuilder.build());
    }

    /**
     * get
     * @param url     请求的url
     * @param params 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        if (StringUtils.isEmpty(url)) {
            log.error("sendGet 有 params：url为空");
            return null;
        }
        StringBuffer sb = getQueryString(url,params);
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        return executeHttp(request);
    }

    /**
     * 发送post请求 form表单
     *
     * @param url 必穿参数
     * @param params 参数列表
     * @return
     */
    public static String postForm(String url,Map<String,String> params) {
        if (StringUtils.isEmpty(url)) {
            log.error("sendPostForm：url为空");
            return null;
        }
        FormBody.Builder builder = new FormBody.Builder();
        if(params != null && params.keySet().size() > 0){
            for (String key : params.keySet()) {
                //追加表单信息
                builder.add(key, params.get(key));
            }
        }
        // 构造Request->call->执行
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return executeHttp(request);
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static byte[] postJSONHearder(String url,String json,Map<String,Object> map) {
        if (StringUtils.isEmpty(url)) {
            log.error("sendPostJson：url为空");
            return null;
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Request.Builder requestBuilder = request.newBuilder();
        for(Map.Entry<String, Object> entry : map.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue().toString();
            requestBuilder.addHeader(mapKey,mapValue);
        }
        return executeHttpByByte(requestBuilder.build());
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String postJson(String url,String json) {
        if (StringUtils.isEmpty(url)) {
            log.error("sendPostJson：url为空");
            return null;
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return executeHttp(request);
    }

    /**
     * Post请求发送xml数据....
     * 参数一：请求Url
     * 参数二：请求的xmlString
     * 参数三：请求回调
     */
    public static String postXml(String url, String xml) {
        if (StringUtils.isEmpty(url)) {
            log.error("sendXmlJson：url为空");
            return null;
        }
        RequestBody requestBody = RequestBody.create(JSON, xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return executeHttp(request);
    }


    /**
     * http上传文件
     *
     * @param url 必穿参数
     * @param params 参数
     * @param files 文件
     * @return
     */
    public static String fileUpload(String url, Map<String,String> params, Map<String,String> files) {
        if (StringUtils.isEmpty(url)) {
            log.error("fileUpload：url为空");
            return null;
        }
        // form 表单形式上传,MultipartBody的内容类型是表单格式，multipart/form-data
        MultipartBody.Builder urlBuilder= new MultipartBody.Builder().setType(MultipartBody.FORM);
        //参数
        if (params != null) {
            for (String key : params.keySet()) {
                if (params.get(key)!=null){
                    urlBuilder.addFormDataPart(key, params.get(key));
                }
            }
        }
        //需要上传的文件，需要携带上传的文件（小型文件 不建议超过500K）
        if (files != null) {
            for (String key : files.keySet()) {
                //重点：RequestBody create(MediaType contentType, final File file)构造文件请求体RequestBody
                urlBuilder.addFormDataPart(key, files.get(key), RequestBody.create(MediaType.parse("multipart/form-data"), files.get(key)));
            }
        }
        //构造请求request
        Request request = new Request.Builder()
                .url(url)
                .post(urlBuilder.build())
                .build();
        return executeHttp(request);
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String postJson2(String url) {
        File file = new File("/Users/hx/get_token.proto");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        return executeHttp(request);
    }

    public static void main(String[] args) {

//        JSONObject object = new JSONObject();
//        object.put("device_id", "fbf8fd55c092c2b");
//        object.put("channel", "3_1");
//        object.put("ts", "1552031309815");
//        object.put("app_secret", "674053d5fcb71754234e552a196b1f42");

        String result = postJson2("https://thirdpartyaccess.sparta.html5.qq.com/map_ugc/get_token");
        System.out.println(result);

    }

}

