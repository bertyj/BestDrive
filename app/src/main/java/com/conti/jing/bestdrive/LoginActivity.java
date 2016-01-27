package com.conti.jing.bestdrive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.conti.jing.bestdrive.util.RequestTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener {
    private final String LOGIN_URL = "http://115.29.209.155:8080/TMS_JSON/user_login";
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private TextView mForgetPasswordTextView;
    private Button mUserLoginButton;
    private Button mQqLoginButton;
    private Button mWechatLoginButton;
    private Button mWeiboLoginButton;
    private JsonObjectRequest mLoginJsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.conti.jing.bestdrive.R.layout.layout_login_activity);
        initView();
    }

    private void initView() {
        mUsernameEditText = (EditText) findViewById(com.conti.jing.bestdrive.R.id.edit_login_username);
        mPasswordEditText = (EditText) findViewById(com.conti.jing.bestdrive.R.id.edit_login_password);
        mUserLoginButton = (Button) findViewById(com.conti.jing.bestdrive.R.id.button_user_login);
        mUserLoginButton.setOnClickListener(this);
        mForgetPasswordTextView = (TextView) findViewById(com.conti.jing.bestdrive.R.id.text_forget_password);
        mForgetPasswordTextView.setOnClickListener(this);
        mQqLoginButton = (Button) findViewById(com.conti.jing.bestdrive.R.id.button_login_qq);
        mQqLoginButton.setOnClickListener(this);
        mWechatLoginButton = (Button) findViewById(com.conti.jing.bestdrive.R.id.button_login_wechat);
        mWechatLoginButton.setOnClickListener(this);
        mWeiboLoginButton = (Button) findViewById(com.conti.jing.bestdrive.R.id.button_login_weibo);
        mWeiboLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case com.conti.jing.bestdrive.R.id.button_user_login:
                if (TextUtils.isEmpty(mUsernameEditText.getText())) {
                    Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mPasswordEditText.getText())) {
                    Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (mUsernameEditText.getText().toString().contains("@")) {
                        Log.i("User Request", "UserName: " + mUsernameEditText.getText() + " " + "Password: " + mPasswordEditText.getText());
                        Thread loginThread = new Thread(new LoginRunnable());
                        loginThread.start();
                    } else {
                        Toast.makeText(this, "用户名必须为E-mail地址！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case com.conti.jing.bestdrive.R.id.text_forget_password:
                break;
            case com.conti.jing.bestdrive.R.id.button_login_qq:
                break;
            case com.conti.jing.bestdrive.R.id.button_login_wechat:
                break;
            case com.conti.jing.bestdrive.R.id.button_login_weibo:
                break;
        }
    }

    private class LoginRunnable implements Runnable {
        @Override
        public void run() {
            sendLoginRequest(MainActivity.sRequestQueue);
        }

        private void sendLoginRequest(RequestQueue requestQueue) {
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                private boolean mResultStatus;
                private String mUserToken;
                private String mFailReason;

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        mResultStatus = response.getBoolean("result");
                        if (mResultStatus) {
                            mUserToken = response.getString("userToken");
                            startActivity(new Intent(LoginActivity.this, ShareActivity.class));
                            Toast.makeText(LoginActivity.this, "登录成功！" + "User Token: " + mUserToken, Toast.LENGTH_SHORT).show();
                        } else {
                            mFailReason = response.getString("failreason");
                            Toast.makeText(LoginActivity.this, "登录失败！" + "Fail Reason: " + mFailReason, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("Server Response", response.toString());
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            };
            JSONObject loginUser = new JSONObject();
            try {
                loginUser.put("email", mUsernameEditText.getText().toString());
                loginUser.put("password", mPasswordEditText.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mLoginJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_URL, loginUser, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    return headers;
                }
            };
            mLoginJsonObjectRequest.setTag(RequestTag.TAG_INDEPENDENT_REQUEST);
            requestQueue.add(mLoginJsonObjectRequest);
        }
    }
}
