 # 如果您觉得本项目对你有用，请随手star，谢谢
 技术文章地址：https://www.jianshu.com/u/6ad247189165
 
 # xin-api-gateway
 基于springcloud gateway 实现了的网关
 
 1，基于apollo作为动态配置
 
 2，解决从body获取数据补全的问题
 
 3，通过redis实现限流
 
 4，重定向
 
 1. 用户进入网关开始登陆，网关过滤器进行判断，如果是登录，则路由到后台管理微服务进行登录
 2. 用户登录成功，后台管理微服务签发JWT TOKEN信息返回给用户
 3. 用户再次进入网关开始访问，网关过滤器接收用户携带的TOKEN 
 4. 网关过滤器解析TOKEN ，判断是否有权限，如果有，则放行，如果没有则返回未认证错误

网关解决的问题
 * 网关服务
 * 协议转换，路由转发
 * 流量聚合，对流量进行监控，日志输出
 * 作为整个系统的前端工程，对流量进行控制，有限流的作用
 * 作为系统的前端边界，外部流量只能通过网关才能访问系统
 * 可以在网关层做权限的判断
 * 可以在网关层做缓存



###以下为规则，有不会的可以参考
Route Predicate Factory
id uri顾名思义，不多说，但predicates是什么呢？predicates做动词有使基于; 
使以…为依据; 表明; 阐明; 断言;的意思，简单说，用于表明在那种条件下，该路由配置生效。

spring:
  cloud:
    gateway:
      routes:
      - id: example
        uri: http://example.org
        predicates:
        # 匹配在什么时间之后的
        - After=2017-01-20T17:42:47.789-07:00[America/Denver]
        # 匹配在什么时间之前的
        - Before=2017-01-20T17:42:47.789-07:00[America/Denver]
        # 匹配在某段时间的
        - Between=2017-01-20T17:42:47.789-07:00[America/Denver], 2017-01-21T17:42:47.789-07:00[America/Denver]
        # 匹配cookie名称为`chocolate`的值要符合`ch.p`正则.
        - Cookie=chocolate, ch.p
        # 匹配header为`X-Request-Id`的值要符合`\d+`正则.
        - Header=X-Request-Id, \d+
        # 匹配任意符合`**.somehost.org`与`**.anotherhost.org`正则的网址
        - Host=**.somehost.org,**.anotherhost.org
        # Host还支持模版变量，会保存在`ServerWebExchange.getAttributes()`的 ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE中，以Map形式存储
        - Host={sub}.myhost.org
        # 匹配GET方法
        - Method=GET
        # 路径匹配，与Host一样支持模版变量，存在URI_TEMPLATE_VARIABLES_ATTRIBUTE中。
        - Path=/foo/{segment},/bar/{segment}
        # 匹配存在baz查询参数
        - Query=baz
        # 匹配存在foo且符合`ba.`正则
        - Query=foo, ba.
        # 匹配远程地址
        - RemoteAddr=192.168.1.1/24

GatewayFilter Factory
除此predicates外，还有filter，用于过滤请求。与predicates一样，
Spring官方也提供了需要内置的过滤器。过滤器部分相对于predicates来说难得多，
有全局的也有可配置的。甚至一些过滤器不支持通过配置文件来修改。

spring:
  cloud:
    gateway:
      routes:
      - id: example
        uri: http://example.org
        filters:
          # 先介绍简单的路由器
          # 对header的操作（添加，删除，设置），以及保留原始host的header
          - AddRequestHeader=X-Request-Foo, Bar
          - AddResponseHeader=X-Response-Foo, Bar
          - RemoveRequestHeader=X-Request-Foo
          - RemoveResponseHeader=X-Response-Foo
          - RewriteResponseHeader=X-Response-Foo, , password=[^&]+, password=***
          - SetResponseHeader=X-Response-Foo, Bar
          - PreserveHostHeader
          # 对查询参数的过滤
          - AddRequestParameter=foo, bar
          # 对Uri的过滤（path与status）
          - PrefixPath=/mypath
          - RewritePath=/foo/(?<segment>.*), /$\{segment}
          - SetPath=/{segment}
          - StripPrefix=2
          - SetStatus=BAD_REQUEST
          - SetStatus=401
          - RedirectTo=302, http://acme.org
          # 保留session，默认情况下是不保留的
          - SaveSession
          # 设置最大请求数据大小，这里发现一种新写法，理论上predicates中也能使用
          - name: RequestSize
            args:
              maxSize: 5000000
          # 重试次数设置
          - name: Retry
            args:
              retries: 3
              statuses: BAD_GATEWAY
        
          # 断路器的配置
          # 断路器的配置比较复杂，首先指定断路器命令名即可启用断路器（这块我也不熟，需要HystrixCommand的内容）
          - Hystrix=myCommandName
          # 另外我们可以设置些错误发生后的跳转，当然现在仅支持forward:
          - name: Hystrix
            args:
              name: fallbackcmd
              fallbackUri: forward:/incaseoffailureusethis
          # 我们还可以修改错误信息放置的header
          - name: FallbackHeaders
            args:
              executionExceptionTypeHeaderName: Test-Header
              executionExceptionMessageHeaderName: Test-Header
              rootCauseExceptionTypeHeaderName: Test-Header
              rootCauseExceptionMessageHeaderName: Test-Header

           # 另一块比较困难的是速率限制
          # 它由RateLimiter的实现来完成的，默认只支持redis，需要添加`spring-boot-starter-data-redis-reactive`依赖
          # 我们需要提供KeyResolver的实现，因为默认会使用PrincipalNameKeyResolver，在不使用Spring Security的情况下几乎不会用到Principal
          # 除此外，我们也可以提供自定义的RateLimiter，#{@myRateLimiter}是一个SpEL表达式，用于从Spring上下文内读取bean
          - name: RequestRateLimiter
            args:
              redis-rate-limiter.replenishRate: 10
              redis-rate-limiter.burstCapacity: 20
          - name: RequestRateLimiter
            args:
              rate-limiter: "#{@myRateLimiter}"
              key-resolver: "#{@userKeyResolver}"

@Bean
KeyResolver userKeyResolver() {
    return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
}
除此外还有两个“特别”的过滤器，modifyRequestBody modifyResponseBody他们只能使用在Fluent Java Routes API中。例如：
@Bean
public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("rewrite_request_obj", r -> r.host("*.rewriterequestobj.org")
            .filters(f -> f.prefixPath("/httpbin")
                .modifyRequestBody(String.class, Hello.class, MediaType.APPLICATION_JSON_VALUE,
                    (exchange, s) -> return Mono.just(new Hello(s.toUpperCase())))).uri(uri))
        .build();
}

PrefixPath Filter 在请求路径前加上自定义的路径
假如应用访问地址是localhost:8001/app, 
接口地址是/test，这里设置了prefixPath为/app, 
那么当你访问localhost:8080/test, 网关在帮你转发请求之前，
会在/test 前加上/app，转发时的请求就变成了localhost:8001/app/test。

spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: http://localhost:8001
        predicates:
        - Path=/{path}
        filters:
        - PrefixPath=/app
        
        
        
RewritePath Filter 重写请求路径
spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: http://localhost:8001
        predicates:
        - Path=/{path}
        filters:
        # 访问localhost:8080/test, 请求会转发到localhost:8001/app/test
        - RewritePath=/test, /app/test        
         
              
这个filter比较灵活的就是可以进行正则匹配替换，
如下的例子就是当请求localhost:8080/test时，
匹配所有以/开头的路径，然后在前面加上/app,
所以现在请求变成了localhost:8080/app/test。
然后转发时的url变成了localhost:8001/app/test 。
在测试的时候，这个filter是没办法使用模板进行匹配的。可能是因为它是用的正则进行匹配替换，所以没办法使用模板吧

spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: http://localhost:8001
        predicates:
        - Path=/test
        filters:
        - RewritePath=(?<oldPath>^/), /app$\{oldPath}      
        
值得注意的是在yml文档中 $ 要写成 $\ 。替换路径是使用的是String.replaceAll()方法，这个方法和replace()不同，是根据正则进行替换的。具体的替换规则感兴趣的话可以去了解一下Pattern。
https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html


SetPath Filter 通过模板设置路径
这个filter的使用方式比较简单。就是匹配到满足/a开头的路径后重新设置路径为以/app开头。

spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: http://localhost:8001
        predicates:
        - Path=/a/{path}
        filters:
        - SetPath=/app/{path}
        
        

