package com.alphawang.oauth.authcodeserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig implements AuthorizationServerConfigurer {
    @Override 
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {
        
    }

    @Override 
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        clientDetailsServiceConfigurer.inMemory()
            .withClient("clientapp")
            .secret("112233")
            .redirectUris("http://localhost:9001/callback")
            .authorizedGrantTypes("authorization_code")
            .scopes("read_userinfo", "read_contacts");
    }

    @Override 
    public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) throws Exception {

    }
}
