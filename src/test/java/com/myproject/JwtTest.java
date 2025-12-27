package com.myproject;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT（JSON Web Token）测试类
 * 用于测试JWT令牌的生成和解析功能
 */
public class JwtTest {
    
    /**
     * 测试JWT令牌的生成
     * 演示如何使用auth0的JWT库创建包含用户信息的JWT令牌
     */
    @Test
    public void testGen() {
        // 创建声明信息map，包含用户的基本信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");
        
        // 生成JWT令牌
        // 设置过期时间为12小时后
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))//12小时
                .withClaim("user", claims)
                .sign(Algorithm.HMAC256("myPrivateKey"));

        System.out.println(token);
    }

    /**
     * 测试JWT令牌的解析
     * 演示如何使用auth0的JWT库解析和验证JWT令牌
     */
    @Test
    public void testParse() {
        // 示例JWT令牌字符串
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJleHAiOjE3NjY2MjcxNjQsInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoi5byg5LiJIn19" +
                ".XUP0Yy4LW0OPS7jwEhqTWubb4KYdCw1UN1IMFT3V5Gg";
        
        // 创建JWT验证器，使用指定的密钥进行验证
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("myPrivateKey")).build();
        
        // 验证并解析JWT令牌
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        
        // 获取声明信息
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}