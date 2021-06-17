# 如果您觉得本项目对你有用，请随手star，谢谢
技术文章地址：https://www.jianshu.com/u/6ad247189165

# xin-auth-jwt
基于jwt实现的认证服务 实现了
``` 
1，基于apollo作为动态配置
2，基于docker的打包，上传harbor上
3，登陆生成token，返回给客户端（userId）不能在外网中传播
4，通过redis实现退出功能
``` 
## JWT  
JSON Web Token（JWT）是一个非常轻巧的规范。这个规范允许我们使用JWT在用户和服务器之间传递安全可靠的信息。一个JWT实际上就是一个字符串，它由三部分组成，头部、载荷与签名。  

### 1，头部（Header）  
``` 
头部用于描述关于该JWT的最基本的信息，例如其类型以及签名所用的算法等。这也可以被表示成一个JSON对象。  
{"typ":"JWT","alg":"HS256"}在头部指明了签名算法是HS256算法。 我们进行BASE64编码http://base64.xpcha.com/，  
编码后的字符串如下：eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9  

小知识：Base64是一种基于64个可打印字符来表示二进制数据的表示方法。由于2的6次方等于64，所以每6个比特为一个单元，  
对应某个可打印字符。三个字节有24个比特，对应于4个Base64单元，即3个字节需要用4个可打印字符来表示。  
JDK 中提供了非常方便的 BASE64Encoder 和 BASE64Decoder，用它们可以非常方便的完成基于 BASE64 的编码和解码  
``` 
### 2，载荷（playload） 
``` 
载荷就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分  
（1）标准中注册的声明（建议但不强制使用）  
 iss: jwt签发者  
 sub: jwt所面向的用户
 aud: 接收jwt的一方
 exp: jwt的过期时间，这个过期时间必须要大于签发时间
 nbf: 定义在什么时间之前，该jwt都是不可用的.
 iat: jwt的签发时间
 jti: jwt的唯一身份标识，主要用来作为一次性token。
 
（2）公共的声明  
 公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息.但不建议添加敏感信息，因为该部分在客户端可解密.
 
（3）私有的声明  
 私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为明文信息。
 这个指的就是自定义的claim。比如前面那个结构举例中的admin和name都属于自定的claim。
 这些claim跟JWT标准规定的claim区别在于：JWT规定的claim，JWT的接收方在拿到JWT之后，都知道怎么对这些标准的claim进行验证(还不知道是否能够验证)；
 而private claims不会验证，除非明确告诉接收方要对这些claim进行验证以及规则才行。

 定义一个payload:{"sub":"1234567890","name":"John Doe","admin":true} 然后将其进行base64加密，得到Jwt的第二部分。
 eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9
``` 
### 3，签证（signature）  
``` 
jwt的第三部分是一个签证信息，这个签证信息由三部分组成：
header (base64后的)
payload (base64后的)
secret
这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，
然后就构成了jwt的第三部分。
TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ

将这三部分用.连接成一个完整的字符串,构成了最终的jwt:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
注意：secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
``` 

一旦签发不能撤销 没有类似清空 session 的操作，不能自动续签
针对上面缺点的解决方案；    
使用黑名单机制； 把作废的 JWT 放入黑名单； 但是这样就引入了状态； 相比 session 就没有那么大的优势了；  
使用 OAuth 中的 refresh_token ；  