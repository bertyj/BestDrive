package com.conti.share.model.wechat;

import android.content.Context;
import android.widget.Toast;

import com.conti.share.model.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by yezhongwen on 16/1/25 at 下午3:49.
 * Description :
 */
public class WXLogin {

    private IWXAPI mWechatAPI;
    private Context mContext;
    private static WXLogin instance;

    private WXLogin(Context context){
        this.mContext = context;
        if (mWechatAPI == null){
            mWechatAPI = WXAPIFactory.createWXAPI(mContext, Constants.wechatAppId, false);
        }
    }

    public static synchronized WXLogin getInstance(Context context){
        if (instance == null){
            instance = new WXLogin(context);
        }
        return instance;
    }

    public void Login(){
        if (!mWechatAPI.isWXAppInstalled()) {
            //提醒用户没有按照微信
            Toast.makeText(mContext, "没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }

        mWechatAPI.registerApp(Constants.wechatAppId);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = Constants.wechatScope;
        mWechatAPI.sendReq(req);
    }

}
