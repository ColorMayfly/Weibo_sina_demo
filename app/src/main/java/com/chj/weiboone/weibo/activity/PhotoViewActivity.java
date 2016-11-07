package com.chj.weiboone.weibo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.adapter.PhotoPagerAdapter;
import com.chj.weiboone.weibo.view.HackyViewPager;

import java.util.ArrayList;

/**
 * 图片浏览
 *
 * Created by C on 2016/5/14.
 */
public class PhotoViewActivity extends AppCompatActivity{

    private HackyViewPager mViewPager;
    private ArrayList<String> picsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        Intent intent = getIntent();
        picsUrl = intent.getStringArrayListExtra("picsUrl");
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        setContentView(mViewPager);
        PhotoPagerAdapter pagerAdapter = new PhotoPagerAdapter(this, picsUrl);
        mViewPager.setAdapter(pagerAdapter);
    }
}
