package com.chj.weiboone.weibo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.activity.SendMessageActivity;

/**
 * 消息页
 *
 * Created by C on 2016/5/12.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {

    private TextView btnSend;
    public static final int SEND_TEXT = 1;
    private  View view;
    private TextView commentByMe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_message, null);
            btnSend = (TextView) view.findViewById(R.id.btn_send);
            commentByMe = (TextView) view.findViewById(R.id.tv_commment_by_me);
            final TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvTitle.setText("消息");

            btnSend.setOnClickListener(this);
            commentByMe.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_send://发微博
                intent.setClass(getContext(), SendMessageActivity.class);
                startActivityForResult(intent, SEND_TEXT);
                break;
            case R.id.tv_commment_by_me:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
