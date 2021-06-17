# 如果您觉得本项目对你有用，请随手star，谢谢
技术文章地址：https://www.jianshu.com/u/6ad247189165

# xin-spring-boot-starter-redis redis工具

该项目主要提供了：  
1，redis的使用  
2，redis cachea 的使用  
3，geohash的基本操作  

#redis使用
### 第一步 引入jar
``` 
<dependency>
    <groupId>com.xin.commons</groupId>
    <artifactId>xin-spring-boot-starter-redis</artifactId>
</dependency>
``` 
### 第二步 配置文件
``` 
#### Redis服务器地址
spring.redis.host=0.0.0.0
#### Redis服务器连接端口
spring.redis.port=6379  
#### Redis服务器连接密码（默认为空）
spring.redis.password=
#### 连接超时时间 单位 ms（毫秒）
spring.redis.timeout=3000
#### 连接池最大连接数（使用负值表示没有限制）
spring.redis.lettuce.pool.max-active=8  
#### 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.lettuce.pool.max-wait=-1  
#### 连接池中的最大空闲连接
spring.redis.lettuce.pool.max-idle=8  
#### 连接池中的最小空闲连接
spring.redis.lettuce.pool.min-idle=0
``` 
### 第三步 使用
``` 
@Autowired
private RedisService redisService;
``` 

# redis cachea使用
``` 
@Cacheable(value = "user", key = "#id")
@Override
public User get(Long id) {
    // TODO 我们就假设它是从数据库读取出来的
    log.info("进入 get 方法");
    return DATABASES.get(id);
}

@CachePut(value = "user", key = "#user.id")
@Override
public User saveOrUpdate(User user) {
    DATABASES.put(user.getId(), user);
    log.info("进入 saveOrUpdate 方法");
    return user;
}

@CacheEvict(value = "user", key = "#id")
@Override
public void delete(Long id) {
    DATABASES.remove(id);
    log.info("进入 delete 方法");
}
``` 