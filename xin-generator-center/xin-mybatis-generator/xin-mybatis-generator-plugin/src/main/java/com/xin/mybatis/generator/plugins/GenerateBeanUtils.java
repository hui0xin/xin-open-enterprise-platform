package com.xin.mybatis.generator.plugins;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置自动生成 vo，
 * service，
 * mapper，
 * impl，
 * controller
 */
public class GenerateBeanUtils {

    /**
     * 生成controller
     * @param topLevelClass
     * @param introspectedTable
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateControllerFile(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, Properties properties) throws IOException, TemplateException {

        String packageController = properties.getProperty("controller");
        if (packageController != null) {
            String packageService = properties.getProperty("service");
            String packageDomain = topLevelClass.getType().getPackageName();
            String[] mulu = properties.getProperty("controller").split("\\.");
            String moduleName = mulu[mulu.length - 1];
            String fileDoName = topLevelClass.getType().getShortName();
            String fileName = generatorFileName(fileDoName);
            String path = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetProject() + "/" + properties.getProperty("controller").replaceAll("\\.", "/");
            File catalog = new File(path);
            catalog.mkdirs();
            File mapperFile = new File(path + '/' + fileName + "Controller.java");
            Template template = FreeMarkerTemplateUtils.getTemplate("controller.ftl");
            FileOutputStream fos = new FileOutputStream(mapperFile);
            Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("packageController", packageController);
            dataMap.put("packageService", packageService);
            dataMap.put("moduleName", moduleName);
            dataMap.put("moduleDesc", introspectedTable.getRemarks());
            dataMap.put("fileName", fileName);
            dataMap.put("fileDoName", fileDoName);
            dataMap.put("date", new Date());
            dataMap.put("packageDomain", packageDomain);
            template.process(dataMap, out);
        }
    }

    /**
     * 生成service
     * @param topLevelClass
     * @param introspectedTable
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateServiceFile(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,Properties properties) throws IOException, TemplateException {
        String packageService = properties.getProperty("service");
        if (packageService != null) {
            String packageDomain = topLevelClass.getType().getPackageName();
            String[] mulu = properties.getProperty("service").split("\\.");
            String moduleName = mulu[mulu.length - 1];
            String fileDoName = topLevelClass.getType().getShortName();
            String fileName = generatorFileName(fileDoName);
            String path = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetProject() + "/" + properties.getProperty("service").replaceAll("\\.", "/");
            File catalog = new File(path);
            catalog.mkdirs();
            File mapperFile = new File(path + '/' + fileName + "Service.java");
            Template template = FreeMarkerTemplateUtils.getTemplate("service.ftl");
            FileOutputStream fos = new FileOutputStream(mapperFile);
            Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("packageService", packageService);
            dataMap.put("moduleName", moduleName);
            dataMap.put("packageDomain", packageDomain);
            dataMap.put("fileName", fileName);
            dataMap.put("fileDoName", fileDoName);
            dataMap.put("date", new Date());
            dataMap.put("moduleDesc", introspectedTable.getRemarks());
            template.process(dataMap, out);
        }
    }

    /**
     * 生成实现类
     * @param topLevelClass
     * @param introspectedTable
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateImplFile(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,Properties properties) throws IOException, TemplateException {
        String packageImpl = properties.getProperty("impl");
        if (packageImpl != null) {
            String packageService = properties.getProperty("service");
            String packageDomain = topLevelClass.getType().getPackageName();
            String packageMapper = introspectedTable.getContext().getJavaClientGeneratorConfiguration().getTargetPackage();
            String fileDoName = topLevelClass.getType().getShortName();
            String fileName = generatorFileName(fileDoName);
            String path = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetProject() + "/" + properties.getProperty("impl").replaceAll("\\.", "/");
            File catalog = new File(path);
            catalog.mkdirs();
            File mapperFile = new File(path + '/' + fileName + "ServiceImpl.java");
            Template template = FreeMarkerTemplateUtils.getTemplate("serviceImpl.ftl");
            FileOutputStream fos = new FileOutputStream(mapperFile);
            Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("packageImpl", packageImpl);
            dataMap.put("packageService", packageService);
            dataMap.put("packageMapper", packageMapper);
            dataMap.put("fileName", fileName);
            dataMap.put("fileDoName", fileDoName);
            dataMap.put("packageDomain", packageDomain);
            dataMap.put("moduleDesc", introspectedTable.getRemarks());
            dataMap.put("date", new Date());
            template.process(dataMap, out);
        }
    }

    /**
     * 生成vo类
     * @param topLevelClass
     * @param introspectedTable
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateVoFile(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,Properties properties) throws IOException, TemplateException {
        String packageVo = properties.getProperty("vo");
        if (packageVo != null) {
            String packageDomain = topLevelClass.getType().getPackageName();
            String fileDoName = topLevelClass.getType().getShortName();
            String fileName = generatorFileName(fileDoName);
            String path = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetProject() + "/" + properties.getProperty("vo").replaceAll("\\.", "/");
            File catalog = new File(path);
            catalog.mkdirs();
            File mapperFile = new File(path + '/' + fileName + "Vo.java");
            Template template = FreeMarkerTemplateUtils.getTemplate("vo.ftl");
            FileOutputStream fos = new FileOutputStream(mapperFile);
            Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("packageVo", packageVo);
            dataMap.put("packageDomain", packageDomain);
            dataMap.put("moduleDesc", introspectedTable.getRemarks());
            dataMap.put("fileName", fileName);
            dataMap.put("fileDoName", fileDoName);
            dataMap.put("date", new Date());
            template.process(dataMap, out);
        }
    }

    /**
     * 处理 fileName 如果后面有Do就去掉
     * @param fileName
     * @return
     */
    private static String generatorFileName(String fileName){
        String result = fileName.replace("Do","").replace("DO","");
        return result;
    }

}
