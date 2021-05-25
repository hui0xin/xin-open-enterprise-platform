
# 如果您觉得本项目对你有用，请随手star，谢谢
技术文章地址：https://www.jianshu.com/u/6ad247189165

# xin-commons-mail 

邮件发送工具

## 具体操作
### 第一步 引入jar
``` 
<dependency>
    <groupId>com.xin.commons</groupId>
    <artifactId>xin-commons-mail</artifactId>
</dependency>
``` 
### 第二步 配置文件
``` 
spring:
  mail:
    # 邮箱服务器
    host: smtp.exmail.qq.com
    # 发送者的邮箱账号
    username: sender@xingeauto.com
    # 密码
    password: xxxxxx
    # 字符集
    default-encoding: UTF-8
    # 表示开启 DEBUG 模式，这样，邮件发送过程的日志会在控制台打印出来，方便排查错误
    debug: true
    smtp:
      # 需要验证登录名和密码 默认是true
      auth: true
      port: 465
      ssl:
        #开启ssl加密 否则项目启动时报530error
        enable: true
        socketFactory: sf
      starttls:
        #需要TLS认证 保证发送邮件安全验证
        enable: true
        required: true
``` 
### 第三步 使用
``` 
@Autowired
private SendMailService sendMailService;
``` 