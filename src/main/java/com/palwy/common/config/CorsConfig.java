package com.palwy.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://one-in.shhpalwy.com","http://one-in-test.shhpalwy.com","http://10.32.0.2:8080","http://localhost:8080","http://localhost:9528","http://h5-installment-shop-test.shhpalwy.com","http://zy-shop-test02.palwy.com","https://installment.shhpalwy.com", "http://zy-shop-test.shhpalwy.com","https://zy-admin.shhpalwy.com","https://common-test.shhpalwy.com","http://192.168.1.172:9094") // 允许的前端域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true) // 允许携带凭证
                .maxAge(3600); // 预检请求缓存时间（秒）
    }
}
