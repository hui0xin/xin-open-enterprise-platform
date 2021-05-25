# 如果您觉得本项目对你有用，请随手star，谢谢
技术文章地址：https://www.jianshu.com/u/6ad247189165

# xin-mybatis-generator-web  
通过表结构来生成mapper，service，impl，controller，Mapperxml等

# 使用
## 第一步配置application.yaml   
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/security_oauth2?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: root
``` 
## 第二步配置 generator.properties
package=com.xin.user

## 第三步 启动服务 MybatisGeneratorApplication
浏览器输入：http://localhost:7777/generator/code 就可以生成了

## 注意：
这只是是个demo，需要按照公司的要求生成，只需要改 resources/template的模版就可以了，  
这里我对前端技术比较生疏，生成的代码不太好看，如果使用直接格式化，当然，如果你对 velocity 很熟悉的话，完全可以做到很好看  