## application name
spring:
  application:
    name: ${serverId}

server:
  port: 8999
  tomcat:
    uri-encoding: UTF-8
    # 最大线程数 #默认200
    max-threads: 200
    # 接受和处理的最大连接数 默认10000
    max-connections: 10000
    # 最大排队数 默认100
    accept-count: 100
    #默认10 初始化时创建的线程数 适当增大一些，以便应对突然增长的访问量100
    min-spare-threads: 50

---
# database HikariCP相关配置
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/xxl-api?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: hxNX@123
    hikari:
      minimum-idle: 5
      maximum-pool-size: 15
      auto-commit: true
      idle-timeout: 30000
      pool-name: DatebookHikariCP
      max-lifetime: 1800000
      connection-timeout: 30000
      connection-test-query: SELECT 1
    type: com.zaxxer.hikari.HikariDataSource

# MyBatis
mybatis:
  # 搜索指定包别名
  type-aliases-package: com.${packageName}.bean.DO
  # 配置mapper的扫描，找到所有的mapper.xml映射文件
  mapper-locations: classpath:mapper/*Mapper.xml
  # 加载全局的配置文件
  config_location: classpath:mybatis/mybatis-config.xml

---
#配置驼峰属性自动映射
mybatis:
  configuration:
    map-underscore-to-camel-case: true

# pagehelper
pagehelper:
  # 方言
  helperDialect: mysql
  # 分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页，
  # pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。
  reasonable: true
  # # 默认值false，分页插件会从查询方法的参数值中，自动根据上面 params 配置的字段中取值，查找到合适的值时就会自动分页。
  supportMethodsArguments: true
  # 用于从对象中根据属性名取值， 可以配置 pageNum,pageSize,count,pageSizeZero,reasonable，不配置映射的用默认值，
  # 默认值为pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero
  params: count=countSql
  returnPageInfo: check

# 允许/actuator/bus-refresh接口被外部调用
management:
  endpoints:
    web:
      exposure:
        # 开放监控内容
        include: "*"
  endpoint:
    shutdown:
      enabled: true #启用shutdown端点
    health:
      # health/detail 细节（）
      show-details: always


# swagger 开关 true为开启，false或没有为关闭
swagger:
  enable: true

## 日志配置
logging:
  config: classpath:logs/logback-spring.xml
  path: /Users/hx/logs
  level:
    # 全局日志级别
    default: INFO
    # 项目日志级别
    project: INFO
    # mybatis日志级别
    mybatis: DEBUG
---
#控制台输出彩色
spring:
  output:
    ansi:
      enabled: always
## ------------------------------业务相关配置写到下面-------------------------