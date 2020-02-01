package org.linlinjava.litemall.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "litemall.wx")
public class WxProperties {

    private String appId;

    private String appSecret;

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String keyPath;

    private String wxnotifyurl;

    private String coursewxnotifyurl;

    private String alipaynotifyurl;

    private String coursealipaynotifyurl;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getWxnotifyurl() {
        return wxnotifyurl;
    }

    public void setWxnotifyurl(String wxnotifyurl) {
        this.wxnotifyurl = wxnotifyurl;
    }

    public String getCoursewxnotifyurl() {
        return coursewxnotifyurl;
    }

    public void setCoursewxnotifyurl(String coursewxnotifyurl) {
        this.coursewxnotifyurl = coursewxnotifyurl;
    }

    public String getAlipaynotifyurl() {
        return alipaynotifyurl;
    }

    public void setAlipaynotifyurl(String alipaynotifyurl) {
        this.alipaynotifyurl = alipaynotifyurl;
    }

    public String getCoursealipaynotifyurl() {
        return coursealipaynotifyurl;
    }

    public void setCoursealipaynotifyurl(String coursealipaynotifyurl) {
        this.coursealipaynotifyurl = coursealipaynotifyurl;
    }
}
