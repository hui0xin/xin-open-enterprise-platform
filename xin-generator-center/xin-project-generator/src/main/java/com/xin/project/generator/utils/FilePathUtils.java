package com.xin.project.generator.utils;


import java.io.File;

public class FilePathUtils {

    /**
     * 通过 项目名称 生成包
     * projectName 项目名称
     * 如：xin-file-uoplod ----》com/xin/file/uoplod/
     */
    public static String getPackPath(String projectName) throws Exception {
        StringBuffer result = new StringBuffer("com/");
        String[] split = projectName.split("-");
        for (int i=0;i<split.length;i++){
            result.append(split[i]);
            result.append("/");
        }
        return result.toString();
    }

    /**
     * 通过 项目名称 生成主类名称
     * projectName 项目名称
     * 如：xin-file-uoplod ----》FileUpload
     */
    public static String getClassName(String projectName) throws Exception {
        StringBuffer result = new StringBuffer();

        String[] split = projectName.split("-");
        for (int i=0;i<split.length;i++){
            if(i+1<split.length){
                String str = split[i+1];
                result.append(upperCase(str));
            }
        }
        return result.toString();
    }

    /**
     * 通过 项目名称 生成主类名称
     * projectName 项目名称
     * 如：xin-file-uoplod ----》fileUpload
     */
    public static String getClassNameFirst(String projectName) throws Exception {
        StringBuffer result = new StringBuffer();

        String[] split = projectName.split("-");
        for (int i=0;i<split.length;i++){
            if(i+1<split.length){
                String str = split[i+1];
                if(i==0){
                    result.append(str);
                }else{
                    result.append(upperCase(str));
                }
            }
        }
        return result.toString();
    }

    /**
     * 通过 项目名称 生成 包名
     * projectName 项目名称
     * 如：xin-file-upload ----》xin.file.upload
     */
    public static String getPackageName(String projectName) throws Exception {

        return projectName.replace("-",".");
    }

    /**
     * 通过 项目名称 生成 包名
     * projectName 项目名称
     * 如：xin-file-upload ----》file-upload-server
     */
    public static String getServerId(String projectName) throws Exception {
        StringBuffer result = new StringBuffer();
        String[] split = projectName.split("-");
        for (int i=0;i<split.length;i++){
            if(i+1<split.length){
                String str = split[i+1];
                result.append(str);
                result.append("-");
            }
        }
        return result.toString()+"server";
    }

    /**
     * 通过 项目名称 生成 包名
     * projectName 项目名称
     * 如：xin-file-upload ----》file-upload
     */
    public static String getMainName(String projectName) throws Exception {
        StringBuffer result = new StringBuffer();
        String[] split = projectName.split("-");
        for (int i=0;i<split.length;i++){
            if(i+1<split.length){
                String str = split[i+1];
                result.append(str);
            }
        }
        return result.toString();
    }

    /**
     * 字符串大写
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 删除指定文件夹下所有文件
     * param path 文件夹完整绝对路径
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     * folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception{
        System.out.println(getPackPath("xin-file-uoplod"));
        System.out.println(getClassName("xin-file-uoplod"));
        System.out.println(getClassNameFirst("xin-file-uoplod"));
        System.out.println(getServerId("xin-file-uoplod"));
    }
}
