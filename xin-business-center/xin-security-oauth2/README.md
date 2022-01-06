README.md

https://cloud.tencent.com/developer/article/1427340


2、OAuth 2.0 认证流程

第一步：得到授权码 code

首先直接跳转至用户授权地址，即图示 Request User Url ，提示用户进行登录，并给予相关资源授权，得到唯一的 Auth code ，这里注意的是 code 只有 10 分钟的有效期，对于安全考虑，相对于 OAuth 1.0 省了一步获取临时的 Token ，并且有效期也进行了控制，比 1.0 认证简化了很多，并安全一些；

第二步：获取 access token

得到授权 code 后，就是请求 access token ，通过图示 Request access url ，生成得到数据 Token ；

第三步：通过 access token, 获取 OpenID

通过 Access Token 请求 OpenID ， OpenID 是用户在此平台的唯一标识，通过图示 Request info url 请求，然后得到 OpenID ；

第四步：通过 access token 及 OpenID 调用 API,获取用户授权信息

通过第二步得到的数据 Token 、第三步得到的 OpenID 及相关 API ，进行请求，获取用户授权资源信息。

3、OAuth 授权模式

OAuth2.0 定义了 四种授权模式。分别为：

授权码模式
简化模式
密码模式
客户端模式
4、oauth2 实例

可以分为简易的分为三个步骤

配置资源服务器
配置认证服务器
配置spring security
