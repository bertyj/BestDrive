package com.conti.jing.bestdrive;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends Activity implements View.OnClickListener {
    private final String REGISTER_URL = "http://115.29.209.155:8080/TMS_JSON/user_register";
    private JsonObjectRequest mRegisterJsonObjectRequest;
    private Button mRegisterButton;
    private EditText mNicknameEditText;
    private EditText mPasswordEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.conti.jing.bestdrive.R.layout.layout_register_activity);
        initView();
    }

    private void initView() {
        mRegisterButton = (Button) findViewById(com.conti.jing.bestdrive.R.id.button_user_register);
        mRegisterButton.setOnClickListener(this);
        mNicknameEditText = (EditText) findViewById(com.conti.jing.bestdrive.R.id.edit_register_nickname);
        mPasswordEditText = (EditText) findViewById(com.conti.jing.bestdrive.R.id.edit_register_password);
        mPhoneEditText = (EditText) findViewById(com.conti.jing.bestdrive.R.id.edit_register_phone);
        mEmailEditText = (EditText) findViewById(com.conti.jing.bestdrive.R.id.edit_register_email);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == com.conti.jing.bestdrive.R.id.button_user_register) {
            if (TextUtils.isEmpty(mNicknameEditText.getText())) {
                Toast.makeText(this, "用户昵称不能为空！", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(mPasswordEditText.getText())) {
                Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(mPhoneEditText.getText())) {
                Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(mEmailEditText.getText())) {
                Toast.makeText(this, "Email地址不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                Thread registerThread = new Thread(new RegisterRunnable());
                registerThread.start();
                Log.i("User Register", "用户昵称：" + mNicknameEditText.getText().toString() + " 密码：" + mPasswordEditText.getText().toString() + " 手机号码：" + mPhoneEditText.getText().toString() + " Email地址：" + mEmailEditText.getText().toString());
            }
        }
    }

    private class RegisterRunnable implements Runnable {
        @Override
        public void run() {
            sendRegisterRequest(MainActivity.sRequestQueue);
        }

        private void sendRegisterRequest(final RequestQueue requestQueue) {
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
                            Toast.makeText(RegisterActivity.this, "用户注册成功！UserToken：" + mUserToken, Toast.LENGTH_SHORT).show();
                        } else {
                            mFailReason = response.getString("failreason");
                            Toast.makeText(RegisterActivity.this, "用户注册失败！FailReason：" + mFailReason, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("Server Response", response.toString());
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(RegisterActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
                }
            };
            JSONObject registerUser = new JSONObject();
            try {
                registerUser.put("nickName", mNicknameEditText.getText().toString());
                registerUser.put("password", mPasswordEditText.getText().toString());
                registerUser.put("phoneNumber", mPhoneEditText.getText().toString());
                registerUser.put("email", mEmailEditText.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mRegisterJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, registerUser, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    return headers;
                }
            };
            mRegisterJsonObjectRequest.setTag(RequestTag.TAG_INDEPENDENT_REQUEST);
            requestQueue.add(mRegisterJsonObjectRequest);
        }
    }
}
