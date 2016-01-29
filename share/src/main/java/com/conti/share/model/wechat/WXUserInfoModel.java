package com.conti.share.model.wechat;

import com.conti.share.utils.http.httpParam.HttpParam;
import com.conti.share.utils.http.json.JsonNode;

/**
 * Created by yezhongwen on 16/1/20 at 下午1:57.
 * Description :
 */
public class WXUserInfoModel {

        /**
         * RefreshToken request
         */
        @HttpParam(key = "access_token")
        public String access_token;
        @HttpParam(key = "openid")
        public String openid;

        /**
         * zh_CN ，
         * zh_TW ，
         * en ，
         * the default is zh-CN
         * */
        @HttpParam(key = "lang")
        public String lang;



        /**
         * RefreshToken response
         */
        @JsonNode(key = "openid")
        public String openid_resp;

        @JsonNode(key = "nickname")
        public String nickname;

        @JsonNode(key = "sex")
        public String sex;

        @JsonNode(key = "province")
        public String province;

        @JsonNode(key = "city")
        public String city;

        @JsonNode(key = "country")
        public String country;

        @JsonNode(key = "headimgurl")
        public String headimgurl;

        @JsonNode(key = "privilege")
        public String[] privilege;

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
                return "https://api.weixin.qq.com/sns/userinfo?";
        }

}
