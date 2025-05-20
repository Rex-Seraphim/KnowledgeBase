package com.hbnu.knowledgebase.interceptor;

import com.hbnu.knowledgebase.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 处理 OPTIONS 预检请求
        if ("OPTIONS".equals(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        // 获取 token 并校验逻辑
        String token = request.getHeader("Authorization");
        if (validateToken(token)) {
            return true;
        } else {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未登录");
            return false;
        }
    }

    private boolean validateToken(String token) {
        try {
            JwtUtil.parseUsername(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}

