# 配置相关

## 1 上传 文件限制
```
spring:
  servlet:
    multipart:
      enabled: true
      #单个数据的大小 可以使用MB活着kb 默认是1MB
      max-file-size: 10Mb
      #总数据的大小 可以使用MB活着kb 默认是10MB
      #设置上传文件的临时目录
      #location :
      max-request-size: 100Mb
      #设置文件缓存的临界点,超过则先保存到临时目录 值可以使用后缀“mb”或“kb” 默认是0
      file-size-threshold: 0
      #是否在文件或参数访问时延迟解析多部分请求。默认是fasle
      resolve-lazily: false
```

# 开启断路器 新版本的fegin自带断路器
```
feign:
  hystrix:
    enabled: true
  client:
    config:
      #默认 超时时间
      default:
        connect-timeout: 3000
        read-timeout: 15000
#      # activity 超时时间
#      activity:
#        connect-timeout: 10000
#        read-timeout: 20000
```

##该参数用来开启重试机制，默认是关闭
```
spring:
  cloud:
    loadbalancer:
      retry:
        enabled: true
```

# 如果你在zuul配置了熔断fallback的话，熔断超时也要配置，上传大文件得将超时时间设置长一些，否则会报超时异常。
```
hystrix:
  command:
    # default 默认所有的设置，这里可以是具体的服务 例如：activity
    default:
      execution:
        isolation:
          strategy: THREAD #隔离策略是Thread
          thread:
            #默认=1000 断路器的超时时间需要大于ribbon的超时时间，不然不会触发重试
            timeoutInMilliseconds: 38000
            #发生超时是是否中断，默认true
            interruptOnTimeout: true
        timeout:
          # 执行是否启用超时
          enabled: true
#      circuitBreaker:
#        #当在配置时间窗口内达到此数量的失败后，进行短路。默认20个）
#        requestVolumeThreshold: 20
#        #短路多久以后开始尝试是否恢复，默认5s
#        sleepWindowInMilliseconds: 5000
#        #出错百分比阈值，当达到此阈值后，开始短路。默认50%
#        errorThresholdPercentage: 50
#  threadpool:
#    default:
#      #并发执行的最大线程数，默认10
#      coreSize: 100
#      #BlockingQueue的最大队列数，当设为－1，会使用SynchronousQueue，值为正时使用LinkedBlcokingQueue。
#      #该设置只会在初始化时有效，之后不能修改threadpool的queue size，除非reinitialising thread executor。默认－1。
#      maxQueueSize: -1
#      #即使maxQueueSize没有达到，达到queueSizeRejectionThreshold该值后，请求也会被拒绝。
#      #因为maxQueueSize不能被动态修改，这个参数将允许我们动态设置该值。if maxQueueSize == -1，该字段将不起作用
#      queueSizeRejectionThreshold: -1
#      #如果corePoolSize和maxPoolSize设成一样（默认实现）该设置无效。
#      #如果通过plugin（https://github.com/Netflix/Hystrix/wiki/Plugins）使用自定义实现，该设置才有用，默认1.
#      keepAliveTimeMinutes: 1
```

# ribbon config 负载均衡配置
```
ribbon:
#  httpclient:
#    # 关闭 httpclient 支持
#    enabled: false
#  okhttp:
#    # 开启 okhttp 支持
#    enabled: true
  #ribbon请求连接的超时时间- 限制3秒内必须请求到服务，并不限制服务处理的返回时间
  #ribbonTimeout = (ribbonReadTimeout + ribbonConnectTimeout) * (maxAutoRetries + 1) * (maxAutoRetriesNextServer + 1);
  #ribbonTimeout = (15000 + 3000) * (1 + 0) * (1 + 1) = 36000
  ConnectTimeout: 3000
  #请求处理的超时时间 下级服务响应最大时间,超出时间消费方（路由也是消费方）返回timeout
  ReadTimeout: 15000
  # 是否开启重试 对所有操作请求都进行重试
#  OkToRetryOnAllOperations: true
  # 重试期间，切换实例的重试次数
  MaxAutoRetriesNextServer: 1
  # 当前实例重试次数
  MaxAutoRetries: 0
  #负载策略
  NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
  eureka:
    #false将明确禁用在Ribbon中使用Eureka
    enabled: true
```
```
#根据如上配置，当访问到故障请求的时候，它会再尝试访问一次当前实例（次数由MaxAutoRetries配置），
#如果不行，就换一个实例进行访问，如果还是不行，再换一次实例访问（更换次数由MaxAutoRetriesNextServer配置），
#如果依然不行，返回失败信息。

#配置具体服务ribbon负载均衡策略 这里的配置要优先于 全局配置
# 单独设置某个服务的超时时间，会覆盖其他的超时时间限制，服务的名称已注册中心页面显示的名称为准，超时时间不可大于断路器的超时时间
#activity:
#  ribbon:
##    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule #随机
##    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule #轮询
##    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RetryRule # 重试
##    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.WeightedResponseTimeRule # 响应时间权重
#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.BestAvailableRule # 最空闲连接策略
#    ConnectTimeout: 1500 #请求连接超时时间
#    ReadTimeout: 2000 #请求处理的超时时间
#    OkToRetryOnAllOperations: true #对所有请求都进行重试
#    MaxAutoRetriesNextServer: 2 #切换实例的重试次数
#    MaxAutoRetries: 1 #对当前实例的重试次数
```

## 邮件发送配置
```
#spring:
#  mail:
#    # 邮箱服务器
#    host: smtp.exmail.qq.com
#    # 发送者的邮箱账号
#    username: ptyy-alerm-sender@xinoauto.com
#    # 密码
#    password: Ptyy.a1000000
#    # 字符集
#    default-encoding: UTF-8
#    # 表示开启 DEBUG 模式，这样，邮件发送过程的日志会在控制台打印出来，方便排查错误
#    debug: true
#    smtp:
#      # 需要验证登录名和密码 默认是true
#      auth: true
#      port: 465
#      ssl:
#        #开启ssl加密 否则项目启动时报530error
#        enable: true
#        socketFactory: sf
#      starttls:
#        #需要TLS认证 保证发送邮件安全验证
#        enable: true
#        required: true

```
# port
```
server:
  port: @server.port@
  tomcat:
    uri-encoding: UTF-8
    # 最大线程数
    max-threads: @server.tomcat.max-threads@
    # 最大连接数
    max-connections: @server.tomcat.max-connections@
  servlet:
    context-path: /sunflower
```
# eureka 注册中心
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
#java7
```
-Xms: 初始化堆大小。-Xmx: 最大堆大小。最好将初始堆大小和最大堆大小设置一样大，-Xmn设置年轻代大小
-XX:PermSize	设置持久代(perm gen)初始值-XX:MaxPermSize	设置持久代最大值
java -jar -Xmx512M -Xms512M -Xmn128M -XX:PermSize=128M -XX:MaxPermSize=128M test.jar
```
# java8
```
-Xms: 初始化堆大小。-Xmx: 最大堆大小。最好将初始堆大小和最大堆大小设置一样大，-Xmn设置年轻代大小
-XX:MetaspaceSize=128M 设置源空间初始值 -XX:MaxMetaspaceSize=128M 设置源空间最大值 
java -jar -Xmx512M -Xms512M -Xmn128M -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=128M test.jar
```

```
//@Data
//@NoArgsConstructor
//public class TestVo<T> extends BaseVO {
//
//    private String name;
//
//    public TestVo(String name, List<T> list){
//        super(list);
//        this.name = name;
//    }
//    public static void main(String[] args) {
//        List list =new ArrayList();
//        list.add("aaaaa");
//
//        TestVo testVo = new TestVo("bbbb",list);
//
//        System.out.println(JSONObject.toJSONString(testVo));
//
//
//        Map<String,Long> map = new HashMap<>();
//    }
//
//}
```
# mybatis peizhi 
```
当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。
mybatis.configuration.aggressive-lazy-loading=true
允许JDBC 生成主键。需要驱动器支持。如果设为了true，这个设置将强制使用被生成的主键，有一些驱动器不兼容不过仍然可以执行。 default:false
mybatis.configuration.use-generated-keys=true
指定 MyBatis 如何自动映射 数据基表的列 NONE：不隐射\u3000PARTIAL:部分 FULL:全部
mybatis.configuration.auto-mapping-behavior=partial
设置本地缓存范围 session:就会有数据的共享 statement:语句范围 (这样就不会有数据的共享 ) defalut:session
mybatis.configuration.local-cache-scope=session
设置但JDBC类型为空时,某些驱动程序 要指定值,default:OTHER，插入空值时不需要指定类型
mybatis.configuration.jdbc-type-for-null=null
如果数据为空的字段，则该字段省略不显示，可以通过添加配置文件，规定查询数据为空是则返回null。
mybatis.configuration.call-setters-on-nulls=true
```