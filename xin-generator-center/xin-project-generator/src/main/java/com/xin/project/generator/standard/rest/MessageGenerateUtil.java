package com.xin.project.generator.standard.rest;

import com.xin.project.generator.utils.FilePathUtils;
import com.xin.project.generator.utils.FreeMarkerTemplateUtils;
import com.xin.project.generator.utils.PathConstant;
import freemarker.template.Template;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 message 工具类，
 * message可能后期会很多，所以单独写一个
 */
public class MessageGenerateUtil {

    /**
     * 生成 messsage
     * filePath = .......-rest
     */
    public static void generateMessages(String filePath,String projectName) throws Exception {
        generateEnglish(filePath,projectName);
        generatezh_CN(filePath,projectName);
        generatezh_HK(filePath,projectName);
        generatezh_TW(filePath,projectName);
    }

    /**
     * 生成 英文语言配置 messages.properties
     * filePath = .......-rest
     */
    public static void generateEnglish(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/messages";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        String mainName = FilePathUtils.getMainName(projectName);
        File projectFileErrorCode = new File(filePath+"/messages-"+mainName+".properties");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/messages/messages.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

    /**
     * 生成 中文语言配置 messages_zh_CN.properties
     * filePath = .......-rest
     */
    public static void generatezh_CN(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/messages";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        String mainName = FilePathUtils.getMainName(projectName);
        File projectFileErrorCode = new File(filePath+"/messages-"+mainName+"_zh_CN.properties");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/messages/messageszhCN.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

    /**
     * 生成 香港语言配置 messages_zh_HK.properties
     * filePath = .......-rest
     */
    public static void generatezh_HK(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/messages";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        String mainName = FilePathUtils.getMainName(projectName);
        File projectFileErrorCode = new File(filePath+"/messages-"+mainName+"_zh_HK.properties");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/messages/messageszhHK.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

    /**
     * 生成 台湾语言配置 messages_zh_HK.properties
     * filePath = .......-rest
     */
    public static void generatezh_TW(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/messages";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        String mainName = FilePathUtils.getMainName(projectName);
        File projectFileErrorCode = new File(filePath+"/messages-"+mainName+"_zh_TW.properties");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/messages/messageszhTW.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

}
