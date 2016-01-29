package com.conti.share.model.wechat;

import com.conti.share.utils.http.json.JsonNode;

/**
 * Created by yezhongwen on 16/1/20 at 上午11:40.
 * Description :
 */
public class WXAuthResp {

    @JsonNode(key = "ErrCode", description = "")
    public int Errcode;

    @JsonNode(key = "code", description = "")
    public String code;

    @JsonNode(key = "state", description = "")
    public String state;

    @JsonNode(key = "lang", description = "")
    public String lang;

    @JsonNode(key = "country", description = "")
    public String country;

}
