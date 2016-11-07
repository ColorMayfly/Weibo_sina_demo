package com.chj.weiboone.weibo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.activity.FollowersActivity;
import com.chj.weiboone.weibo.activity.FriendsActivity;
import com.chj.weiboone.weibo.activity.UserActivity;
import com.chj.weiboone.weibo.adapter.UserLineAdapter;
import com.chj.weiboone.weibo.entity.Info;
import com.chj.weiboone.weibo.entity.UserLine;
import com.chj.weiboone.weibo.util.DateFormat;
import com.chj.weiboone.weibo.util.MyListView;
import com.chj.weiboone.weibo.util.SourceFormatUtil;
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
 * 我的
 * Created by C on 2016/5/1.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

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
    private View myView;
    private AlertDialog alertDialog;
    private SwipeRefreshLayout swipeRefresh;
    private RequestParams params;
    private AsyncHttpClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (myView == null) {
            myView = inflater.inflate(R.layout.fragment_my, null);
            findView(myView);

            // 获取当前已保存过的 Token
            mAccessToken = AccessTokenKeeper.readAccessToken(getContext());
            String uid = mAccessToken.getUid();
            String access_token = mAccessToken.getToken();

            client = new AsyncHttpClient();
            params = new RequestParams();
            params.put("access_token", access_token);
            params.put("uid", uid);
            requestNet();
        }

        return myView;
    }

    /**
     * 初始化
     *
     * @param myView
     */
    private void findView(View myView) {
        final TextView tvTitle = (TextView) myView.findViewById(R.id.tv_title);
        tvTitle.setText("我");
        final ImageView tvSet = (ImageView) myView.findViewById(R.id.tv_set);
        tvSet.setVisibility(View.VISIBLE);
        tvSet.setOnClickListener(this);
        //下拉刷新
        swipeRefresh = (SwipeRefreshLayout) myView.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestNet();
            }
        });
        swipeRefresh.setColorSchemeColors(
                R.color.base_color,
                R.color.base_color,
                R.color.base_color,
                R.color.base_color);

        ivCover = (ImageView) myView.findViewById(R.id.iv_cover);
        ivProfile = (ImageView) myView.findViewById(R.id.iv_profile);
        tvScreenName = (TextView) myView.findViewById(R.id.tv_screen_name);
        tvLocation = (TextView) myView.findViewById(R.id.tv_location);
        llFollowers = (LinearLayout) myView.findViewById(R.id.ll_followers);
        llFriends = (LinearLayout) myView.findViewById(R.id.ll_friends);
        llStatuses = (LinearLayout) myView.findViewById(R.id.ll_statuses);
        llFavourites = (LinearLayout) myView.findViewById(R.id.ll_favourites);
        tvFollowers = (TextView) myView.findViewById(R.id.tv_followers);
        tvFriends = (TextView) myView.findViewById(R.id.tv_friends);
        tvStatuses = (TextView) myView.findViewById(R.id.tv_statuses);
        tvFavourites = (TextView) myView.findViewById(R.id.tv_favourites);
        lvMyNew = (MyListView) myView.findViewById(R.id.lv_my_new);
        lists = new ArrayList<>();
        adapter = new UserLineAdapter(getContext(), lists);
        lvMyNew.setAdapter(adapter);

        llFollowers.setOnClickListener(this);
        llFriends.setOnClickListener(this);
        llStatuses.setOnClickListener(this);
        llFavourites.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_followers://粉丝
                startActivity(new Intent(getContext(), FollowersActivity.class));
                break;
            case R.id.ll_friends://关注
                startActivity(new Intent(getContext(), FriendsActivity.class));
                break;
            case R.id.ll_statuses://微博
                startActivity(new Intent(getContext(), UserActivity.class));
                break;
            case R.id.ll_favourites://收藏
                startActivity(new Intent(getContext(), FollowersActivity.class));
                break;
            case R.id.tv_set://设置

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_setting, null);
                final TextView tvLogout = (TextView) dialogView.findViewById(R.id.tv_logout);
                final TextView tvExit = (TextView) dialogView.findViewById(R.id.tv_exit);
                final TextView tvCancel = (TextView) dialogView.findViewById(R.id.tv_cancel);

                tvLogout.setOnClickListener(this);
                tvExit.setOnClickListener(this);
                tvCancel.setOnClickListener(this);

                builder.setView(dialogView);
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();

                break;

            case R.id.tv_logout:
                alertDialog.dismiss();
                AccessTokenKeeper.clear(getContext());
                mAccessToken = new Oauth2AccessToken();
                getActivity().finish();
                break;
            case R.id.tv_exit:
                alertDialog.dismiss();
                getActivity().finish();
                break;

            case R.id.tv_cancel:
                alertDialog.dismiss();
                break;
            default:
                break;
        }
    }

    public void requestNet() {

        client.get(Constants.URL_USER, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("MyFragment", "onSuccess: ---" + response.toString());
                try {
                    //封面
                    String cover_image_phone = response.getString("cover_image_phone");
                    //头像大图
                    String avatar_large = response.getString("avatar_large");
                    //用户昵称
                    String screen_name = response.getString("screen_name");
                    //所在地
                    String location = response.getString("location");
                    //粉丝、关注、微博、收藏
                    String followers_count = response.getString("followers_count");
                    String friends_count = response.getString("friends_count");
                    String statuses_count = response.getString("statuses_count");
                    String favourites_count = response.getString("favourites_count");


                    Picasso.with(getContext()).load(cover_image_phone).into(ivCover);
                    Picasso.with(getContext()).load(avatar_large).transform(new CircleTransform()).into(ivProfile);
                    tvScreenName.setText(screen_name);
                    tvLocation.setText(location);
                    tvFollowers.setText(followers_count);
                    tvFriends.setText(friends_count);
                    tvStatuses.setText(statuses_count);
                    tvFavourites.setText(favourites_count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        client.get(Constants.URL_USER_LINE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("UserActivity", "onSuccess: --" + response.toString());
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
                Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        //停止刷新
        swipeRefresh.setRefreshing(false);
    }
}
