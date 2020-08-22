package com.xin.project.generator.rest;

import com.xin.project.generator.utils.FilePathUtils;
import com.xin.project.generator.utils.FreeMarkerTemplateUtils;
import com.xin.project.generator.utils.PathConstant;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 config 工具类，
 */
public class ConfigGenerateUtil {

    /**
     * 生成 config
     * filePath = .......-rest
     */
    public static void generateConfig(String filePath,String projectName,String projectDesc) throws Exception {
        //生成local.properties
        generateLocal(filePath,projectName,projectDesc);
        //生成dev.properties
        generateDev(filePath,projectName,projectDesc);
        //生成test.properties
        generateTest(filePath,projectName,projectDesc);
        //生成prod.properties
        generateProd(filePath,projectName,projectDesc);
    }

    /**
     * 生成 local.properties
     * filePath = .......-rest
     */
    public static void generateLocal(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/config";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/local.properties");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/config/local.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        template.process(dataMap, out);
    }

    /**
     * 生成 dev.properties
     * filePath = .......-rest
     */
    public static void generateDev(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/config";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/dev.properties");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/config/dev.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        template.process(dataMap, out);
    }

    /**
     * 生成 test.properties
     * filePath = .......-rest
     */
    public static void generateTest(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/config";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/test.properties");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/config/test.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        template.process(dataMap, out);
    }

    /**
     * 生成 prod.properties
     * filePath = .......-rest
     */
    public static void generateProd(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/config";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/prod.properties");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/config/prod.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        template.process(dataMap, out);
    }


}
