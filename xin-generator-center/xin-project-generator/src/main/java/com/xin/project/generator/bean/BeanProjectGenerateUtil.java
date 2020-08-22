package com.xin.project.generator.bean;

import com.xin.project.generator.utils.FilePathUtils;
import com.xin.project.generator.utils.FreeMarkerTemplateUtils;
import com.xin.project.generator.utils.PathConstant;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 bean工程
 */
public class BeanProjectGenerateUtil {

    public static void generateBeanProject(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+"/"+projectName+"/"+projectName+"-bean";
        //创建 项目 路径
        File projectFile = new File(filePath);
        projectFile.mkdirs();
        //生成 pom。xml
        generatePom(filePath,projectName);
        System.out.println("    1, ----生成 pom.xml 文件成功");
        //生成Bo
        generateBo(filePath,projectName);
        System.out.println("    2, ----生成 Bo 成功");
        //生成Vo
        generateVo(filePath,projectName);
        System.out.println("    3, ----生成 Vo 成功");
        //生成Do
        generateDo(filePath,projectName);
        System.out.println("    4, ----生成 Do 成功");
        //生成Do
        generateDto(filePath,projectName);
        System.out.println("    5, ----生成 dto 成功");

    }

    /**
     * 生成pom
     * filePath = .......-bean
     */
    public static void generatePom(String filePath,String projectName) throws Exception {
        File mapperFile = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/bean/pom.ftl");
        FileOutputStream fos = new FileOutputStream(mapperFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        template.process(dataMap, out);
    }

    /**
     * 生成 bo
     * filePath = .......-bean
     */
    public static void generateBo(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"bean/bo";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/DemoBo.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/bean/bo.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 vo
     * filePath = .......-bean
     */
    public static void generateVo(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"bean/vo";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/DemoVo.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/bean/vo.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }
    /**
     * 生成 DO
     */
    public static void generateDo(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"bean/DO";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/DemoDo.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/bean/do.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }
    /**
     * 生成 dto
     * filePath = .......-bean
     */
    public static void generateDto(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"bean/dto";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/DemoDto.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/bean/dto.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

}
