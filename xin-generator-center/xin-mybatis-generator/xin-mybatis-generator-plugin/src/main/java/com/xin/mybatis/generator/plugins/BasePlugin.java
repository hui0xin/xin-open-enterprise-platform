package com.xin.mybatis.generator.plugins;

import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @class: BasePlugin
 * @Description:  配置自动生成vo，service，mapper，impl，controller
 * @author: xin
 */
public class BasePlugin extends PluginAdapter {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 生成的xml是以覆盖方式，而不是追加
     * @param sqlMap
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        sqlMap.setMergeable(false);
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }


    @Override
    public boolean clientBasicCountMethodGenerated(Method var1, Interface var2, IntrospectedTable var3){

        return true;
    }

    /**
     * 拦截普通字段
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        classAnnotation(topLevelClass, remarks);
        Set<FullyQualifiedJavaType> set = new HashSet<FullyQualifiedJavaType>();
        set.add(new FullyQualifiedJavaType(Annotation.ApiModel.getClazz()));
        set.add(new FullyQualifiedJavaType(Annotation.DATA.getClazz()));
        topLevelClass.addImportedTypes(set);
        topLevelClass.addAnnotation(Annotation.DATA.getAnnotation());
        topLevelClass.addAnnotation(Annotation.ApiModel.getAnnotation() + "(value=\"" + topLevelClass.getType().getShortName() + "\",description=\"" + remarks + "\")");
        try {
            // 生成controller文件
            GenerateBeanUtils.generateControllerFile(topLevelClass, introspectedTable,properties);
            // 生成vo文件
            GenerateBeanUtils.generateVoFile(topLevelClass, introspectedTable,properties);
            // 生成service文件
            GenerateBeanUtils.generateServiceFile(topLevelClass, introspectedTable,properties);
            // 生成Impl文件
            GenerateBeanUtils.generateImplFile(topLevelClass, introspectedTable,properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 拦截 主键
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }


    /**
     * 拦截 blob 类型字段
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    /**
     * get方法 false 不生成
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * get方法 false 不生成
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }



    /**
     * 实体类字段
     *
     * @param field
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 生成注释
        fieldAnnotation(field, introspectedColumn.getRemarks());
        // 生成注释结束
        // 追加ApiModelProperty注解
        topLevelClass.addImportedType(new FullyQualifiedJavaType(Annotation.ApiModelProperty.getClazz()));
        field.addAnnotation(Annotation.ApiModelProperty.getAnnotation() + "(name=\"" + introspectedColumn.getJavaProperty() + "\", value=\"" + introspectedColumn.getRemarks() + "\")");
        // 追加日期格式化注解
        if (introspectedColumn.getJdbcTypeName() == "TIMESTAMP") {
            field.addAnnotation(Annotation.JsonFormat.getAnnotation() + "(pattern = \"yyyy-MM-dd HH:mm:ss\",timezone=\"GMT+8\")");
            topLevelClass.addImportedType(new FullyQualifiedJavaType(Annotation.JsonFormat.getClazz()));
        }
        // tinyint数据（Byte）转换成（Integer）类型
        String a = field.getType().getShortName();
        if (StringUtils.equals("Byte", a)) {
            field.setType(new FullyQualifiedJavaType("java.lang.Integer"));
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    /**
     * mapper接口方法
     * DeleteByPrimaryKey方法
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "根据主键删除数据");
        return super.clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }


    /**
     * mapper接口方法
     * Insert方法
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "插入数据库记录");
        return super.clientInsertMethodGenerated(method, interfaze, introspectedTable);
    }


    /**
     * mapper接口方法
     * InsertSelective方法
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "插入数据库记录");
        return super.clientInsertSelectiveMethodGenerated(method, interfaze, introspectedTable);
    }


    /**
     * mapper接口方法
     * SelectByPrimaryKey方法
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "根据主键id查询");
        return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }


    /**
     * mapper接口方法
     * UpdateByPrimaryKeySelective方法
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "修改数据");
        return super.clientUpdateByPrimaryKeySelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    /**
     * 修改mapper接口
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfazeAnnotation(interfaze, introspectedTable.getRemarks());
        Set<FullyQualifiedJavaType> set = new HashSet<FullyQualifiedJavaType>();
        set.add(new FullyQualifiedJavaType(Annotation.Mapper.getClazz()));
        interfaze.addImportedTypes(set);
        interfaze.addAnnotation(Annotation.Mapper.getAnnotation());
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    /**
     * 设置baseMapper
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     */
    private void setBaseMapper(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        String brt = introspectedTable.getBaseRecordType();
        String et = introspectedTable.getExampleType();
        //主键默认采用java.lang.Long
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseMapper<" +brt+ "," +et+ "," + "java.lang.Long" + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.xin.commons.support.base.BaseMapper");
        //添加 extends MybatisBaseMapper
        interfaze.addSuperInterface(fqjt);
        //添加import my.mabatis.example.base.MybatisBaseMapper;
        interfaze.addImportedType(imp);
    }
    /**
     * mapper接口方法
     * UUpdateByPrimaryKey方法
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        methodAnnotation(method, "修改数据");
        return super.clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }

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
