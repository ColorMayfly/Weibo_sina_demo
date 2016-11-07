package com.chj.weiboone.weibo.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.adapter.FollowersAdapter;
import com.chj.weiboone.weibo.entity.Friendships;
import com.chj.weiboone.weibo.util.SystemBarTintManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 粉丝
 *
 * Created by C on 2016/5/2.
 */
public class FriendsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Oauth2AccessToken mAccessToken;
    private int cursor = 0;
    private Button btnLoad;
    private List<Friendships.UsersBean> lists;
    private Friendships.UsersBean usersBean;
    private FollowersAdapter followersAdapter;
    private SystemBarTintManager tintManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        initWindow();
        findView();
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        String uid = mAccessToken.getUid();
        String access_token = mAccessToken.getToken();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("access_token", access_token);
        params.put("uid", uid);
        params.put("count", 20);
        params.put("cursor", cursor);
        client.get(Constants.URL_FRIENDS_LINE, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("FollowersActivity", "onSuccess: ---" + response.toString());
                try {
                    JSONArray users = response.getJSONArray("users");
                    Log.i("FollowersActivity", "length: ---" + users.length());
                    cursor = response.getInt("next_cursor");
                    for (int i = 0; i < users.length(); i++) {
                        usersBean = new Friendships.UsersBean();
                        JSONObject jsonObject = (JSONObject) users.get(i);
                        //头像大图
                        String avatar_hd = jsonObject.getString("avatar_hd");
                        //用户昵称
                        String screen_name = jsonObject.getString("screen_name");
                        Log.i("FollowersActivity", "screen_name: ---" + screen_name);
                        String description = jsonObject.getString("description");

                        usersBean.setAvatar_hd(avatar_hd);
                        usersBean.setScreen_name(screen_name);
                        usersBean.setDescription(description);
                        lists.add(usersBean);
                        followersAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("FollowersActivity", "onFailure: ---" + errorResponse.toString());
            }
        });
    }

    /**
     * 初始化
     */
    private void findView() {
        lists = new ArrayList<>();
        final TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("关注");
        listView = (ListView)findViewById(R.id.lv_followers);
        btnLoad = (Button) findViewById(R.id.btn_load);
        followersAdapter = new FollowersAdapter(this, lists);
        listView.setAdapter(followersAdapter);
        listView.setOnItemClickListener(this);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Friendships.UsersBean usersBean = lists.get(position);
        //根据昵称查询
        final String screen_name = usersBean.getScreen_name();
        final long uid = usersBean.getId();
        final Intent intent = new Intent(FriendsActivity.this, InfoActivity.class);
        intent.putExtra("screen_name", screen_name);
        intent.putExtra("uid", uid);
        FriendsActivity.this.startActivity(intent);
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
