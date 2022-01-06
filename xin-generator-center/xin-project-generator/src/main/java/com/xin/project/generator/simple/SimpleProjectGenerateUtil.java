package com.xin.project.generator.simple;

import com.xin.project.generator.standard.bean.BeanProjectGenerateUtil;
import com.xin.project.generator.standard.core.CoreProjectGenerateUtil;
import com.xin.project.generator.standard.rest.EnvYamlGenerateUtil;
import com.xin.project.generator.standard.rest.RestProjectGenerateUtil;
import com.xin.project.generator.utils.FilePathUtils;
import com.xin.project.generator.utils.FreeMarkerTemplateUtils;
import freemarker.template.Template;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 简单 工程，
 */
public class SimpleProjectGenerateUtil {

    public static void generateSimpleProject(String filePath,String projectName,String projectDesc,Boolean isMultiEnv) throws Exception {
        filePath = filePath+"/"+projectName;

        //创建 项目 路径
        File projectFile = new File(filePath);
        projectFile.mkdirs();

        //生成简单pom.xml
        generateSimplePom(filePath,projectName,projectDesc);
        System.out.println("    1, ----生成 pom.xml 文件成功");

        //生成简单readme
        generateSimpleReadme(filePath,projectName,projectDesc);
        System.out.println("    2, ----生成 readme 文件成功");

        if(isMultiEnv){
            EnvYamlGenerateUtil.generateEnvYaml(filePath,projectName,projectDesc);
            System.out.println("    3, ----生成 多个环境的yaml 文件成功");
        }else{
            //生成简单的 配置 yaml
            EnvYamlGenerateUtil.generateSimpleYaml(filePath,projectName);
            System.out.println("    3, ----生成 application.yaml 文件成功");
        }

        //生成mybatis-config.xml
        RestProjectGenerateUtil.generateMybatisConfigXml(filePath,projectName,projectDesc);
        System.out.println("    4, ----生成 mybatis-config.xml 文件成功");

        //生成logback-spring.xml
        RestProjectGenerateUtil.generateLogbackSpringXml(filePath,projectName,projectDesc);
        System.out.println("    5, ----生成 logback-spring.xml 文件成功");

        //生成main启动类
        RestProjectGenerateUtil.generateStartMain(filePath,projectName,projectDesc);
        System.out.println("    6, ----生成 启动类 文件成功");

        //生成Bo
        BeanProjectGenerateUtil.generateBo(filePath,projectName);
        System.out.println("    7, ----生成 Bo 成功");

        //生成Vo
        BeanProjectGenerateUtil.generateVo(filePath,projectName);
        System.out.println("    8, ----生成 Vo 成功");

        //生成Do
        BeanProjectGenerateUtil.generateDo(filePath,projectName);
        System.out.println("    9, ----生成 Do 成功");

        //生成Do
        BeanProjectGenerateUtil.generateDto(filePath,projectName);
        System.out.println("    10, ----生成 Dto 成功");

        //生成controller类
        RestProjectGenerateUtil.generateController(filePath,projectName,projectDesc);
        System.out.println("    11, ----生成 controller 文件成功");

        //生成Service
        CoreProjectGenerateUtil.generateService(filePath,projectName);
        System.out.println("    12, ----生成 Service 成功");

        //生成serviceImpl
        CoreProjectGenerateUtil.generateServiceImpl(filePath,projectName);
        System.out.println("    13, ----生成 ServiceImpl 成功");

        //生成Mapper
        CoreProjectGenerateUtil.generateMapper(filePath,projectName);
        System.out.println("    14, ----生成 Mapper 成功");

        //生成swagger类
        RestProjectGenerateUtil.generateSwagger(filePath,projectName,projectDesc);
        System.out.println("    15, ----生成 swagger 文件成功");

    }

    /**
     * 生成简单项目pom.xml
     */
    public static void generateSimplePom(String filePath,String projectName,String projectDesc) throws Exception {
        File prjectFilePom = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/simple/pom.ftl");
        FileOutputStream fos = new FileOutputStream(prjectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        dataMap.put("projectName", projectName);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        dataMap.put("projectDesc", projectDesc);
        template.process(dataMap, out);
    }

    /**
     * 生成 README.md
     */
    public static void generateSimpleReadme(String filePath, String projectName, String projectDesc) throws Exception {
        File prjectFilereadMe = new File(filePath + "/README.md");
        Template template = FreeMarkerTemplateUtils.getTemplate("/simple/readme.ftl");
        FileOutputStream fos = new FileOutputStream(prjectFilereadMe);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        template.process(dataMap, out);
    }

}
