package com.wk.controller;

import com.wk.com.wk.annotation.ApiJsonObject;
import com.wk.com.wk.annotation.ApiJsonProperty;
import com.wk.conf.JwtConfig;
import com.wk.entity.User;
import com.wk.service.LoginService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("登录管理")
@RestController()
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginServiceImpl;

    @Autowired
    private JwtConfig jwtConfig;

    @ApiOperation(value = "登录",notes = "登录")
    @PostMapping("/login")
    public Map login(@ApiJsonObject(name = "login_model", value = {
            @ApiJsonProperty(key = "username", example = "18614242538", description = "用户名"),
            @ApiJsonProperty(key = "passwd", example = "123456", description = "密码")
    })@RequestBody List<Map<String,String>> list){
        Map<String,String> params = list.get(0);
        String username = params.get("username");
        String passwd = params.get("passwd");
        User user = loginServiceImpl.login(username,passwd);
        Map map = new HashMap();
        map.put("user",user);
        map.put("token",jwtConfig.createToken(user.getId().toString()));
        return map;
    }
}
