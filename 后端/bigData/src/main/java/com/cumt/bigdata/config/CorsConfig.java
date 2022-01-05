package com.cumt.bigdata.config;

/**
 * @date 2021/11/27 - 10:57
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // 设置允许跨域请求的域名
                        .allowedOriginPatterns("*")
                        // 是否允许证书（cookies）
                        .allowCredentials(true)
                        // 设置允许的方法
                        .allowedMethods("*")
                        // 跨域允许时间
                        .maxAge(3600);
            }
        };
    }
}
