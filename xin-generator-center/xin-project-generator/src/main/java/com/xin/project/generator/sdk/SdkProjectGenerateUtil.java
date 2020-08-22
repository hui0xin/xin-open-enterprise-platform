package com.xin.project.generator.sdk;

import com.xin.project.generator.utils.FilePathUtils;
import com.xin.project.generator.utils.FreeMarkerTemplateUtils;
import com.xin.project.generator.utils.PathConstant;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 sdk
 * 提供给其他客户端的sdk
 */
public class SdkProjectGenerateUtil {

    public static void generateSdkProject(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+"/"+projectName+"/"+projectName+"-sdk";
        //创建 项目 路径
        File projectFile = new File(filePath);
        //然后新建文件夹
        projectFile.mkdirs();
        //生成 pom。xml
        generatePom(filePath,projectName);
        System.out.println("    1, ----生成 pom.xml 文件成功");
        //生成sdk
        generateSdk(filePath,projectName,projectDesc);
        System.out.println("    2, ----生成 client 成功");
    }

    /**
     * 生成pom
     */
    public static void generatePom(String filePath,String projectName) throws Exception {
        File mapperFile = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/sdk/pom.ftl");
        FileOutputStream fos = new FileOutputStream(mapperFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        template.process(dataMap, out);
    }

    /**
     * 生成 Sdk
     */
    public static void generateSdk(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"sdk";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        String className = FilePathUtils.getClassName(projectName);
        File projectFileErrorCode = new File(filePath+"/"+className+"Client.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/sdk/client.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        String serverId = FilePathUtils.getServerId(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        dataMap.put("serverId", serverId);
        dataMap.put("className", className);
        template.process(dataMap, out);
    }

}
