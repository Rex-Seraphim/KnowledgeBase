package com.hbnu.knowledgebase.config;

import com.hbnu.knowledgebase.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//拦截器文件，通过拦截器文件实现登录校验
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/**"
//                        "/api/auth/login",
//                        "/api/auth/register",
//                        "/api/kb/{kbId}/articles",
//                        "/api/user/**",
//                        "/api/article/**"
                );
    }
}

