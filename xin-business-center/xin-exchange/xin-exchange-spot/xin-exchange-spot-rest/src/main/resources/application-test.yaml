server:
  port: 8991

## eureka 注册中心
eureka:
  client:
    serviceUrl:
      defaultZone: http://101.201.46.114:8761/eureka/,http://101.201.46.114:8762/eureka/,http://101.201.46.114:8763/eureka/

# database HikariCP相关配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/xxl-api?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: hxNX@123

---
## redis config
spring:
  redis:
    # Redis默认情况下有16个分片，这里配置具体使用的分片
    database: 0
    host: 101.201.46.114
    port: 6379
    password: 123456

## ------------------------------业务相关配置写到下面-------------------------
