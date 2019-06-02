
# 组件
1. authcode-server 9000

@EnableAuthorizationServer

```java
 clientDetailsServiceConfigurer.inMemory()
            .withClient("clientapp")
            .secret("112233")
            .redirectUris("http://localhost:9001/callback")
            .authorizedGrantTypes("authorization_code")
            .scopes("read_userinfo", "read_contacts");
```

2. resource-server 9002

@EnableResourceServer

```java
httpSecurity.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .requestMatchers()
            .antMatchers("/api/**");
```

# 未鉴权时
访问资源服务：http://localhost:9002/api/userinfo  
提示：`Full authentication is required to access this resource`


# 鉴权流程
## 1. 获取授权码

```
http://localhost:9000/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9001/callback&response_type=code&scope=read_userinfo
```

弹出提示框，输入用户名密码（properties文件中配置）；
提示是否同意授权。

API: `oauth/authorize`  
Params:
- client_id=clientapp
- redirect_uri=http://localhost:9001/callback
- response_type=code
- scope=read_userinfo

Response:
http://localhost:9001/callback?code=8uYpdo

Q: 报错 InsufficientAuthenticationException: User must be authenticated with Spring Security before authorization can be completed.  
A:


## 2. 获取访问令牌
```
curl -X POST --user clientapp:112233 http://localhost:9000/oauth/token -H "content-type: application/x-www-form-urlencoded" -d "code=8uYpdo&grant_type=authorization_code&redirect_uri=http%3A%2F%2Flocalh ost%3A9001%2Fcallback&scope=read_userinfo"
```


API: `oauth/token`

Authorization:
- type: Basic Auth
- username: clientapp
- password: 112233  

**EnableAuthorizationServer中配置的信息！**


Params: 
- code=8uYpdo
- grant_type=authorization_code
- redirect_uri=http%3A%2F%2Flocalhost%3A9001%2Fcallback
- scope=read_userinfo

Response:
```
{
    "access_token": "36cded80-b6f5-43b7-bdfc-594788a24530",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read_userinfo"
}
```

## 3. 访问资源
```
curl -X GET http://localhost:9002/api/userinfo -H "authorization: Bearer 36cded80-b6f5-43b7-bdfc-594788a24530"
```

Authorization:
- type: Bearer Token
- token: step 2 token

Headers:
- authorization: Bearer 36cded80-b6f5-43b7-bdfc-594788a24530