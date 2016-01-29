package com.conti.share.model.wechat;

import com.conti.share.utils.http.httpParam.HttpParam;
import com.conti.share.utils.http.json.JsonNode;

/**
 * Created by yezhongwen on 16/1/20 at 下午1:40.
 * Description :
 */
public class WXRefreshModel {

    /**
     * RefreshToken request
     */
    @HttpParam(key = "appid")
    public String appid;
    @HttpParam(key = "grant_type")
    public String grant_type = "refresh_token";
    @HttpParam(key = "refresh_token")
    public String refresh_token_req;

    /**
     * RefreshToken response
     */
    @JsonNode(key = "access_token")
    public String access_token;
    @JsonNode(key = "expires_in")
    public String expires_in;

    @JsonNode(key = "refresh_token")
    public String refresh_token_resp;

    @JsonNode(key = "openid")
    public String openid;

    @JsonNode(key = "scope")
    public String scope;


    /**
     * error response
     * */
    @JsonNode(key = "errcode")
    public String errcode;

    @JsonNode(key = "errmsg")
    public String errmsg;


    public String getUrl(){
        return "https://api.weixin.qq.com/sns/oauth2/refresh_token?";
    }

}

