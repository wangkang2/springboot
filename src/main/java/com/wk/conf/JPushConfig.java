package com.wk.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jpush")
public class JPushConfig {

    private String appKey;
    private String masterSecret;
    private long liveTime;


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public long getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(long liveTime) {
        this.liveTime = liveTime;
    }
}
