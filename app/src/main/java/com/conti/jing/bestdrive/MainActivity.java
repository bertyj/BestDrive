package com.conti.jing.bestdrive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.conti.jing.bestdrive.util.RequestQueueSingleton;
import com.conti.jing.bestdrive.util.RequestTag;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button mLoginButton;
    private Button mRegisterButton;
    private RequestQueueSingleton mRequestQueueSingleton;
    public static RequestQueue sRequestQueue;
    public static Cache sRequestCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.conti.jing.bestdrive.R.layout.layout_main_activity);
        initView();
        mRequestQueueSingleton = RequestQueueSingleton.getInstance(this);
        if (sRequestQueue == null) {
            sRequestCache = mRequestQueueSingleton.getRequestCache();
            sRequestQueue = mRequestQueueSingleton.getRequestQueue();
        }
        sRequestQueue.start();
    }

    private void initView() {
        mLoginButton = (Button) findViewById(com.conti.jing.bestdrive.R.id.button_login);
        mLoginButton.setOnClickListener(this);
        mRegisterButton = (Button) findViewById(com.conti.jing.bestdrive.R.id.button_register);
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case com.conti.jing.bestdrive.R.id.button_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case com.conti.jing.bestdrive.R.id.button_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mRequestQueueSingleton.destroyRequestQueue(sRequestQueue, RequestTag.TAG_INDEPENDENT_REQUEST, sRequestCache);
    }
}
