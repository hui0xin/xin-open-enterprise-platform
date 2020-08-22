package com.xin.project.generator.parent;

import com.xin.project.generator.utils.FreeMarkerTemplateUtils;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 主项目，parent 项目
 */
public class ParentProjectGenerateUtil {

    public static void generateParentProject(String filePath,String projectName,String projectDesc) throws Exception {
        //创建 项目 路径
        File projectFile = new File(filePath+"/"+projectName);
        projectFile.mkdirs();
        //生成 pom。xml
        generatePom(filePath,projectName,projectDesc);
        System.out.println("    1, ----生成 pom.xml 文件成功");
        //生成README.md
        generateReadme(filePath,projectName,projectDesc);
        System.out.println("    2, ----生成 README.md 文件成功");
    }

    /**
     * 生成pom
     */
    public static void generatePom(String filePath,String projectName,String projectDesc) throws Exception {
        File prjectFilePom = new File(filePath+"/"+projectName+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/parent/pom.ftl");
        FileOutputStream fos = new FileOutputStream(prjectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        template.process(dataMap, out);
    }

    /**
     * 生成 README.md
     */
    public static void generateReadme(String filePath,String projectName,String projectDesc) throws Exception {
        File prjectFilereadMe = new File(filePath+"/"+projectName+"/README.md");
        Template template = FreeMarkerTemplateUtils.getTemplate("/parent/readme.ftl");
        FileOutputStream fos = new FileOutputStream(prjectFilereadMe);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        template.process(dataMap, out);
    }

}
