package com.conti.share.model.qq;

import com.conti.share.utils.http.json.JsonNode;

/**
 * Created by yezhongwen on 16/1/22 at 下午1:15.
 * Description :
 *
 *
 * successed:
 {
 "ret":0,

 "msg":"",

 "nickname":"Peter",

 "figureurl":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/30",

 "figureurl_1":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/50",

 "figureurl_2":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/100",

 "figureurl_qq_1":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/40",

 "figureurl_qq_2":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/100",

 "gender":"男",

 "is_yellow_vip":"1",

 "vip":"1",

 "yellow_vip_level":"7",

 "level":"7",

 "is_yellow_year_vip":"1"

 }

 failed:
 {

 "ret":1002,

 "msg":"请先登录"

 }

 */
public class QQUserInfoModel {
    @JsonNode(key = "is_yellow_year_vip")
    String isYellowYearVip;

    @JsonNode(key = "ret")
    String ret;

    @JsonNode(key = "figureurl_qq_1")
    String figureurlQq1;

    @JsonNode(key = "figureurl_qq_2")
    String figureurlQq2;

    @JsonNode(key = "nickname")
    String nickName;

    @JsonNode(key = "yellow_vip_level")
    String yellowVipLevel;

    @JsonNode(key = "msg")
    String msg;

    @JsonNode(key = "figureurl_1")
    String figureurl_1;

    @JsonNode(key = "vip")
    String vip;

    @JsonNode(key = "level")
    String level;

    @JsonNode(key = "figureurl_2")
    String figureurl_2;

    @JsonNode(key = "is_yellow_vip")
    String IsYellowVip;

    @JsonNode(key = "gender")
    String gender;

    @JsonNode(key = "figureurl")
    String figureurl;
}
