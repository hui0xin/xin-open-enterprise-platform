package com.xin.mybatis.generator.plugins.annotation;

import org.mybatis.generator.api.dom.java.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 为属性，字段，方法 ，类 添加注释
 * @author: xin
 */
public class Annotation {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 方法注释生成
     *
     * @param method
     * @param explain
     */
    public static void methodAnnotation(Method method, String explain) {
        // 生成注释
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(explain);
        method.addJavaDocLine(sb.toString());
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
        // 生成注释结束
    }


    /**
     * 属性注释生成
     *
     * @param field
     * @param explain
     */
    public static void fieldAnnotation(Field field, String explain) {
        // 生成注释
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(explain);
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
        // 生成注释结束
    }

    /**
     * 类注释生成
     *
     * @param topLevelClass
     * @param explain
     */
    public static void classAnnotation(TopLevelClass topLevelClass, String explain) {
        // 生成注释
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine("* @Class: "+ topLevelClass.getType().getShortName());
        topLevelClass.addJavaDocLine("* @Description: "+explain);
        topLevelClass.addJavaDocLine("* @author: 系统");
        topLevelClass.addJavaDocLine("* @created: " + df.format(new Date()));
        topLevelClass.addJavaDocLine("*/");
        // 生成注释结束
    }


    /**
     * 接口注释生成
     *
     * @param interfaze
     * @param explain
     */
    public static void interfazeAnnotation(Interface interfaze, String explain) {
        // 生成注释
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine("* @Interface: " + interfaze.getType().getShortName());
        interfaze.addJavaDocLine("* @Description: "+explain+" Mapper");
        interfaze.addJavaDocLine("* @author: 系统");
        interfaze.addJavaDocLine("* @created: " + df.format(new Date()));
        interfaze.addJavaDocLine("*/");
        // 生成注释结束
    }
}