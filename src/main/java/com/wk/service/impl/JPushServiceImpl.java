package com.wk.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.wk.conf.JPushConfig;
import com.wk.service.JPushService;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class JPushServiceImpl implements JPushService {

    private static final Logger logger = LoggerFactory.getLogger(JPushServiceImpl.class);

    @Autowired
    private JPushConfig jPushConfig;

    @Override
    public PushResult sendCustomPush(){
        PushResult pushResult = null;
        ClientConfig clientConfig = ClientConfig.getInstance();
        clientConfig.setTimeToLive(jPushConfig.getLiveTime());
        JPushClient jPushClient = new JPushClient(jPushConfig.getMasterSecret(),jPushConfig.getAppKey(),null,clientConfig);
        PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId("140fe1da9ec73af7165"))
                .setMessage(Message.newBuilder()
                        .setTitle("测试题目")
                        .setMsgContent("测试内容3333")
                        .build())
                .setNotification(Notification.newBuilder()
                        .setAlert("测试通知9999")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("测试题目")
                                .addExtra("kay","value")
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert("测试6666")
                                .incrBadge(1)
                                .setSound("default")
                                .addExtra("key","value")
                                .build())
                        .build())
                .build();
        try {
            pushResult = jPushClient.sendPush(pushPayload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return pushResult;
    }

    @Override
    public PushResult sendPushWithCallback() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        clientConfig.setTimeToLive(jPushConfig.getLiveTime());
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(jPushConfig.getAppKey(),jPushConfig.getMasterSecret()),null,clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload pushPayload = PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.registrationId("140fe1da9ec73af7165"))
                    .setMessage(Message.newBuilder()
                            .setTitle("测试题目")
                            .setMsgContent("测试内容789")
                            .build())
                    .setNotification(Notification.newBuilder()
                            .setAlert("测试通知9999")
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setTitle("测试题目")
                                    .addExtra("kay","value")
                                    .build())
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert("测试6666")
                                    .incrBadge(1)
                                    .setSound("default")
                                    .addExtra("key","value")
                                    .build())
                            .build())
                    .build();
            client.sendRequest(HttpMethod.POST, pushPayload.toString(), uri, new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    logger.info("Got result: " + responseWrapper.responseContent);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }
        return null;
    }

}
