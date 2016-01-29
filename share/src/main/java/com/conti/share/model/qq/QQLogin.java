package com.conti.share.model.qq;
import android.app.Activity;

import com.conti.share.model.Constants;
import com.conti.share.utils.common.LogUtil;
import com.conti.share.utils.http.json.JsonResolver;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by yezhongwen on 16/1/22 at 上午11:20.
 * Description :
 */
public class QQLogin implements IUiListener {

    private Tencent mTencent;
    private QQLoginModel mLoginModel;
    private Activity mActivity;
    private QQLoginListener mLoginListener;
    private static QQLogin instance;

    private QQLogin(Activity activity,  QQLoginListener loginListener){
        this.mActivity = activity;
        this.mTencent = Tencent.createInstance(Constants.qqAppid, activity.getApplicationContext());
        this.mLoginListener = loginListener;
    }

    public static  synchronized QQLogin getInstance(Activity activity, QQLoginListener loginListener){
        if (instance == null){
            instance = new QQLogin(activity, loginListener);
        }

        return instance;
    }

    public void login(){
        LogUtil.LOG_D(this, "login start");
        if (!mTencent.isSessionValid()){
            mTencent.login(mActivity, "all", this);
        }
    }

    @Override
    public void onComplete(Object o) {
        LogUtil.LOG_D(this, "login onQQLoginComplete : " + o.toString());
        try {
            mLoginModel = JsonResolver.fromJson(o.toString(), QQLoginModel.class);

            if (mLoginListener != null){
                mLoginListener.onQQLoginComplete(mLoginModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        LogUtil.LOG_D(this, "login onQQLoginError : ");
        if (mLoginListener != null){
            mLoginListener.onQQLoginError(uiError);
        }
    }

    @Override
    public void onCancel() {
        LogUtil.LOG_D(this, "login onQQLoginCancel : ");
        if (mLoginListener != null){
            mLoginListener.onQQLoginCancel();
        }
    }
}
