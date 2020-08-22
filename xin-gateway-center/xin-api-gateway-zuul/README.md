# 如果您觉得本项目对你有用，请随手star，谢谢
技术文章地址：https://www.jianshu.com/u/6ad247189165

# xin-api-gateway-zuul
基于zuul 实现了的网关

## 0，查看现在的路由列表
http://ip:port/routes

##1,Zuul的核心是一系列的过滤器，这些过滤器可以完成以下功能：
1、身份认证与安全：识别每个资源的验证要求，并拒绝那些与要求不符的请求。
2、审查与监控：在边缘位置追踪有意义的数据和统计结果，从而带来精确的生产视图。
3、动态路由：动态地将请求路由到不同的后端集群。
4、压力测试：逐渐增加指向集群的流量，以了解性能。
5、负载分配：为每一种负载类型分配对应容量，并启用超出限定值的请求。
6、静态响应处理：在边缘位置直接建立部分相应，从而避免其转发到内部集群。

##2,apigateway 结合oauth2.0使用步骤
1.用户请求某个资源前，需要先通过api网关访问Oauth2认证授权服务请求一个AccessToken
2.用户通过认证授权服务得到AccessToken后，通过api网关调用其他资源服务A、B、C
3.资源服务根据AccessToken从OAuth2认证授权服务验证该token的用户请求是否有效


## 3，Zuul的四种过滤器API：
前置（Pre）
路由（Route）
后置（Post）
错误（Error）
zuul前后置过滤器的典型应用场景：
前置（Pre）
限流
鉴权
参数校验调整
后置（Post）
统计
日志

#### application.yaml 配置

#Zuul：Cookie和动态路由
#我们在web开发中，经常会利用到cookie来保存用户的登录标识。
#但我们使用了zuul组件后，默认情况下，cookie是无法直接传递给服务的，
#因为cookie默认被列为敏感的headers。所以我们需要在配置文件中，将sensitiveHeaders的值置空。如下：
#zuul:
#  ...
#  routes:
#    myProduct:
#      path: /myProduct/**
#      serviceId: product
#      sensitiveHeaders:  # 置空该属性的值即可
# 统一的路由前缀

#zuul.routes.<key>.url=<url>指定一个服务的url或者使用forward转向Zuul服务的接口，对应路由路径为zuul.routes.<key>.path 
users2:
  path: /userApi2/**
  url: http://localhost:8002
 
使用zuul.routes.<routeName>.stripPrefix=false在向服务发起请求时不会去掉path前缀，
即http://localhost:8301/sms会代理到sms-service服务的/sms接口（
如果stripPrefix设置为true我们需要使用http://localhost:8301/sms/sms才能正常访问到这个接口）。  
sms2:
  service-id: sms-service
  path: /sms/**
  stripPrefix: false
  
forward将请求转发至本地处理（http://localhost:8301/forward/test）会将请求转发至本地的/myZuul/test接口。 
forward:
  path: /forward/**
  url: forward:/myZuul
  
zuul.routes.<ribbon>=<path>使用自定义Ribbon实现路由  
service-by-ribbon: /service-by-ribbon/**


## 路由规则
## 传统路由配置：不依赖服务发现。
## 所有以myapi开头的url路由至http://127.0.0.1:2000/下
## 如http://127.0.0.1:8888/myapi/hello --> http://127.0.0.1:2000/hello
zuul.routes.myApi.path=/myapi/**
zuul.routes.myApi.url=http://127.0.0.1:2000


# 多实例
zuul.routes.server-provide.path=/user-service/**
zuul.routes.server-provide.serviceId=user-service
ribbon.eureka.enabled=false
server-provide.ribbon.listOfServers=http://127.0.0.1:1001/,http://127.0.0.1:1001/


#如果系统zuul出现http://ip:port这一类的路由规则的时候，需要以下配置
zuul:
  host:
    #代理普通http请求的超时时间
    socket-timeout-millis: 6000
    connect-timeout-millis: 3000
    # 每个服务的http客户端连接池最大连接，默认是200.
    max-total-connections: 2000
    # 每个route可用的最大连接数，默认值是20。
    max-per-route-connections: 200


# 以下是几种 demo
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @program: api-gateway
// * @description: 买家权限过滤器
// * @author: 01
// * @create: 2018-08-25 17:03
// **/
//@Component
//public class AuthBuyerFilter extends ZuulFilter {
//
//    private static final String ORDER_CREATE = "/order/buyer/order/create";
//
//    @Override
//    public String filterType() {
//        // 声明过滤器的类型为Pre
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        // 将这个过滤器的优先级放在 PRE_DECORATION_FILTER_ORDER 之前
//        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        // 从上下文中拿到请求对象
//        HttpServletRequest request = requestContext.getRequest();
//
//        // 如果访问的是 ORDER_CREATE 则进行拦截，否则不进行拦截
//        return ORDER_CREATE.equals(request.getRequestURI());
//    }
//
//    /**
//     * 这个方法用于自定义过滤器的处理代码
//     *
//     * @return Object
//     * @throws ZuulException ZuulException
//     */
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        // 从上下文中拿到请求对象
//        HttpServletRequest request = requestContext.getRequest();
//
//        // /buyer/order/create 只能买家访问 （cookie里有openid）
//        Cookie cookie = CookieUtil.get(request, "openid");
//        if (cookie == null || StringUtils.isBlank(cookie.getValue())) {
//            requestContext.setSendZuulResponse(false);
//            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//        }
//
//        return null;
//    }
//}



//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.zero.springcloud.apigateway.constant.RedisConstant;
//import org.zero.springcloud.apigateway.utils.CookieUtil;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @program: api-gateway
// * @description: 卖家权限过滤器
// * @author: 01
// * @create: 2018-08-25 17:03
// **/
//@Component
//public class AuthSellerFilter extends ZuulFilter {
//
//    private final StringRedisTemplate redisTemplate;
//    private static final String ORDER_FINISH = "/order/buyer/order/finish";
//
//    @Autowired
//    public AuthSellerFilter(StringRedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public String filterType() {
//        // 声明过滤器的类型为Pre
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        // 将这个过滤器的优先级放在 PRE_DECORATION_FILTER_ORDER 之前
//        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        // 从上下文中拿到请求对象
//        HttpServletRequest request = requestContext.getRequest();
//
//        // 如果访问的是 ORDER_FINISH 则进行拦截，否则不进行拦截
//        return ORDER_FINISH.equals(request.getRequestURI());
//    }
//
//    /**
//     * 这个方法用于自定义过滤器的处理代码
//     *
//     * @return Object
//     * @throws ZuulException ZuulException
//     */
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        // 从上下文中拿到请求对象
//        HttpServletRequest request = requestContext.getRequest();
//
//        // /buyer/order/finish 只能卖家访问 （cookie里有token，并且redis存储了session数据）
//        if (ORDER_FINISH.equals(request.getRequestURI())) {
//            Cookie cookie = CookieUtil.get(request, "token");
//            if (cookie == null ||
//                    StringUtils.isBlank(cookie.getValue()) ||
//                    StringUtils.isNotBlank(redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue())))) {
//                requestContext.setSendZuulResponse(false);
//                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//
//        return null;
//    }
//}




//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Data
//@Component
//@ConfigurationProperties(prefix="myProps")
//public class MyProps {
//
//private String simpleProp;
//private String[] arrayProps;
//private List<Map<String, String>> listProp1 = new ArrayList<>(); //接收prop1里面的属性值
//private List<String> listProp2 = new ArrayList<>(); //接收prop2里面的属性值
//private Map<String, String> mapProps = new HashMap<>(); //接收prop1里面的属性值
//  
//
//myProps: #自定义的属性和值
//  simpleProp: simplePropValue
//  arrayProps: 1,2,3,4,5
//  listProp1:
//    - name: abc
//      value: abcValue
//    - name: efg
//      value: efgValue
//  interfaces:
//    - config2Value1
//    - config2Vavlue2
//  mapProps:
//    key1: value1
//    key2: value2
//
//
//
//
//}




#ribbo负载均衡策略配置，默认是依次轮询
API-USER-SERVER.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule

@HystrixCommand(fallbackMethod = "findError") //如果请求失败或超时
public String find() {
    String s = restTemplate.getForEntity("http://API-USER-SERVER/user/find/123", String.class).getBody();  
}


 	    线程池隔离	             信号量隔离
线程	    与调用线程非相同线程	     与调用线程相同（jetty线程）
开销	    排队、调度、上下文开销等	 无线程切换，开销低
异步	    支持	                     不支持
并发支持	支持（最大线程池大小）	     支持（最大信号量上限）

Execution相关的属性的配置：


hystrix.command.default.execution.isolation.strategy=THREAD 隔离策略，默认是Thread, 可选THREAD，SEMAPHORE
thread 通过线程数量来限制并发请求数，可以提供额外的保护，但有一定的延迟。一般用于网络调用
semaphore 通过semaphore count来限制并发请求数，适用于无网络的高并发请求

hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=2000 命令执行超时时间，默认1000ms
hystrix.command.default.execution.timeout.enabled=true 执行是否启用超时，默认启用true, 如果为false, 则熔断机制只在服务不可用时开启
hystrix.command.default.execution.isolation.thread.interruptOnTimeout=true 发生超时是是否中断，默认true

hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests 最大并发请求数，默认10，(注意，这个必须是SEMAPHORE隔离策略)
该参数当使用ExecutionIsolationStrategy.SEMAPHORE策略时才有效。如果达到最大并发请求数，请求会被拒绝。
理论上选择semaphore size的原则和选择thread size一致，但选用semaphore时每次执行的单元要比较小且执行速度快（ms级别），否则的话应该用thread。
semaphore应该占整个容器（tomcat）的线程池的一小部分。

Fallback相关的属性
这些参数可以应用于Hystrix的THREAD和SEMAPHORE策略

hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests 如果并发数达到该设置值，请求会被拒绝和抛出异常并且fallback不会被调用。默认10
hystrix.command.default.fallback.enabled 当执行失败或者请求被拒绝，是否会尝试调用hystrixCommand.getFallback() 。默认true
Circuit Breaker相关的属性
hystrix.command.default.circuitBreaker.enabled 用来跟踪circuit的健康性，如果未达标则让request短路。默认true
hystrix.command.default.circuitBreaker.requestVolumeThreshold 一个rolling window内最小的请求数。如果设为20，那么当一个rolling window的时间内（比如说1个rolling window是10秒）收到19个请求，即使19个请求都失败，也不会触发circuit break。默认20
hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds 触发短路的时间值，当该值设为5000时，则当触发circuit break后的5000毫秒内都会拒绝request，也就是5000毫秒后才会关闭circuit。默认5000
hystrix.command.default.circuitBreaker.errorThresholdPercentage错误比率阀值，如果错误率>=该值，circuit会被打开，并短路所有请求触发fallback。默认50
hystrix.command.default.circuitBreaker.forceOpen 强制打开熔断器，如果打开这个开关，那么拒绝所有request，默认false
hystrix.command.default.circuitBreaker.forceClosed 强制关闭熔断器 如果这个开关打开，circuit将一直关闭且忽略circuitBreaker.errorThresholdPercentage

Metrics相关参数
hystrix.command.default.metrics.rollingStats.timeInMilliseconds 设置统计的时间窗口值的，毫秒值，circuit break 的打开会根据1个rolling window的统计来计算。若rolling window被设为10000毫秒，则rolling window会被分成n个buckets，每个bucket包含success，failure，timeout，rejection的次数的统计信息。默认10000
hystrix.command.default.metrics.rollingStats.numBuckets 设置一个rolling window被划分的数量，若numBuckets＝10，rolling window＝10000，那么一个bucket的时间即1秒。必须符合rolling window % numberBuckets == 0。默认10
hystrix.command.default.metrics.rollingPercentile.enabled 执行时是否enable指标的计算和跟踪，默认true
hystrix.command.default.metrics.rollingPercentile.timeInMilliseconds 设置rolling percentile window的时间，默认60000
hystrix.command.default.metrics.rollingPercentile.numBuckets 设置rolling percentile window的numberBuckets。逻辑同上。默认6
hystrix.command.default.metrics.rollingPercentile.bucketSize 如果bucket size＝100，window＝10s，若这10s里有500次执行，只有最后100次执行会被统计到bucket里去。增加该值会增加内存开销以及排序的开销。默认100
hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds 记录health 快照（用来统计成功和错误绿）的间隔，默认500ms

Request Context 相关参数
hystrix.command.default.requestCache.enabled 默认true，需要重载getCacheKey()，返回null时不缓存
hystrix.command.default.requestLog.enabled 记录日志到HystrixRequestLog，默认true

Collapser Properties 相关参数
hystrix.collapser.default.maxRequestsInBatch 单次批处理的最大请求数，达到该数量触发批处理，默认Integer.MAX_VALUE
hystrix.collapser.default.timerDelayInMilliseconds 触发批处理的延迟，也可以为创建批处理的时间＋该值，默认10
hystrix.collapser.default.requestCache.enabled 是否对HystrixCollapser.execute() and HystrixCollapser.queue()的cache，默认true

ThreadPool 相关参数
线程数默认值10适用于大部分情况（有时可以设置得更小），如果需要设置得更大，那有个基本得公式可以follow：
requests per second at peak when healthy × 99th percentile latency in seconds + some breathing room
每秒最大支撑的请求数 (99%平均响应时间 + 缓存值)
比如：每秒能处理1000个请求，99%的请求响应时间是60ms，那么公式是：
1000 （0.060+0.012）

基本得原则时保持线程池尽可能小，他主要是为了释放压力，防止资源被阻塞。
当一切都是正常的时候，线程池一般仅会有1到2个线程激活来提供服务

hystrix.threadpool.default.coreSize 并发执行的最大线程数，默认10
hystrix.threadpool.default.maxQueueSize BlockingQueue的最大队列数，当设为－1，会使用SynchronousQueue，值为正时使用LinkedBlcokingQueue。该设置只会在初始化时有效，之后不能修改threadpool的queue size，除非reinitialising thread executor。默认－1。
hystrix.threadpool.default.queueSizeRejectionThreshold 即使maxQueueSize没有达到，达到queueSizeRejectionThreshold该值后，请求也会被拒绝。因为maxQueueSize不能被动态修改，这个参数将允许我们动态设置该值。if maxQueueSize == -1，该字段将不起作用
hystrix.threadpool.default.keepAliveTimeMinutes 如果corePoolSize和maxPoolSize设成一样（默认实现）该设置无效。如果通过plugin（https://github.com/Netflix/Hystrix/wiki/Plugins）使用自定义实现，该设置才有用，默认1.
hystrix.threadpool.default.metrics.rollingStats.timeInMilliseconds 线程池统计指标的时间，默认10000
hystrix.threadpool.default.metrics.rollingStats.numBuckets 将rolling window划分为n个buckets，默认10


//
//    /**
//     * 获取 请求来源
//     * @param request
//     * @return
//     */
//    private String getRequestFrom(RequestContext ctx,HttpServletRequest request){
//        String uri = request.getRequestURI();
//        //请求来源
//        String requestFrom = uri.substring(getCharacterPosition(uri,2)+1, getCharacterPosition(uri,3));
//        if(RequestFromEnum.CAR.getCode().equals(requestFrom)){
//            return RequestFromEnum.CAR.getCode();
//        }else if(RequestFromEnum.APP.getCode().equals(requestFrom)){
//            return RequestFromEnum.APP.getCode();
//        }else{
//            String from = request.getParameter("os");
//            if("Android-Car".equals(from)){
//                return RequestFromEnum.CAR.getCode();
//            }else if("Android".equals(from)){
//                return RequestFromEnum.APP.getCode();
//            }else if("iOS".equals(from)){
//                return RequestFromEnum.APP.getCode();
//            }else{
//                return "unknown";
//            }
//        }
//    }
//
//    /**
//     * 日志打印
//     * @param request
//     * uri = /activity/car/gasstation/v1/auth/getActivityRewardList
//     */
//    private void printLogs(HttpServletRequest request){
//        String uri = request.getRequestURI();
//        //查询参数 考虑到可能会有很多，这里暂时不打log
//        String queryString = request.getQueryString();
//        //返回发出请求的客户机的完整主机名
//        String remoteHost = request.getRemoteHost();
//        //返回发出请求的客户机的IP地址
//        String remoteAddr = request.getRemoteAddr();
//        //获取请求方式。doPost     POST
//        String method = request.getMethod();
//        //h返回请求的方案名，如http,ftp,https等
//        String scheme = request.getScheme();
//        //如果对发送请求的客户端主机用户进行了身份验证，则返回登录信息，否则返回null
//        String remoteUser = request.getRemoteUser();
//        //应用名称
//        String applicationName = uri.substring(uri.indexOf("/",0)+1, uri.indexOf("/",1));
//        //请求来源
//        String requestFrom = uri.substring(getCharacterPosition(uri,2)+1, getCharacterPosition(uri,3));
//
//        log.info("applicationName: {},remoteAddr: {},remoteHost: {},requestFrom: {},uri: {},method: {},scheme: {},remoteUser: {}",
//                applicationName,remoteAddr,remoteHost,requestFrom,uri,method,scheme,remoteUser);
//
//    }

//    /**
//     * 查看 "/"出现 第count次数的下标
//     * uri 需要查找的字符串 uri = /activity/car/gasstation/v1/auth/getActivityRewardList
//     * count 第几次出现
//     */
//    public int getCharacterPosition(String uri,int count){
//        //这里是获取"/"符号的位置
//        Matcher slashMatcher = Pattern.s("/").matcher(uri);
//        int mIdx = 0;
//        while(slashMatcher.find()) {
//            mIdx++;
//            //当"/"符号第三次出现的位置
//            if(mIdx == count){
//                break;
//            }
//        }
//        return slashMatcher.start();
//    }
//


