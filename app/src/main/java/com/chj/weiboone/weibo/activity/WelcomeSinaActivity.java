package com.chj.weiboone.weibo.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.util.SystemBarTintManager;

/**
 * 新浪欢迎界面
 * Created by C on 2016/5/14.
 */
public class WelcomeSinaActivity extends AppCompatActivity{

    private Handler mHandler = new Handler();
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_sina);
        initWindow();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeSinaActivity.this, SinaMainActivity.class));
                WelcomeSinaActivity.this.finish();
            }
        }, 2000);
    }

    /**
     *沉浸式布局
     */
    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(Color.parseColor("#ffffff"));
            tintManager.setStatusBarTintEnabled(true);
        }
    }
}
