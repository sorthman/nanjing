package org.linlinjava.litemall.core.notify;

import org.linlinjava.litemall.core.util.HttpUtil;

import java.util.Map;

public class AxonSmsSender {

    static String api = "http://122.192.33.40:18080/api/v2/Sms/SmsSendMessage";

    public static boolean SendSms(String phone, String templatename, Map<String, String> params) {
        String postdata = "{";
        postdata += "\"smsTemplateName\":\"" + templatename + "\",";
        postdata += "\"mob\":\"" + phone + "\",";
        postdata += "\"source\":\"品值APP\",";
        postdata += "\"sender\":\"cd390931edec434cba3731e8a11f25c2\",";
        postdata += "\"channelType\":0,";

        for (Map.Entry<String, String> entry : params.entrySet()) {
            postdata += "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
        }
        postdata = postdata.substring(0, postdata.length() - 1);
        postdata += "}";

//        System.out.println("发送数据：" + postdata);
        String res = HttpUtil.sendPostByJson(api, postdata);
        if (res.indexOf("true") >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
