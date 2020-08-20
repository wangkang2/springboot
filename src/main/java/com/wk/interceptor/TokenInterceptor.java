package com.wk.interceptor;

import com.wk.conf.JwtConfig;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SignatureException {

        String token = request.getHeader(jwtConfig.getHeader());

        if(StringUtils.isEmpty(token)){
            throw new SignatureException("token为空");
        }

        if(jwtConfig.isTokenExpired(token)){
            throw new SignatureException("token失效");
        }
        return true;

    }

}
