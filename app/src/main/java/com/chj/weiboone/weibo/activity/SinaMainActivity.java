package com.chj.weiboone.weibo.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.fragment.DiscoverFragment;
import com.chj.weiboone.weibo.fragment.HomeFragment;
import com.chj.weiboone.weibo.fragment.MessageFragment;
import com.chj.weiboone.weibo.fragment.MyFragment;
import com.chj.weiboone.weibo.util.SystemBarTintManager;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

/**
 * 登陆新浪主页面
 *
 * Created by C on 2016/5/1.
 */
public class SinaMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private String access_token;
    private AsyncHttpClient client;

    //是否退出
    private boolean isExit;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }

    };
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private DiscoverFragment discoverFragment;
    private MyFragment myFragment;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sina_main);
        initWindow();
        findView();
        initFragment();

    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        discoverFragment = new DiscoverFragment();
        myFragment = new MyFragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout, homeFragment);
        transaction.commit();
    }

    /**
     * 初始化控件
     *
     */
    private void findView() {
        radioGroup = (RadioGroup) findViewById(R.id.rg_sina_main);
        //设置RadioGroup初始位置为0
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this);
    }


    /**
     * radiogroup的事件监听
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        transaction = fragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.rb_home:
                transaction.replace(R.id.frame_layout, homeFragment);
                break;
            case R.id.rb_attention:
                transaction.replace(R.id.frame_layout, messageFragment);
                break;
            case R.id.rb_discover:
                transaction.replace(R.id.frame_layout, discoverFragment);
                break;
            case R.id.rb_my:
                transaction.replace(R.id.frame_layout, myFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }


    /**
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 判断是否退出
     */
    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
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
