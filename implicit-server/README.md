# 简化模式

## 组件
### 1. authcode-server 9000

@EnableAuthorizationServer

```java
 clientDetailsServiceConfigurer.inMemory()
            .withClient("clientapp")
            .secret("112233")
            .redirectUris("http://localhost:9001/callback")
            .authorizedGrantTypes("implicit")
            .accessTokenValiditySeconds(120)
            .scopes("read_userinfo", "read_contacts");
```

### 2. resource-server 9002

@EnableResourceServer

```java
httpSecurity.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .requestMatchers()
            .antMatchers("/api/**");
```

## 未鉴权时
访问资源服务：http://localhost:9002/api/userinfo  
提示：`Full authentication is required to access this resource`


## 鉴权流程
### 1. 获取访问令牌

```
http://localhost:9000/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9001/callback&response_type=token&scope=read_userinfo&state=abc
```

弹出提示框，输入用户名密码（properties文件中配置）；
提示是否同意授权。

API: `oauth/authorize`  
Params:
- client_id=clientapp
- redirect_uri=http://localhost:9001/callback
- response_type=token
- scope=read_userinfo

Response:
```
http://localhost:9001/callback#access_token=0406040a-779e-4b5e-adf1-bf2f02031e83&token_type=bearer&state=abc&expires_in=119
```



## 2. 获取资源

```
curl -X GET http://localhost:9002/api/userinfo -H "authorization: Bearer 36cded80-b6f5-43b7-bdfc-594788a24530"
```

Authorization:
- type: Bearer Token
- token: step 1 token

Headers:
- authorization: Bearer 36cded80-b6f5-43b7-bdfc-594788a24530