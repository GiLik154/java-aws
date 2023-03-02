package com.example.demo;

import com.example.demo.interceptor.CheckPassBookKindsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.demo.interceptor.LoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/*.ico")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/reset/**")
                .excludePathPatterns("/join/**");

        registry.addInterceptor(new CheckPassBookKindsInterceptor())
                .order(1)
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/join/**")
                .excludePathPatterns("/pbkinds/**")
                .excludePathPatterns("/choice/**")
                .excludePathPatterns("/reset/**")
                .excludePathPatterns("/*.ico");
    }
}
