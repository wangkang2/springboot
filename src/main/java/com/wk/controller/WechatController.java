package com.wk.controller;

import com.wk.service.RedisService;
import com.wk.utils.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("wechatController")
public class WechatController {


    @Autowired
    private RedisService redisServiceImpl;

    @RequestMapping("getToken")
    @ResponseBody
    public String getToken(){
        try {
            return WechatUtil.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        //return WechatUtil.getToken();
    }
}
