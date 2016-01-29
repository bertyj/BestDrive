package com.conti.share.model;

/**
 * Created by yezhongwen on 16/1/20 at 下午1:15.
 * Description :
 */
public class Constants {


    /**
     * wechat url
     *
     * method   : get
     * response  : json
     * */
    public static final String wechatAccessToken    = "/sns/oauth2/access_token";
    public static final String wechatRefreshToken   = "/sns/oauth2/refresh_token";
    public static final String wechatCheckToken     = "/sns/auth";
    public static final String wechatUserInfo       = "/sns/userinfo";

    public static final String wechatAppId          = "wxe376e545c669f550";
    public static final String wechatSecret         = "77a7e7f3a8965b258a4550a8b136256a";
    public static final String wechatScope          = "snsapi_userinfo";


    /**
     * QQ url
     *
     * method   : get
     * response : json
     * */

    public static final String qqAppid              = "222222";
}
