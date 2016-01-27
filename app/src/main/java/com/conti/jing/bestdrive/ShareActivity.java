package com.conti.jing.bestdrive;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareActivity extends Activity implements View.OnClickListener {
    private Button mShareButton;
    private OnekeyShare mOnekeyShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_share_activity);
        initView();
        mOnekeyShare = new OnekeyShare();
    }

    private void setShare() {
        ShareSDK.initSDK(this);
        mOnekeyShare.disableSSOWhenAuthorize();
        mOnekeyShare.setTitle("分享标题：第三方分享测试");
        mOnekeyShare.setText("分享内容：这个仅仅是第三方分享的测试内容，哈哈");
        mOnekeyShare.setImageUrl("http://img.gawkerassets.com/img/17nng5apwhl0ijpg/original.jpg");
        mOnekeyShare.setUrl("http://weixin.qq.com/"); //Only for Wechat
        mOnekeyShare.setTitleUrl("http://qzone.qq.com/"); //Only for QQ Zone
        mOnekeyShare.show(this);
    }

    private void initView() {
        mShareButton = (Button) findViewById(R.id.button_share);
        mShareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_share:
                setShare();
                break;
        }
    }
}
