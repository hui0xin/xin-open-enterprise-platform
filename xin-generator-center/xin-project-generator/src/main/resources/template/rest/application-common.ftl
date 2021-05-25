## 默认配置
spring:
  profiles:
    active: @env@

---
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
  # 最大的连接超时时间
  connection-timeout: 200
  servlet:
    context-path: /

# eureka 注册中心
eureka:
  client:
    serviceUrl:
      defaultZone: http://101.201.46.114:8761/eureka/,http://101.201.46.114:8762/eureka/,http://101.201.46.114:8763/eureka/
    # 表示eureka client间隔多久去拉取服务注册信息，默认为30秒
    registry-fetch-interval-seconds: 30
  instance:
    #表示eureka server至上一次收到client的心跳之后，等待下一次心跳的超时时间 默认90秒
    lease-expiration-duration-in-seconds: 60
    #表示eureka client发送心跳给server端的频率，即每30秒发送一次心跳 默认30秒
    lease-renewal-interval-in-seconds: 30
    home-page-url-path: ${r'${server.servlet.context-path}'};
    health-check-url-path:${r' ${server.servlet.context-path}'}/actuator/health
    status-page-url-path: ${r'${server.servlet.context-path}'}/actuator/info
    metadata-map:
      management:
        context-path: ${r'${server.servlet.context-path}'}/actuator

# apollo 配置
app:
  # 应用全局唯一的身份标识
  id: 100010012
apollo:
  ## Apollo Meta Server 地址
  meta: http://10.2.0.0:8080
  # 缓存路径，确保路径存在且有权限访问
  cacheDir: /data1/apollo/cache
  bootstrap:
    # 设置在应用启动阶段就加载 Apollo 配置
    enabled: true
    ## 注入 application namespace
    namespaces: application.yaml
    # 该配置将使 Apollo 在日志初始化前完成载入，如果需要请设置为 true， 否则设置为 false
    eagerLoad:
      enabled: false

---
# 多语言配置
spring:
  messages:
    # #指定message的basename，多个以逗号分隔，如果不加包名的话，默认从classpath路径开始，默认: messages
    basename: messages/messages-system,messages/messages-redis,messages/messages-lock,messages-order
    # 设置是否始终应用MessageFormat规则，甚至解析不带参数的消息
    always-use-message-format: true
    # #设定加载的资源文件缓存失效时间，-1的话为永不过期，默认为-1
    cache-seconds: 3600
    # 设定Message bundles的编码，默认: TF-8
    encoding: UTF-8
    # 设置如果找不到特定区域设置的文件，是否返回到系统区域设置。
    fallback-to-system-locale: false

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
  type-aliases-package: com.${packageName}
  # 配置mapper的扫描，找到所有的mapper.xml映射文件
  #  mapper-locations: classpath:mapper/*Mapper.xml
  # 加载全局的配置文件
  config_location: classpath:mybatis/mybatis-config.xml

#mybatis:
#  #配置驼峰属性自动映射
#  configuration:
#    map-underscore-to-camel-case: true

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

---
# redis config
spring:
  redis:
    # Redis默认情况下有16个分片，这里配置具体使用的分片
    database: 0
    host: 101.201.46.114
    port: 6379
    # 连接超时时间（毫秒）
    timeout: 3000
    password: 123456
      #集群配置
      #cluster:
      #nodes: #192.168.211.134:7000,192.168.211.134:7001
      #max-redirects: 5
      #哨兵配置
      #sentinel:
    #master: master1
    #nodes: # 172.16.33.216:16001,172.16.33.216:16002
    lettuce:
      pool:
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: 20
        # 连接池中的最大空闲连接
        max-idle: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: -1
        # 连接池中的最小空闲连接
        min-idle: 0

---
#上传 文件限制
spring:
  servlet:
    multipart:
      enabled: true
      #单个数据的大小 可以使用MB活着kb 默认是1MB
      max-file-size: 10MB
      #总数据的大小 可以使用MB活着kb 默认是10MB
      #设置上传文件的临时目录
      #location :
      max-request-size: 100MB
      #设置文件缓存的临界点,超过则先保存到临时目录 值可以使用后缀“mb”或“kb” 默认是0
      file-size-threshold: 0
      #是否在文件或参数访问时延迟解析多部分请求。默认是fasle
      resolve-lazily: false

#ribbon请求连接的超时时间- 限制3秒内必须请求到服务，并不限制服务处理的返回时间
#ribbonTimeout = (ribbonReadTimeout + ribbonConnectTimeout) * (maxAutoRetries + 1) * (maxAutoRetriesNextServer + 1);
#ribbonTimeout = (15000 + 3000) * (1 + 0) * (1 + 1) = 36000
# ribbon config
ribbon:
  #请求连接超时时间
  ConnectTimeout: 3000
  #请求处理的超时时间
  ReadTimeout: 6000
  #对当前实例的重试次数
  MaxAutoRetries: 0
  #切换实例的重试次数 1
  MaxAutoRetriesNextServer: 1
  #ServerList缓存时间
  ServerListRefreshInterval: 10000


hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            #熔断超时时间
            timeoutInMilliseconds: 10000
        timeout:
          #是否启用超时时间
          enabled: true

#开启断路器 新版本的fegin自带断路器
feign:
  hystrix:
    enabled: true

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
  level:
    # 全局日志级别
    default: INFO
    # 项目日志级别
    commons: INFO
    # mybatis日志级别
    mybatis: DEBUG
---
#控制台输出彩色
spring:
  output:
    ansi:
      enabled: always
## ------------------------------业务相关配置写到下面-------------------------