package com.conti.share.model.qq;
import com.tencent.tauth.UiError;

/**
 * Created by yezhongwen on 16/1/25 at 下午2:21.
 * Description :
 */
public interface QQLoginListener {

    public void onQQLoginComplete(QQLoginModel loginModel) ;

    public void onQQLoginError(UiError uiError) ;

    public void onQQLoginCancel() ;


}
