package com.xin.commons.support.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求校验工具类
 * @author: xin
 */
@Slf4j
public class SignUtil {
    /**
     * 校验签名是否合法
     *
     * @param request
     * @param secret
     * @param param
     * @param signature
     * @return
     * @throws Exception
     */
    public static boolean validateSignature(HttpServletRequest request, String secret, String param, String signature) throws Exception {
        Map<String, String> paramMap = generateParamMap(request, param);

        // 待签名的字符串
        String tempStr = generateSortString(paramMap) + "&key=" + secret;
        // 生成签名串
        String signStr = generateSignture(tempStr);

        if (signStr.equalsIgnoreCase(signature)) {
            log.error("验证签名通过.[requestUrl=%s]", request.getRequestURL().toString() + "?" + request.getQueryString());
            return true;
        } else {
            log.error("验证签名不通过.[requestUrl=%s][tempStr=%s][signStr=%s][signature=%s]",
                    request.getRequestURL().toString() + "?" + request.getQueryString(), tempStr, signStr, signature);

            return false;
        }
    }

    /**
     * 根据待加签的字符串生成签名
     *
     * @param tempStr
     * @return
     */
    public static String generateSignture(String tempStr) {
        return DigestUtils.sha1Hex(tempStr).toUpperCase();
    }

    /**
     * 从请求对象获取待签名的字符串
     *
     * @param request
     * @param param
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static Map<String, String> generateParamMap(HttpServletRequest request, String param)
            throws UnsupportedEncodingException, IOException {

        // 用于签名的所有请求参数（包括path、query、body）
        Map<String, String> map = new HashMap<>();

        // 添加 query 参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null && parameterMap.size() > 0) {
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                // 排除签名本身的参数值
                if (!key.equalsIgnoreCase(param)) {
                    String[] value = entry.getValue();
                    map.put(key, URLDecoder.decode(value[0], "UTF-8"));
                }
            }
        }

        // 添加 body 参数
        String method = request.getMethod();
        if (HttpUtil.METHOD_POST.equalsIgnoreCase(method)
                || HttpUtil.METHOD_PUT.equalsIgnoreCase(method)
                || HttpUtil.METHOD_DELETE.equalsIgnoreCase(method)) {
            String requestBody = HttpUtil.requestBody(request);
            if (StringUtils.isNotBlank(requestBody)) {
                Map<String, String> bodyMap = JsonUtil.jsonToMap(requestBody);
                if (bodyMap != null && bodyMap.size() > 0) {
                    map.putAll(bodyMap);
                }
            }
        }

        return map;
    }

    /**
     * 请求参数按照字典序生成字符串
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String generateSortString(final Map<String, String> data) throws Exception {
        Set<String> keySet = data.keySet();
        String[] arr = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(arr);

        StringBuilder sb = new StringBuilder();
        for (String k : arr) {
            if (data.get(k).trim().length() > 0) {
                // 这里做了特殊处理，对于mushroom参数，需要将空格替换成
                if ("mushroom".equalsIgnoreCase(k)) {
                    String v = data.get(k).trim().replaceAll(" ", "+");
                    sb.append(k).append("=").append(v).append("&");
                } else {
                    sb.append(k).append("=").append(data.get(k).trim()).append("&");
                }
            }
        }

        String result = sb.toString();
        if (result.endsWith("&")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try (InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"))) {
            Map<String, String> data = xmlToMap(stream);
            return data;
        }
    }

    /**
     * 解析XML为MAP
     *
     * @param stream
     * @return
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(InputStream stream) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = db.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        Map<String, String> data = new HashMap<String, String>();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        return data;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();
        try {
            writer.close();
        } catch (Exception ex) {
        }
        return output;
    }

}