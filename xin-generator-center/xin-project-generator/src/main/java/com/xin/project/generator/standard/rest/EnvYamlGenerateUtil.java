package com.xin.project.generator.standard.rest;

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
public class EnvYamlGenerateUtil {

    /**
     * 生成 简单的yaml文件
     */
    public static void generateSimpleYaml(String filePath,String projectName) throws Exception {
        //生成 application.ftl
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/application.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/simple/application.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }


    /**
     * 生成 config
     * filePath = .......-rest
     */
    public static void generateEnvYaml(String filePath,String projectName,String projectDesc) throws Exception {
        //生成 application.ftl
        generate(filePath,projectName,projectDesc);
        //生成 application-local.ftl
        generateLocal(filePath,projectName,projectDesc);
        //生成 application-dev.ftl
        generateDev(filePath,projectName,projectDesc);
        //生成 application-test.ftl
        generateTest(filePath,projectName,projectDesc);
        //生成 application-prod.ftl
        generateProd(filePath,projectName,projectDesc);
    }


    /**
     * 生成 application.ftl
     * filePath = .......-rest
     */
    public static void generate(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/application.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/application-common.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 application-local.ftl
     * filePath = .......-rest
     */
    public static void generateLocal(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/application-local.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/application-local.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 application-dev.ftl
     * filePath = .......-rest
     */
    public static void generateDev(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/application-dev.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/application-dev.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 application-test.ftl
     * filePath = .......-rest
     */
    public static void generateTest(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/application-test.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/application-test.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 application-prod.ftl
     * filePath = .......-rest
     */
    public static void generateProd(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/application-prod.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/application-prod.ftl");
        String serverId = FilePathUtils.getServerId(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("serverId", serverId);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }


}
