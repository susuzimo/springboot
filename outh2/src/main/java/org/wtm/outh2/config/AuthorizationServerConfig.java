package org.wtm.outh2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

//授权服务器
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
      return   new BCryptPasswordEncoder();
    }


    //授权模式
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
                //内存里面
        clients.inMemory()
                .withClient("password") //设置认证模式
                .authorizedGrantTypes("password","refresh_token")        //授权模式
                .accessTokenValiditySeconds(1800)  //token过期时间
                .resourceIds("rid")  //资源id
                .scopes("all")
                .secret("$2a$10$/t6Xej2quLL61UsDpyhv/.x3d/0ZowiBZrvzXBMPSV55VNFx26AZy");   //需要的密码
     }


     //
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //令牌存到什么地方
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }


    //支持做登录认证
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }
}
