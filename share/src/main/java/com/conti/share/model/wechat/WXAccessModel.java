package com.conti.share.model.wechat;

import com.conti.share.utils.http.httpParam.HttpParam;
import com.conti.share.utils.http.json.JsonNode;

/**
 * Created by yezhongwen on 16/1/20 at 下午12:41.
 * Description :
 */
public class WXAccessModel {

    /***
     * request
     */
    @HttpParam(key = "appid")
    public String appid;

    @HttpParam(key = "secret")
    public String secret;

    @HttpParam(key = "code")
    public String code;

    @HttpParam(key = "grant_type")
    public String grant_type = "authorization_code";



    /**
     * correct response
     * */
    @JsonNode(key = "access_token")
    public String access_token;

    @JsonNode(key = "expires_in")
    public String expires_in;

    @JsonNode(key = "refresh_token")
    public String refresh_token;

    @JsonNode(key = "openid")
    public String openid;

    @JsonNode(key = "scope")
    public String scope;

    @JsonNode(key = "unionid")
    public String unionid;

    /**
     * error response
     * */
    @JsonNode(key = "errcode")
    public String errcode;

    @JsonNode(key = "errmsg")
    public String errmsg;


    public String getUrl(){
        return "https://api.weixin.qq.com/sns/oauth2/access_token?";
    }


}
