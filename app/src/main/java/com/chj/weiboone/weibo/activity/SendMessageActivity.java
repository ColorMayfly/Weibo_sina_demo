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
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.util.SystemBarTintManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

/**
 * 发送消息
 * Created by C on 2016/5/12.
 */
public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    //发送
    private Button btnSend;
    //微博内容
    private EditText etText;
    private String access_token;
    private RequestParams params;
    private AsyncHttpClient client;
    public static final int TEXT = 1;
    public static final int TEXT_PIC = 2;
    //带上传图片
    private GridView gvPics;
    //微博可见性选项
    private RadioGroup rgpVisible;
    //可见性惨数
    private static int visible= 0;
    //添加图片
    private Button btnAddPic;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        initWindow();
        findView();

        client = new AsyncHttpClient();
        // 获取当前已保存过的 Token
        Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(this);
        access_token = oauth2AccessToken.getToken();
        params = new RequestParams();
    }

    /**
     * 初始化
     */
    private void findView() {
        final TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("发微博");
        btnSend = (Button) findViewById(R.id.btn_send_message);
        etText = (EditText) findViewById(R.id.et_text);
        gvPics = (GridView) findViewById(R.id.gv_pics);
        rgpVisible = (RadioGroup) findViewById(R.id.rgp_visible);
        btnAddPic = (Button) findViewById(R.id.btn_add_pic);

        rgpVisible.setOnCheckedChangeListener(this);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_message:
                try {
                    params.put("access_token", access_token);
                    String encode = URLEncoder.encode(etText.getText().toString(), "utf-8");
                    params.put("status", encode);
                    client.post(Constants.URL_UPDATE_TEXT, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            if (i == 200) {
                                Intent intent = new Intent();
                                Toast.makeText(SendMessageActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                setResult(TEXT, intent);
                                SendMessageActivity.this.finish();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 微博可见性选择
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_0:
                visible = 0;
                break;
            case R.id.rb_1:
                visible = 1;
                break;
            case R.id.rb_2:
                visible = 2;
                break;
            case R.id.rb_3:
                visible = 3;
                break;
            default:
                break;
        }
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
