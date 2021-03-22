package com.xin.mybatis.generator.plugins;

import com.xin.mybatis.generator.plugins.annotation.Annotation;
import com.xin.mybatis.generator.plugins.annotation.ImportAnnotation;
import com.xin.mybatis.generator.plugins.custom.CustomAbstractXmlElementGenerator;
import com.xin.mybatis.generator.plugins.custom.CustomJavaMapperMethodGenerator;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.io.*;
import java.util.*;

/**
 * @class: BasePlugin
 * @Description:  配置自动生成vo，service，mapper，impl，controller
 * @author: xin
 */
public class BasePlugin extends PluginAdapter {

    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 生成的xml是以覆盖方式，而不是追加
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
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        Annotation.classAnnotation(topLevelClass, remarks);
        Set<FullyQualifiedJavaType> set = new HashSet<FullyQualifiedJavaType>();
        set.add(new FullyQualifiedJavaType(ImportAnnotation.ApiModel.getClazz()));
        set.add(new FullyQualifiedJavaType(ImportAnnotation.DATA.getClazz()));
        topLevelClass.addImportedTypes(set);
        topLevelClass.addAnnotation(ImportAnnotation.DATA.getAnnotation());
        topLevelClass.addAnnotation(ImportAnnotation.ApiModel.getAnnotation() + "(value=\"" + topLevelClass.getType().getShortName() + "\",description=\"" + remarks + "\")");
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
     * @return false 不生成
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * get方法 false 不生成
     * @return false 不生成
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 实体类字段
     * @return
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 生成注释
        Annotation.fieldAnnotation(field, introspectedColumn.getRemarks());
        // 生成注释结束
        // 追加ApiModelProperty注解
        topLevelClass.addImportedType(new FullyQualifiedJavaType(ImportAnnotation.ApiModelProperty.getClazz()));
        field.addAnnotation(ImportAnnotation.ApiModelProperty.getAnnotation() + "(name=\"" + introspectedColumn.getJavaProperty() + "\", value=\"" + introspectedColumn.getRemarks() + "\")");
        // 追加日期格式化注解
        if (introspectedColumn.getJdbcTypeName() == "TIMESTAMP") {
            field.addAnnotation(ImportAnnotation.JsonFormat.getAnnotation() + "(pattern = \"yyyy-MM-dd HH:mm:ss\",timezone=\"GMT+8\")");
            topLevelClass.addImportedType(new FullyQualifiedJavaType(ImportAnnotation.JsonFormat.getClazz()));
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
     * Insert方法 插入数据
     * @return
     */
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        Annotation.methodAnnotation(method, "插入数据");
        return super.clientInsertMethodGenerated(method, interfaze, introspectedTable);
    }

    /**
     * mapper接口方法
     * InsertSelective方法
     * @return false 禁止生成该方法
     */
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * mapper xml接口方法
     * InsertSelective方法
     * @return false 禁止生成该方法
     */
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * mapper接口方法
     * deleteByPrimaryKey方法 根据主键删除数据
     * @return
     */
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        Annotation.methodAnnotation(method, "根据主键删除数据");
        return super.clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    /**
     * mapper接口方法
     * UpdateByPrimaryKeySelective方法
     * @return
     */
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        Annotation.methodAnnotation(method, "修改数据");
        return super.clientUpdateByPrimaryKeySelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    /**
     * mapper接口方法
     * UpdateByPrimaryKey方法
     * @return false 禁止生成
     */
    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * mapper xml 接口方法
     * UpdateByPrimaryKey方法
     * @return false 禁止生成
     */
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * mapper接口方法
     * selectByPrimaryKey方法 根据主键id查询
     * @return
     */
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        if(("selectByPrimaryKey").equals(method.getName())){
            Annotation.methodAnnotation(method, "根据主键id查询");
        }else if(("selectByObject").equals(method.getName())){
            Annotation.methodAnnotation(method, "根据参数查询对象");
        }else if(("listByObject").equals(method.getName())){
            Annotation.methodAnnotation(method, "根据参数查询列表");
        }
        return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    /**
     * 为mapper添加新方法
     * @return
     */
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        AbstractJavaMapperMethodGenerator methodGenerator = new CustomJavaMapperMethodGenerator();
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.addInterfaceElements(interfaze);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);

    }

    /**
     * 为mapper.xml 添加新方法
     * @param document
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        AbstractXmlElementGenerator elementGenerator = new CustomAbstractXmlElementGenerator();
        elementGenerator.setContext(context);
        elementGenerator.setIntrospectedTable(introspectedTable);
        elementGenerator.addElements(document.getRootElement());
        return super.sqlMapDocumentGenerated(document, introspectedTable);
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

}
