package com.chj.weiboone.weibo.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.util.SystemBarTintManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

/**
 * 转发
 * Created by C on 2016/5/19.
 */
public class RepostInfoActivity extends AppCompatActivity{

    private EditText text;
    private Button btnSend;
    private String access_token;
    private RequestParams params;
    private AsyncHttpClient client;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repost);
        initWindow();
        findView();
    }

    private void requestNet() {
        final Intent intent = getIntent();
        final long id = intent.getLongExtra("id", 0);
        Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(this);
        access_token = oauth2AccessToken.getToken();
        params = new RequestParams();
        client = new AsyncHttpClient();
        params.put("access_token", access_token);
        params.put("id", id);
        //添加的转发文本，必须做URLencode，内容不超过140个汉字，不填则默认为“转发微博”。
        final String encode;
        try {
            encode = URLEncoder.encode(text.getText().toString(), "utf-8");
            params.put("status", encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(Constants.URL_REPOST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(RepostInfoActivity.this, "转发成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(RepostInfoActivity.this, "转发失败", Toast.LENGTH_SHORT).show();
            }
        });

        final Intent intent1 = new Intent();
        setResult(RESULT_OK, intent1);
        text.setText("");
        RepostInfoActivity.this.finish();
    }

    /**
     * 初始化
     */
    private void findView() {
        final TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("转发");
        text = (EditText) findViewById(R.id.et_text);
        btnSend = (Button) findViewById(R.id.btn_send_repost);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNet();
            }
        });
    }

    /**
     *沉浸式布局
     */
    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(Color.parseColor("#00bb9c"));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

}
