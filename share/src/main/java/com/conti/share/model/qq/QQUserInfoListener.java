package com.conti.share.model.qq;

import com.conti.share.utils.common.LogUtil;
import com.conti.share.utils.http.json.JsonResolver;
import com.tencent.tauth.UiError;

/**
 * Created by yezhongwen on 16/1/25 at 下午2:52.
 * Description :
 */
public interface QQUserInfoListener {

    public void onComplete(QQUserInfoModel userInfoModel);

    public void onError(UiError uiError);

    public void onCancel() ;

}
