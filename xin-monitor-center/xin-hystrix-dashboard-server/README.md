#xin-hystrix-dashboard-server springboot hystrix 监控

###dev 环境 http://0.0.0.0:7061/hystrix

##通过 Hystrix Dashboard 主页面的文字介绍，我们可以知道，Hystrix Dashboard 共支持三种不同的监控方式：

开启，实现对默认集群的监控。
##《1》默认的集群监控：通过 URL：http://turbine-hostname:port/turbine.stream 

开启，实现对 clusterName 集群的监控。
##《2》指定的集群监控：通过 URL：http://turbine-hostname:port/turbine.stream?cluster=[clusterName] 

开启 ，实现对具体某个服务实例的监控。
##《3》单体应用的监控：通过 URL：http://hystrix-app:port/hystrix.stream 

现在这里的 URL 应该为 http://hystrix-app:port/actuator/hystrix.stream
Actuator 2.x 以后  endpoints 全部在/actuator下，可以通过management.endpoints.web.base-path修改

##页面上的另外两个参数：
Delay：控制服务器上轮询监控信息的延迟时间，默认为 2000 毫秒，可以通过配置该属性来降低客户端的网络和 CPU 消耗。
Title：该参数可以展示合适的标题。

http://1.0.0.0:7081/hystrix.stream
http://1.0.0.0:7081/turbine.stream  
http://1.0.0.0:7081/turbine.stream?cluster=[clusterName] 



通过mq聚合：
服务将（metrics信息）-------》mq-----------》turbine处理----------》dashboard 展示

