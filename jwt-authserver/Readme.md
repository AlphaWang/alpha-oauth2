## 1. 获取JWT令牌
```
curl -X POST --user clientapp:112233 http://localhost:9000/oauth/token -H "accept: application/json" -H "content-type: application/x-www-formurlencoded" -d "grant_type=password&username=alpha&password=xyz&scope=read_userinfo"
```


API: `oauth/token`  
Auth:
- Basic: clientapp / 112233
Params:
- response_type=password
- username=alpha
- password=xyz
- scope=read_userinfo


Response:

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjAxMTg1ODMsInVzZXJfbmFtZSI6ImFscGhhIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjlmNzdmYWI3LThmODctNDBmYi04MWExLTE0NzBiM2NiMGYzYiIsImNsaWVudF9pZCI6ImNsaWVudGFwcCIsInNjb3BlIjpbInJlYWRfdXNlcmluZm8iXX0.50fxwwdqSDh-mqY7Dyc7Gq6f2oe6sQ3fusSLhkLFd_Y",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhbHBoYSIsInNjb3BlIjpbInJlYWRfdXNlcmluZm8iXSwiYXRpIjoiOWY3N2ZhYjctOGY4Ny00MGZiLTgxYTEtMTQ3MGIzY2IwZjNiIiwiZXhwIjoxNTYyNjY3MzgzLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNmU5NmVhYTItNzU4Yi00NDFiLTlkYzMtYTdjYzNkODgzMGE1IiwiY2xpZW50X2lkIjoiY2xpZW50YXBwIn0.Ou7BrgW7aFTFf-pbtD6OXONpC_KBs7wACivtAfCxdrI",
    "expires_in": 43199,
    "scope": "read_userinfo",
    "jti": "9f77fab7-8f87-40fb-81a1-1470b3cb0f3b"
}
```

### 2. 调用API

```
curl -X GET http://localhost:9002/api/userinfo -H "authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjAxMTg1ODMsInVzZXJfbmFtZSI6ImFscGhhIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjlmNzdmYWI3LThmODctNDBmYi04MWExLTE0NzBiM2NiMGYzYiIsImNsaWVudF9pZCI6ImNsaWVudGFwcCIsInNjb3BlIjpbInJlYWRfdXNlcmluZm8iXX0.50fxwwdqSDh-mqY7Dyc7Gq6f2oe6sQ3fusSLhkLFd_Y"
```

Response:

```json
{
    "name": "alpha",
    "email": "alpha@alphawang.com"
}
```