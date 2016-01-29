package com.conti.share.model.wechat;

import com.conti.share.utils.http.httpParam.HttpParam;
import com.conti.share.utils.http.json.JsonNode;

/**
 * Created by yezhongwen on 16/1/20 at 下午1:49.
 * Description :
 */
public class WXCheckModel {

    /***
     * request
     */
    @HttpParam(key = "access_token")
    public String access_token;
    @HttpParam(key = "openid")
    public String openid;

    /**
     * error response
     * if error_code is 0 and errmsg is "ok", it means the access token is ok;
     * if error_code is others, it means the access token is invalid;
     * */
    @JsonNode(key = "errcode")
    public String errcode;
    @JsonNode(key = "errmsg")
    public String errmsg;


    public String getUrl(){
        return "https://api.weixin.qq.com/sns/auth?";
    }


}
