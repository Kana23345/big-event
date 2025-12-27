package com.myproject.interceptors;

import com.myproject.utils.JwtUtil;
import com.myproject.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 登录拦截器
 * 用于验证请求中的JWT令牌，确保用户已登录才能访问受保护的资源
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前执行的拦截方法
     * 验证请求头中的Authorization令牌，并将用户信息存储到ThreadLocal中
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @param handler  请求处理器
     * @return 如果令牌有效返回true，否则返回false
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取Authorization令牌
        String token = request.getHeader("Authorization");
        try {
//            System.out.println("HereHereHereHere HereHere");
            // 解析JWT令牌获取用户信息
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 将用户信息存储到ThreadLocal中，供后续处理使用
            ThreadLocalUtil.set(claims);

            return true;
        } catch (Exception e) {
            // 如果令牌解析失败，返回401未授权状态码
            response.setStatus(401);
            return false;
        }
    }

    /**
     * 在请求处理完成后执行的方法
     * 清除ThreadLocal中存储的用户信息，防止内存泄漏
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @param handler  请求处理器
     * @param ex       异常信息
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 从ThreadLocal中移除用户信息，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}