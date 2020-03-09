package com.wk.controller;

import cn.jpush.api.push.PushResult;
import com.wk.service.JPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpush")
public class JPushController {

    @Autowired
    private JPushService jPushServiceImpl;

    @GetMapping("/sendCustomPush")
    public PushResult sendCustomPush(){
        return jPushServiceImpl.sendCustomPush();
    }

    @GetMapping("/sendPushWithCallback")
    public PushResult sendPushWithCallback(){
        return jPushServiceImpl.sendPushWithCallback();
    }
}
