package com.wk.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Configuration
public class CorsConfig{
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer(){
            public void addCorsMappings(CorsRegistry registry) {
                //*registry.addMapping("/api*").allowedOrigins("http://localhost:8081");*//*
                registry.addMapping("/**")
                        .allowedOrigins(ALL)
                        .allowedMethods(ALL)
                        .allowedHeaders(ALL)
                        .allowCredentials(true);
            }
        };
    }
}
