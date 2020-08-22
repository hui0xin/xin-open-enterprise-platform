spring.application.name=${serverId}

# port
server.port=9001
server.tomcat.max-threads=10
server.tomcat.max-connections=100

# eureka
eureka.client.serviceUrl.defaultZone=http://10.0.0.0:8761/eureka/,http://10.2.0.0:8762/eureka/
eureka.client.registryFetchIntervalSeconds=30
eureka.instance.leaseExpirationDurationInSeconds=60
eureka.instance.leaseRenewalIntervalInSeconds=30

# apollo config
apollo.meta=http://10.2.0.0:8080

#redis config
spring.redis.host=10.2.0.0
spring.redis.port=6379
spring.redis.password=123456
spring.redis.timeout=3000
spring.redis.lettuce.pool.max-active=20
spring.redis.lettuce.pool.max-wait=-1
spring.redis.lettuce.pool.max-idle=8
spring.redis.lettuce.pool.min-idle=0

# database
spring.datasource.url=jdbc:mysql://10.2.0.0:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.maximum-pool-size=15
spring.datasource.hikari.auto-commit=true
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.connection-test-query=SELECT 1

# swagger config
swagger.enable=true

