package com.chj.weiboone.weibo.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.adapter.UserLineAdapter;
import com.chj.weiboone.weibo.entity.UserLine;
import com.chj.weiboone.weibo.util.DateFormat;
import com.chj.weiboone.weibo.util.SourceFormatUtil;
import com.chj.weiboone.weibo.util.SystemBarTintManager;
import com.chj.weiboone.weibo.view.RefreshListView;
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
 * 我发布的微博
 */
public class UserActivity extends AppCompatActivity implements RefreshListView.OnRefreshListener {

    private RefreshListView refreshListView;
    private List<UserLine.StatusesBean> lists;
    private UserLineAdapter adapter;
    private AsyncHttpClient client;
    private String access_token;
    private RequestParams params;
    //下拉刷新
    private static final int STATE_PULL = 1;
    //上拉加载
    private static final int STATE_LOAD = 2;
    private UserLine.StatusesBean statusesBean;
    private String uid;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initWindow();
        initView();
        setAdapter();

        initNet();

    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        lists = new ArrayList<>();
        adapter = new UserLineAdapter(this, lists);
        refreshListView.setAdapter(adapter);
        refreshListView.setOnRefreshListener(this);
    }

    /**
     * 初始化
     */
    private void initView() {
        final TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("我的微博");
        refreshListView = (RefreshListView) findViewById(R.id.lv_refresh);
    }


    private void initNet() {
        client = new AsyncHttpClient();
        // 获取当前已保存过的 Token
        Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(this);
        access_token = oauth2AccessToken.getToken();
        uid = oauth2AccessToken.getUid();
        Log.i("UserActivity", "initNet: ---" + access_token);
        Log.i("UserActivity", "initNet: ---" + uid);
        params = new RequestParams();

        requestNet(STATE_PULL);
    }

    /**
     * 网络请求
     */
    private void requestNet(final int state) {

        params.put("access_token", access_token);
        params.put("uid", uid);

        client.get(Constants.URL_USER_LINE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("UserActivity", "onSuccess: --" + response.toString());
                try {
                    JSONArray statuses = (JSONArray) response.get("statuses");

                    for (int index = 0; index < statuses.length(); index++) {
                        statusesBean = new UserLine.StatusesBean();
                        JSONObject jsonObject = (JSONObject) statuses.get(index);
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

                        if (state == STATE_PULL) {
                            lists.add(0, statusesBean);
                        } else {
                            lists.add(statusesBean);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    refreshListView.hideFooterView();
                    refreshListView.hideHeaderView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                refreshListView.hideFooterView();
                refreshListView.hideHeaderView();
                Toast.makeText(UserActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onDownPullRefresh() {
        requestNet(STATE_PULL);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadingMore() {
        requestNet(STATE_LOAD);
    }

    /**
     * 微博来源格式转换(设备)
     *
     * @param source
     * @return
     */
//    public String format(String source) {
//        String newSource;
//        int start = source.indexOf("\">", 0);
//        int end = source.indexOf("</a>", 0);
//        if (start + 2 < end) {
//            newSource = source.substring(start + 2, end);
//        } else {
//            newSource = "";
//        }
//        return newSource;
//    }


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
