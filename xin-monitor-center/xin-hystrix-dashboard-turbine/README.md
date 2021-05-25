# xin-hystrix-dashboard-turbine
将turbine汇总和dashboard展示汇总到一起

# 客户端的使用
### 《1》加入jar
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```
注意，必须配置rabbitmq 

### 《2》配置文件中加入以下配置，开启feigin断路器 新版本的fegin自带断路器  
```
feign:
  hystrix:
    enabled: true
```

1.断路器机制  
```
断路器很好理解, 当Hystrix Command请求后端服务失败数量超过一定比例(默认50%), 断路器会切换到开路状态(Open). 
这时所有请求会直接失败而不会发送到后端服务. 断路器保持在开路状态一段时间后(默认5秒), 
自动切换到半开路状态(HALF-OPEN). 这时会判断下一次请求的返回情况, 如果请求成功, 断路器切回闭路状态(CLOSED), 
否则重新切换到开路状态(OPEN). Hystrix的断路器就像我们家庭电路中的保险丝, 一旦后端服务不可用, 
断路器会直接切断请求链, 避免发送大量无效请求影响系统吞吐量, 并且断路器有自我检测并恢复的能力.
```
2.Fallback
```
Fallback相当于是降级操作. 对于查询操作, 我们可以实现一个fallback方法, 当请求后端服务出现异常的时候, 
可以使用fallback方法返回的值. fallback方法的返回值一般是设置的默认值或者来自缓存.
```
3.资源隔离
```
在Hystrix中, 主要通过线程池来实现资源隔离. 通常在使用的时候我们会根据调用的远程服务划分出多个线程池. 
例如调用产品服务的Command放入A线程池, 调用账户服务的Command放入B线程池. 这样做的主要优点是运行环境被隔离开了. 
这样就算调用服务的代码存在bug或者由于其他原因导致自己所在线程池被耗尽时, 不会对系统的其他服务造成影响. 
但是带来的代价就是维护多个线程池会对系统带来额外的性能开销. 如果是对性能有严格要求而且确信自己调用服务的客户端代码不会出问题的话, 
可以使用Hystrix的信号模式(Semaphores)来隔离资源
```
```
开启，实现对默认集群的监控。
《1》默认的集群监控：通过 URL：http://turbine-hostname:port/turbine.stream 

开启，实现对 clusterName 集群的监控。
《2》指定的集群监控：通过 URL：http://turbine-hostname:port/turbine.stream?cluster=[clusterName] 

开启 ，实现对具体某个服务实例的监控。
《3》单体应用的监控：通过 URL：http://hystrix-app:port/hystrix.stream 

现在这里的 URL 应该为 http://hystrix-app:port/actuator/hystrix.stream
Actuator 2.x 以后  endpoints 全部在/actuator下，可以通过management.endpoints.web.base-path修改
```
## 页面上的另外两个参数：
Delay：控制服务器上轮询监控信息的延迟时间，默认为 2000 毫秒，可以通过配置该属性来降低客户端的网络和 CPU 消耗。  
Title：该参数可以展示合适的标题。  

```
http://1.0.0.0:7081/hystrix.stream
http://1.0.0.0:7081/turbine.stream  
http://1.0.0.0:7081/turbine.stream?cluster=[clusterName] 
```

通过mq聚合：
服务将（metrics信息）-------》mq-----------》turbine处理----------》dashboard 展示



