package org.wtm.jwt.controller;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wtm.jwt.annotation.JwtIgnore;
import org.wtm.jwt.bean.Audience;
import org.wtm.jwt.common.response.Result;
import org.wtm.jwt.utils.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class AdminUserController {

    private static Logger log = LoggerFactory.getLogger(AdminUserController.class);


    @Autowired
    private Audience audience;

    @PostMapping("/login")
    @JwtIgnore
    public Result adminLogin(HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password) {
        // 这里模拟测试, 默认登录成功，返回用户ID和角色信息
        String userId = UUID.randomUUID().toString();
        String role = "admin";
        System.out.println(username+"11111111111111");
        // 创建token
        String token = JwtTokenUtil.createJWT(userId, username, role, audience);
        log.info("### 登录成功, token={} ###", token);

        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        // 将token响应给客户端
        JSONObject result = new JSONObject();
        result.put("token", token);
        return Result.SUCCESS(result);
    }

    @GetMapping("/users")
    public Result userList(HttpServletRequest request) {
        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String token = authHeader.substring(7);
        String username = JwtTokenUtil.getUsername(token, audience.getBase64Secret());
        log.info("### 查询所有用户列表 ###"+username);
        return Result.SUCCESS();
    }
}

