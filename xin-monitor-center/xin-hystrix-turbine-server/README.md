# xin-hystrix-turbine-server 收集hystrix熔断信息，汇总到dashboard显示

#客户端的使用
###《1》加入jar
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-netflix-hystrix-stream</artifactId>
</dependency>
```
###《2》配置文件中加入以下配置，开启feigin断路器 新版本的fegin自带断路器
```
feign:
  hystrix:
    enabled: true
```   
###《3》主类加入 以下配置，开启hystrix
@EnableHystrix
