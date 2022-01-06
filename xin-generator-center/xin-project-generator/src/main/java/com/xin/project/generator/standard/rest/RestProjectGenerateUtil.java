package com.xin.project.generator.standard.rest;

import com.xin.project.generator.utils.FilePathUtils;
import com.xin.project.generator.utils.FreeMarkerTemplateUtils;
import com.xin.project.generator.utils.PathConstant;
import freemarker.template.Template;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 rest 工程，
 */
public class RestProjectGenerateUtil {

    public static void generateRestProject(String filePath,String projectName,String projectDesc,Boolean isDockerProject,String isYaml) throws Exception {
        filePath = filePath+"/"+projectName+"/"+projectName+"-rest";
        //创建 项目 路径
        File projectFile = new File(filePath);
        //然后新建文件夹
        projectFile.mkdirs();

        if(isDockerProject){
            if(isYaml.equals("yaml_properties")){
                //生成 docker 项目 pom。xml
                generateDockerPom(filePath,projectName);
                System.out.println("    1, ----生成 pom.xml 文件成功");
            }else if(isYaml.equals("yaml")){
                //生成 docker 项目 pom。xml
                generateYamlDockerPom(filePath,projectName);
                System.out.println("    1, ----生成 pom.xml 文件成功");
            }else{
                System.out.println("    1, ----生成 pom.xml 文件成功");
                return;
            }
        }else{
            if(isYaml.equals("yaml_properties")){
                //生成 普通项目 pom。xml
                generateBasePom(filePath,projectName);
                System.out.println("    1, ----生成 pom.xml 文件成功");
            }else if(isYaml.equals("yaml")){
                //生成 普通项目 pom。xml
                generateYamlBasePom(filePath,projectName);
                System.out.println("    1, ----生成 pom.xml 文件成功");
            }else{
                System.out.println("    1, ----生成 pom.xml 文件成功");
                return;
            }
        }

        //生成mybatis-config.xml
        generateMybatisConfigXml(filePath,projectName,projectDesc);
        System.out.println("    2, ----生成 mybatis-config.xml 文件成功");

        //生成logback-spring.xml
        generateLogbackSpringXml(filePath,projectName,projectDesc);
        System.out.println("    3, ----生成 logback-spring.xml 文件成功");

        //生成bootstrap.yaml
        if(isYaml.equals("yaml_properties")){
            generateBootstrapYaml(filePath,projectName,projectDesc);
            System.out.println("    4, ----生成 bootstrap.yaml 文件成功");
        }

        // (1) yaml 为纯yml配置，这种结构在启动的时候才确定环境，打出的jar都是一样的
        //（2）yaml_properties，为混合方式，这种结构在打包时候就确定了环境
        if(isYaml.equals("yaml_properties")){
            //生成application.yaml
            generateApplicationYaml(filePath,projectName,projectDesc);
            System.out.println("    5, ----生成 application.yaml 文件成功");
            ConfigGenerateUtil.generateConfig(filePath,projectName,projectDesc);
            System.out.println("    6, ----生成 config配置 文件成功");
        }else if(isYaml.equals("yaml")){
            EnvYamlGenerateUtil.generateEnvYaml(filePath,projectName,projectDesc);
            System.out.println("    6, ----生成 多个环境的yaml 文件成功");
        }else{
            System.out.println("    6, ----生成 多个环境的yaml 失败");
            return;
        }

        //生成message
        MessageGenerateUtil.generateMessages(filePath,projectName);
        System.out.println("    7, ----生成 message 文件成功");

        if(isDockerProject){
            //生成DockerFile
            generateDockerFile(filePath,projectName,projectDesc);
            System.out.println("    8, ----生成 DockerFile 文件成功");
        }

        //生成main启动类
        generateStartMain(filePath,projectName,projectDesc);
        System.out.println("    9, ----生成 启动类 文件成功");

        //生成controller类
        generateController(filePath,projectName,projectDesc);
        System.out.println("    10, ----生成 controller 文件成功");

        //生成swagger类
        generateSwagger(filePath,projectName,projectDesc);
        System.out.println("    11, ----生成 swagger 文件成功");

        //生成ThreadPoolTaskConfig类
        generateThreadPoolTaskConfig(filePath,projectName,projectDesc);
        System.out.println("    12, ----生成 ThreadPoolTaskConfig 文件成功");

    }

    /**
     * 生成普通pom.xml
     * filePath = .......-rest
     */
    public static void generateBasePom(String filePath,String projectName) throws Exception {
        File projectFilePom = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/pom-base.ftl");
        FileOutputStream fos = new FileOutputStream(projectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        dataMap.put("projectName", projectName);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成普通pom.xml
     * filePath = .......-rest
     */
    public static void generateYamlBasePom(String filePath,String projectName) throws Exception {
        File projectFilePom = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/pom-yaml-base.ftl");
        FileOutputStream fos = new FileOutputStream(projectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        dataMap.put("projectName", projectName);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成docker项目pom.xml
     * filePath = .......-rest
     */
    public static void generateDockerPom(String filePath,String projectName) throws Exception {
        File projectFilePom = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/pom-docker.ftl");
        FileOutputStream fos = new FileOutputStream(projectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        dataMap.put("projectName", projectName);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成docker项目pom.xml
     * filePath = .......-rest
     */
    public static void generateYamlDockerPom(String filePath,String projectName) throws Exception {
        File projectFilePom = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/pom-yaml-docker.ftl");
        FileOutputStream fos = new FileOutputStream(projectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        dataMap.put("projectName", projectName);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 mybatis-config.xml
     * filePath = .......-rest
     */
    public static void generateMybatisConfigXml(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/mybatis";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/mybatis-config.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/mybatis/mybatisconfig.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

    /**
     * 生成 logback-spring.xml
     * filePath = .......-rest
     */
    public static void generateLogbackSpringXml(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/logs";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/logback-spring.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/logs/logbackspring.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

    /**
     * 生成 bootstrap.yaml
     * filePath = .......-rest
     */
    public static void generateBootstrapYaml(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/bootstrap.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/bootstrap.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

    /**
     * 生成 application.yaml
     * filePath = .......-rest
     */
    public static void generateApplicationYaml(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/application.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/application.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        String mainName = FilePathUtils.getMainName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        dataMap.put("mainName", mainName);
        template.process(dataMap, out);
    }

    /**
     * 生成 DockerFile
     * filePath = .......-rest
     */
    public static void generateDockerFile(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.DOCKERPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/Dockerfile");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/docker/dockerfile.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        template.process(dataMap, out);
    }

    /**
     * 生成 StartMain
     * filePath = .......-rest
     */
    public static void generateStartMain(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName);
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+FilePathUtils.getClassName(projectName)+"Application.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/java/main.ftl");
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 swagger
     * filePath = .......-rest
     */
    public static void generateSwagger(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"config/";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"SwaggerConfig.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/java/swagger.ftl");
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 线程池配置
     * filePath = .......-rest
     */
    public static void generateThreadPoolTaskConfig(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"config/";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"ThreadPoolTaskConfig.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/java/threadpooltaskconfig.ftl");
        String className = FilePathUtils.getClassNameFirst(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 Controller
     * filePath = .......-rest
     */
    public static void generateController(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"controller/";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"DemoController.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/java/controller.ftl");
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

}
