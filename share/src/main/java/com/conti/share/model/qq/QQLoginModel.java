package com.conti.share.model.qq;

import com.conti.share.utils.http.json.JsonNode;

/**
 * Created by yezhongwen on 16/1/22 at 下午1:05.
 * Description :
 * {
 "ret":0,
 "pay_token":"xxxxxxxxxxxxxxxx",
 "pf":"openmobile_android",
 "expires_in":"7776000",
 "openid":"xxxxxxxxxxxxxxxxxxx",
 "pfkey":"xxxxxxxxxxxxxxxxxxx",
 "msg":"sucess",
 "access_token":"xxxxxxxxxxxxxxxxxxxxx"
 }
 */
public class QQLoginModel {
    @JsonNode(key = "ret")
    String ret;

    @JsonNode(key = "pay_token")
    String payToken;

    @JsonNode(key = "pf")
    String pf;

    @JsonNode(key = "expires_in")
    String expiresIn;

    @JsonNode(key = "openid")
    String openid;

    @JsonNode(key = "msg")
    String msg;

    @JsonNode(key = "access_token")
    String accessToken;

}
