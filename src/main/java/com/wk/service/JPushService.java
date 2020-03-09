package com.wk.service;

import cn.jpush.api.push.PushResult;

public interface JPushService {

    /**
     * 同步nativeHttpClient
     * @return
     */
    PushResult sendCustomPush();

    /**
     * 异步NettyHttpClient 通过回调函数获取推送情况
     * @return
     */
    PushResult sendPushWithCallback();
}
