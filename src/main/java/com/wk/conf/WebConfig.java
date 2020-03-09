package com.wk.conf;

import com.wk.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/checkreport/**")
                .excludePathPatterns("/login");
    }

}
