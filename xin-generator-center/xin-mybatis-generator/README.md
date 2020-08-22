# 如果您觉得本项目对你有用，请随手star，谢谢
技术文章地址：https://www.jianshu.com/u/6ad247189165

# xin-mybatis-generator
公司用来生成MyBatis Generator插件: 
可以自动生成: DAO, DO, servcie，controller，Mapper xml or annotation
当然你也可以自己定制符合自己的插件，需要修改 BasePlugin类和template包下的ftl

# xin-mybatis-generator-starter 为具体生成代码的主要工程，修改
generator-config.properties 中数据库配置，存储位置配置
mybatis-generator.xml 添加 需要生成的表名称

# 执行 
xin-mybatis-generator-starter --> 
Plugins --> mybatis-generator ---> mybatis-generator:generate

