package com.wk.handler;

import io.jsonwebtoken.SignatureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class PermissionHandler {

    @ResponseBody
    @ExceptionHandler(value = {SignatureException.class})
    public Map signatureException(SignatureException exception){
        Map<String, Object> map = new HashMap<>();
        map.put("code",401);
        map.put("msg",exception.getMessage());
        return map;
    }
}
