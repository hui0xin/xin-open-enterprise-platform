package com.xin.project.generator;

import com.xin.project.generator.bean.BeanProjectGenerateUtil;
import com.xin.project.generator.common.CommonProjectGenerateUtil;
import com.xin.project.generator.core.CoreProjectGenerateUtil;
import com.xin.project.generator.parent.ParentProjectGenerateUtil;
import com.xin.project.generator.rest.RestProjectGenerateUtil;
import com.xin.project.generator.sdk.SdkProjectGenerateUtil;

/**
 * 启动类生成
 * 会生成到
 */
public class GenerateStartUp {

    //生成项目地址
    public final static String filePath = "/Users/bixin/project";
    //项目名称
    public final static String  projectName = "xin-user";
    //项目说明
    public final static String  projectDesc = "用户服务";

    //是否是docker项目，true-->是，false-->否
    public final static Boolean isDockerProject = false;

    //项目配置文件结构:
    // (1) yaml 为纯yml配置，这种结构在启动的时候才确定环境，打出的jar都是一样的
    //（2）yaml_properties，为混合方式，这种结构在打包时候就确定了环境
    public final static String isYaml = "yaml";

    public static void main(String[] args) {

        try {
            //生成 parent 项目
            System.out.println("1, ----生成parent项目");
            ParentProjectGenerateUtil.generateParentProject(filePath,projectName,projectDesc);

            //生成 common 项目
            System.out.println("2, ----生成common项目");
            CommonProjectGenerateUtil.generateCommonProject(filePath,projectName,projectDesc);

            System.out.println("3, ----生成Bean项目.");
            BeanProjectGenerateUtil.generateBeanProject(filePath,projectName,projectDesc);

            System.out.println("4, ----生成Core项目.");
            CoreProjectGenerateUtil.generateCoreProject(filePath,projectName,projectDesc);

            System.out.println("6, ----生成Rest项目.");
            RestProjectGenerateUtil.generateRestProject(filePath,projectName,projectDesc,isDockerProject,isYaml);

            System.out.println("6, ----生成Sdk项目.");
            SdkProjectGenerateUtil.generateSdkProject(filePath,projectName,projectDesc);
            System.out.println("项目已将生成，请到 ：请到 ："+filePath+"路径下查看查看");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}