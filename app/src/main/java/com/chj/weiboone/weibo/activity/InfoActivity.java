package com.chj.weiboone.weibo.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.adapter.UserLineAdapter;
import com.chj.weiboone.weibo.entity.UserLine;
import com.chj.weiboone.weibo.util.DateFormat;
import com.chj.weiboone.weibo.util.MyListView;
import com.chj.weiboone.weibo.util.SourceFormatUtil;
import com.chj.weiboone.weibo.util.SystemBarTintManager;
import com.chj.weiboone.weibo.view.CircleTransform;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 查看个人信息
 * Created by C on 2016/5/18.
 */
public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Oauth2AccessToken mAccessToken;
    //微博封面
    private ImageView ivCover;
    //头像
    private ImageView ivProfile;
    //用户昵称
    private TextView tvScreenName;
    //用户所在地
    private TextView tvLocation;
    //粉丝数
    private TextView tvFollowers;
    //关注数
    private TextView tvFriends;
    //微博数
    private TextView tvStatuses;
    //收藏数
    private TextView tvFavourites;
    //粉丝框
    private LinearLayout llFollowers;
    private LinearLayout llFriends;
    private LinearLayout llStatuses;
    private LinearLayout llFavourites;
    private MyListView lvMyNew;
    private UserLineAdapter adapter;
    private List<UserLine.StatusesBean> lists;
    private UserLine.StatusesBean statusesBean;
    private SystemBarTintManager tintManager;
    private String screen_name;
    private long uid;
    private SwipeRefreshLayout swipeRefresh;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my);
        initWindow();
        findView();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestNet();
            }
        }, 5000);

    }

    /**
     * 初始化
     *
     *
     */
    private void findView() {
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestNet();
                    }
                }, 5000);
            }
        });
        swipeRefresh.setColorSchemeColors(
                R.color.base_color,
                R.color.base_color,
                R.color.base_color,
                R.color.base_color);
        final TextView title = (TextView) findViewById(R.id.tv_title);
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        ivProfile = (ImageView) findViewById(R.id.iv_profile);
        tvScreenName = (TextView) findViewById(R.id.tv_screen_name);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        llFollowers = (LinearLayout) findViewById(R.id.ll_followers);
        llFriends = (LinearLayout) findViewById(R.id.ll_friends);
        llStatuses = (LinearLayout) findViewById(R.id.ll_statuses);
        llFavourites = (LinearLayout) findViewById(R.id.ll_favourites);
        tvFollowers = (TextView) findViewById(R.id.tv_followers);
        tvFriends = (TextView) findViewById(R.id.tv_friends);
        tvStatuses = (TextView) findViewById(R.id.tv_statuses);
        tvFavourites = (TextView) findViewById(R.id.tv_favourites);
        lvMyNew = (MyListView) findViewById(R.id.lv_my_new);
        lists = new ArrayList<>();
        adapter = new UserLineAdapter(this, lists);
        lvMyNew.setAdapter(adapter);

        llFollowers.setOnClickListener(this);
        llFriends.setOnClickListener(this);
        llStatuses.setOnClickListener(this);
        llFavourites.setOnClickListener(this);

        final Intent intent = getIntent();
        screen_name = intent.getStringExtra("screen_name");
        title.setText(screen_name + "的信息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_followers://粉丝
                startActivity(new Intent(this, FollowersActivity.class));
                break;
            case R.id.ll_friends://关注
                startActivity(new Intent(this, FriendsActivity.class));
                break;
            case R.id.ll_statuses://微博
                startActivity(new Intent(this, UserActivity.class));
                break;
            case R.id.ll_favourites://收藏
                startActivity(new Intent(this, FollowersActivity.class));
                break;

            default:
                break;
        }
    }

    /**
     * 网络请求
     */
    public void requestNet() {
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        String access_token = mAccessToken.getToken();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("access_token", access_token);
        params.put("screen_name", screen_name);
//        params.put("uid", uid);

        client.get(Constants.URL_USER, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("info...", "onSuccess: ---" + response.toString());
                try {
                    String cover_image;
                    //封面
                    if (response.toString().contains("cover_image_phone")) {
                        cover_image = response.getString("cover_image_phone");
                        Picasso.with(InfoActivity.this).load(cover_image).into(ivCover);
                    } else if (response.toString().contains("cover_image")) {
                        cover_image = response.getString("cover_image");
                        Picasso.with(InfoActivity.this).load(cover_image).into(ivCover);
                    }
                    //头像大图
                    String avatar_large = response.getString("avatar_large");
                    System.out.println("---avatar_large" + avatar_large);
                    //用户昵称
                    String screen_name = response.getString("screen_name");
                    //所在地
                    String location = response.getString("location");
                    //粉丝、关注、微博、收藏
                    String followers_count = response.getString("followers_count");
                    String friends_count = response.getString("friends_count");
                    String statuses_count = response.getString("statuses_count");
                    String favourites_count = response.getString("favourites_count");



                    Picasso.with(InfoActivity.this).load(avatar_large).transform(new CircleTransform()).into(ivProfile);
                    tvScreenName.setText(screen_name);
                    tvLocation.setText(location);
                    tvFollowers.setText(followers_count);
                    tvFriends.setText(friends_count);
                    tvStatuses.setText(statuses_count);
                    tvFavourites.setText(favourites_count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.print("---onfailure1" + responseString);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.print("---onfailure2" + errorResponse);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.print("---onfailure3" + errorResponse);
                swipeRefresh.setRefreshing(false);
            }
        });

        /**
         * 最新发表的微博
         */
        client.get(Constants.URL_USER_LINE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("infoactivity", "onSuccess: --" + response.toString());
                try {
                    JSONArray statuses = (JSONArray) response.get("statuses");
                    for (int i = 0; i < statuses.length(); i++) {

                        statusesBean = new UserLine.StatusesBean();
                        JSONObject jsonObject = (JSONObject) statuses.get(i);
                        //获取user
                        JSONObject user = jsonObject.getJSONObject("user");
                        //获取昵称
                        String screen_name = user.getString("screen_name");
                        //用户头像地址
                        String profile_image_url = user.getString("profile_image_url");
                        //是否为加v用户
                        boolean verified = user.getBoolean("verified");
                        //微博内容
                        String text = jsonObject.getString("text");
                        //点赞
                        int attitudes_count = jsonObject.getInt("attitudes_count");
                        //评论数
                        int comments_count = jsonObject.getInt("comments_count");
                        //转发数
                        int reposts_count = jsonObject.getInt("reposts_count");
                        //发表时间
                        String created_at = jsonObject.getString("created_at");
                        //发表设备
                        String strSource = jsonObject.getString("source");
                        String source = SourceFormatUtil.format(strSource);

                        ////////////////////存放用户信息/////////////////////
                        UserLine.StatusesBean.UserBean statuesUser = new UserLine.StatusesBean.UserBean();
                        statuesUser.setScreen_name(screen_name);
                        statuesUser.setProfile_image_url(profile_image_url);
                        statuesUser.setVerified(verified);

                        statusesBean.setUser(statuesUser);
                        statusesBean.setText(text);
                        statusesBean.setAttitudes_count(attitudes_count);
                        statusesBean.setComments_count(comments_count);
                        statusesBean.setReposts_count(reposts_count);
                        //转换时间格式
                        DateFormat format = new DateFormat();
                        String newCreateTime = format.format(created_at);
                        statusesBean.setCreated_at(newCreateTime);
                        statusesBean.setSource(source);
                        lists.add(statusesBean);
                        adapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(InfoActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
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
