# eureka 注册中心
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
    home-page-url-path: ${r'${server.servlet.context-path}'}
    health-check-url-path: ${r'${server.servlet.context-path}'}/actuator/health
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
  meta: @apollo.meta@
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