# 密码模式

## 组件
### 1. password-server 9000

@EnableAuthorizationServer

```java
@Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
        throws Exception {
        clients.inMemory()
            .withClient("clientapp")
            .secret("112233")
//            .redirectUris("http://localhost:9001/callback")
            // 面膜模式
            .authorizedGrantTypes("password")
            .scopes("read_userinfo", "read_contacts");
    } 
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
curl -X POST --user clientapp:112233 http://localhost:8080/oauth/token -H "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d "grant_type=password&username=bobo&password=xyz&scope=read_userinfo"
```


API: `oauth/token`  
Authorization:
- basic: clientapp:112233 

Params:
- client_id=clientapp
- grant_type=password
- username=alpha
- password=123
- scope=read_userinfo

Response:
```
{
    "access_token": "4a7db895-ac66-471e-9d0e-4795ab91dd42",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read_userinfo"
}
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