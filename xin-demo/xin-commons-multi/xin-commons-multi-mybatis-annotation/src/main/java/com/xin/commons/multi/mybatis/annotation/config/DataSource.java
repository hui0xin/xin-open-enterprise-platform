package com.xin.commons.multi.mybatis.annotation.config;

import java.lang.annotation.*;

/**
    @Target(ElementType.TYPE) //接口、类、枚举、注解
    @Target(ElementType.FIELD) //字段、枚举的常量
    @Target(ElementType.METHOD) //方法
    @Target(ElementType.PARAMETER) //方法参数
    @Target(ElementType.CONSTRUCTOR) //构造函数
    @Target(ElementType.LOCAL_VARIABLE)//局部变量
    @Target(ElementType.ANNOTATION_TYPE)//注解
    @Target(ElementType.PACKAGE) ///包
    @Retention(RetentionPolicy.SOURCE) //注解仅存在于源码中，在class字节码文件中不包含
    @Retention(RetentionPolicy.CLASS) //默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
    @Retention(RetentionPolicy.RUNTIME)//注解会在class字节码文件中存在，在运行时可以通过反射获取到
    @Documented注解表明这个注释是由 javadoc记录的，在默认情况下也有类似的记录工具。
    如果一个类型声明被注释了文档化，它的注释成为公共API的一部分。

 * 自定义注解
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DBConfig value() default DBConfig.dataSource1;
}
