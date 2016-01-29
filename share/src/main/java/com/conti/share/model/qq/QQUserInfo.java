package com.conti.share.model.qq;

import android.content.Context;

import com.conti.share.utils.common.LogUtil;
import com.conti.share.utils.http.json.JsonResolver;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by yezhongwen on 16/1/22 at 下午4:07.
 * Description :
 */
public class QQUserInfo implements IUiListener {
    QQUserInfoModel userInfo;
    private Context mContext;
    private Tencent mTencent;
    private QQLoginModel mQQLoginModel;
    private QQUserInfoListener mUserInfoListener;

    public QQUserInfo(Context context, Tencent tencent, QQLoginModel qqLoginModel, QQUserInfoListener userInfoListener){
        this.mContext = context;
        this.mTencent = tencent;
        this.mQQLoginModel = qqLoginModel;
        this.mUserInfoListener = userInfoListener;

        mTencent.setAccessToken(mQQLoginModel.accessToken, mQQLoginModel.expiresIn);
        mTencent.setOpenId(mQQLoginModel.openid);

        QQToken qqToken = mTencent.getQQToken();
        UserInfo userInfo = new UserInfo(mContext,qqToken);
        userInfo.getUserInfo(this);
    }


    @Override
    public void onComplete(Object o) {
        LogUtil.LOG_D(this, "onQQLoginComplete UserInfoListener : " + o.toString());
        try {
            userInfo = JsonResolver.fromJson(o.toString(), QQUserInfoModel.class);
            if (mUserInfoListener != null) {
                mUserInfoListener.onComplete(userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(UiError uiError) {
        if (mUserInfoListener != null) {
            mUserInfoListener.onError(uiError);
        }
    }

    @Override
    public void onCancel() {
        if (mUserInfoListener != null){
            mUserInfoListener.onCancel();
        }
    }
}
