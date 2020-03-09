package com.wk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wk.conf.properties.Wechat;
import com.wk.service.HttpClientService;
import com.wk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.annotation.PostConstruct;

@Component
public class WechatUtil {

    @Autowired
    private RedisService redisServiceImpl;
    @Autowired
    private HttpClientService httpClientServiceImpl;
    @Autowired
    private Wechat wechat;

    private static WechatUtil wechatUtil;

    @PostConstruct
    public void init(){
        wechatUtil = this;
    }

    public static String getToken() throws Exception {

        Object tokenObject = wechatUtil.redisServiceImpl.get("token");
        String accessToken = null;
        long expiresIn = 0L;
        if(tokenObject!=null){
            accessToken = String.valueOf(tokenObject);
        }else {
            String url = wechatUtil.wechat.getTokenUrl()+"&appId="+wechatUtil.wechat.getAppId()+"&secret="+wechatUtil.wechat.getAppsecret();
            String response = wechatUtil.httpClientServiceImpl.doGet(url);
            System.out.println(response);
            JSONObject jsonObject = JSON.parseObject(response);
            String errcode = jsonObject.getString("errcode");
            if(StringUtils.isEmpty(errcode)){
                accessToken = jsonObject.getString("access_token");
                expiresIn = jsonObject.getLong("expires_in");
                wechatUtil.redisServiceImpl.set("token",accessToken,expiresIn);
            }
        }
        return accessToken;
    }
}
