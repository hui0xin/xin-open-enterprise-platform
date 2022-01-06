package com.xin.project.generator.standard.core;

import com.xin.project.generator.utils.FilePathUtils;
import com.xin.project.generator.utils.FreeMarkerTemplateUtils;
import com.xin.project.generator.utils.PathConstant;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 core 工程
 */
public class CoreProjectGenerateUtil {

    public static void generateCoreProject(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+"/"+projectName+"/"+projectName+"-core";
        //创建 项目 路径
        File projectFile = new File(filePath);
        //然后新建文件夹
        projectFile.mkdirs();
        //生成 pom.xml
        generatePom(filePath,projectName);
        System.out.println("    1, ----生成 pom.xml 文件成功");
        //生成 README.md
        generateReadMe(filePath,projectName);
        System.out.println("    2, ----生成 README.md 文件成功");
        //生成Service
        generateService(filePath,projectName);
        System.out.println("    3, ----生成 Service 成功");
        //生成serviceImpl
        generateServiceImpl(filePath,projectName);
        System.out.println("    4, ----生成 ServiceImpl 成功");
        //生成Mapper
        generateMapper(filePath,projectName);
        System.out.println("    5, ----生成 Mapper 成功");
        //生成Client
        generateClient(filePath,projectName);
        System.out.println("    6, ----生成 Client 成功");
    }

    /**
     * 生成pom.xml
     * filePath = .......-core
     */
    public static void generatePom(String filePath,String projectName) throws Exception {
        File mapperFile = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/core/pom.ftl");
        FileOutputStream fos = new FileOutputStream(mapperFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        template.process(dataMap, out);
    }

    /**
     * 生成 README.md
     */
    public static void generateReadMe(String filePath,String projectName) throws Exception {
        File readMeFile = new File(filePath+"/README.md");
        Template template = FreeMarkerTemplateUtils.getTemplate("/core/readme.ftl");
        FileOutputStream fos = new FileOutputStream(readMeFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

    /**
     * 生成 Service
     * filePath = .......-core
     */
    public static void generateService(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"core/service";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/DemoService.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/core/service.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 ServiceImpl
     * filePath = .......-core
     */
    public static void generateServiceImpl(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"core/service/impl";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/DemoServiceImpl.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/core/serviceImpl.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 Mapper
     * filePath = .......-core
     */
    public static void generateMapper(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"core/mapper";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/DemoMapper.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/core/mapper.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 Client
     * filePath = .......-core
     */
    public static void generateClient(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"core/client";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/DemoClient.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/core/client.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

}
