
## 启用info端点并禁用所有其他端点
```
management.endpoints.enabled-by-default=false
management.endpoint.info.enabled=true
```
## 要停止通过JMX公开所有端点并仅公开health和info端点
```
management.endpoints.jmx.exposure.include=health,info
```

## 要通过HTTP公开除env和beans端点之外的所有内容，请使用以下属性：
```
management.endpoints.web.exposure.include=*
management.endpoints.web.exposure.exclude=env,beans
```

## 配置端点缓存响应的时间
```
management.endpoint.beans.cache.time-to-live=10s
```

## 默认情况下，CORS支持处于禁用状态 允许来自example.com域的GET和POST调用：
```
management.endpoints.web.cors.allowed-origins=http://example.com
management.endpoints.web.cors.allowed-methods=GET,POST
```
```
DOWN	服务不可用 (503)  
OUT_OF_SERVICE	服务不可用 (503)  
UP	默认情况下没有映射，所以http状态是200
UNKNOWN	默认情况下没有映射，所以http状态是200
management.health.status.order=FATAL, DOWN, OUT_OF_SERVICE, UNKNOWN, UP
management.health.status.http-mapping.FATAL=503`
```
