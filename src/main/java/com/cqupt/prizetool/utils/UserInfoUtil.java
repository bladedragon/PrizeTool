package com.cqupt.prizetool.utils;


import com.cqupt.prizetool.utils.verify.SHA1Utils;

public class UserInfoUtil {


    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";


    public static String getCode(String APPID, String REDIRECT_URI, String SCOPE) {
        return String.format(Get_Code, APPID, REDIRECT_URI, SCOPE);
    }


    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";


    public static String getWebAccess(String APPID, String SECRET, String CODE) {
        return String.format(Web_access_tokenhttps, APPID, SECRET, CODE);
    }


    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";


    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token, openid);
    }


    public static void main(String[] args) {


        String openid="ouRCyjtjZXSh31ArxIFOQrBj0eog";
        String string = "asdfghjkl";
        String timestamp = "1505118409";
        String encodeStr = SHA1Utils.encode( SHA1Utils.encode(timestamp)+"."+SessionUtil.getMD5(string)+".redrock");
        System.out.println(encodeStr);




    }

}
