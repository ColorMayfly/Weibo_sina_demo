package com.chj.weiboone.weibo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.adapter.MainFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by C on 2016/5/11.
 */
public class HomeFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       if (view == null) {
           view = inflater.inflate(R.layout.fragment_home, container, false);
           radioGroup = (RadioGroup) view.findViewById(R.id.rgp_home);
           viewPager = (ViewPager) view.findViewById(R.id.vp_home);
           radioGroup.setOnCheckedChangeListener(this);
           RadioButton childAt = (RadioButton) radioGroup.getChildAt(0);
           childAt.setChecked(true);
           fragments = new ArrayList<>();
           fragments.add(new AllFragment());
           fragments.add(new AttentionFragment());
           viewPager.setAdapter(new MainFragmentPagerAdapter(getFragmentManager(), fragments));
           viewPager.setOnPageChangeListener(this);
       }
        return view;
    }

    /**
     * RadioGroup事件监听
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.isChecked()) {
                viewPager.setCurrentItem(i, false);
                break;
            }
        }
    }

    /**
     *ViewPager事件监听
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
        radioButton.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
