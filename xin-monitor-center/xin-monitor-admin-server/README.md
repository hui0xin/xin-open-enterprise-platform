springboot服务监控系统（健康状态，失败后邮件发送等）

# 第一种 基于eureka
      
## 客户端 admin client
注意：这里其实不需要做任何操作，只需要在配置文件中加入 management.endpoints配置既可
```
<!--注册中心客户端 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<!-- spring-boot-starter-actuator 管理工具/web 查看堆栈，动态刷新配置 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
```
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS  
```       
如果具体客户端服务使用了
```
server:
  servlet:
    context-path: /sunflower
```

那么eureka的配置就需要是以下才能配置admin server监控到

```
eureka:
  client:
    serviceUrl:
      defaultZone: @eureka.client.serviceUrl.defaultZone@
    # 表示eureka client间隔多久去拉取服务注册信息，默认为30秒
    registry-fetch-interval-seconds: @eureka.client.registryFetchIntervalSeconds@
  instance:
    #表示eureka server至上一次收到client的心跳之后，等待下一次心跳的超时时间 默认90秒
    lease-expiration-duration-in-seconds: @eureka.instance.leaseExpirationDurationInSeconds@
    #表示eureka client发送心跳给server端的频率，即每30秒发送一次心跳 默认30秒
    lease-renewal-interval-in-seconds: @eureka.instance.leaseRenewalIntervalInSeconds@
    home-page-url-path: ${server.servlet.context-path}
    health-check-url-path: ${server.servlet.context-path}/actuator/health
    status-page-url-path: ${server.servlet.context-path}/actuator/info
    metadata-map:
      management:
        context-path: ${server.servlet.context-path}/actuator
```  
    
# 第二种 基于链接的
### 服务端
``` 
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
</dependency>
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-server-ui</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
``` 
``` 
@EnableAdminServer
``` 
### 客户端
``` 
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
    <version>2.0.1</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
``` 
``` 
spring:
  boot: 
    admin: 
      client: 
        url: http://localhost:8080
management:
  endpoints:
    web:
      exposure:
        include: "*"
``` 