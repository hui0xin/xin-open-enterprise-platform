package com.xin.mybatis.generator.plugins.annotation;

/**
 * @enum: 导入包的配置
 * @author: xin
 */
public enum ImportAnnotation {

    DATA("@Data", "lombok.Data"),
    Mapper("@Mapper", "org.apache.ibatis.annotations.Mapper"),
    Param("@Param", "org.apache.ibatis.annotations.Param"),
    ApiModel("@ApiModel", "io.swagger.annotations.ApiModel"),
    ApiModelProperty("@ApiModelProperty", "io.swagger.annotations.ApiModelProperty"),
    Length("@Length", "org.hibernate.validator.constraints.Length"),
    JsonFormat("@JsonFormat", "com.fasterxml.jackson.annotation.JsonFormat");

    private String annotation;
    private String clazz;

    ImportAnnotation(String annotation, String clazz) {
        this.annotation = annotation;
        this.clazz = clazz;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getClazz() {
        return clazz;
    }
}