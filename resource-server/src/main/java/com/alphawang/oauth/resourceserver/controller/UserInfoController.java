package com.alphawang.oauth.resourceserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    // 资源API
    @RequestMapping("/api/userinfo")
    public ResponseEntity<UserInfo> getUserInfo() {
        String username = (String) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        
        String email = username + "@alphawang.com";

        UserInfo userInfo = new UserInfo();
        userInfo.setName(username);
        userInfo.setEmail(email);

        return ResponseEntity.ok(userInfo);
    }
}
