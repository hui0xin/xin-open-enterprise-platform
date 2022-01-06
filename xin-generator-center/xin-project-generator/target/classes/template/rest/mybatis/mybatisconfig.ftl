<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!--使全局的映射器启用或禁用缓存。-->
        <setting name="cacheEnabled" value="true"/>
        <!--全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。-->
        <setting name="lazyLoadingEnabled" value="true"/>
        <!--是否允许单条sql 返回多个数据集 (取决于驱动的兼容性) default:true-->
        <setting name="multipleResultSetsEnabled" value="true"/>
        <!--是否可以使用列的别名 (取决于驱动的兼容性) default:true-->
        <setting name="useColumnLabel" value="true"/>
        <!--使用驼峰命名法转换字段。-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <!--这是默认的执行类型 （SIMPLE: 简单； REUSE: 执行器可能重复使用prepared statements语句；BATCH: 执行器可以重复执行语句和批量更新）-->
        <setting name="defaultExecutorType" value="REUSE"/>
        <!---->
        <setting name="defaultStatementTimeout" value="25000"/>
    </settings>
</configuration>


