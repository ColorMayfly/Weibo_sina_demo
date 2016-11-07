package com.chj.weiboone.weibo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.activity.CommentActivity;
import com.chj.weiboone.weibo.activity.RepostInfoActivity;
import com.chj.weiboone.weibo.adapter.ListViewInfoAdapter;
import com.chj.weiboone.weibo.entity.Info;
import com.chj.weiboone.weibo.entity.User;
import com.chj.weiboone.weibo.entity.UserLine;
import com.chj.weiboone.weibo.util.DateFormat;
import com.chj.weiboone.weibo.util.NetWorkUtil;
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
 * 首页
 * Created by C on 2016/5/1.
 */
public class AllFragment extends Fragment implements RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    private List<Info.StatusesBean> lists;
    private Info.StatusesBean statusesBean;
    private ListViewInfoAdapter adapter;
    private RefreshListView refreshListView;
    private RefreshListView.OnRefreshListener listener;
    private int page = 1;
    private AsyncHttpClient client;
    private String access_token;
    private RequestParams params;
    //下拉刷新
    private static final int STATE_PULL = 1;
    //上拉加载
    private static final int STATE_LOAD = 2;
    private long itemLongId;
    private AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_all, null);

        refreshListView = (RefreshListView) homeView.findViewById(R.id.lv_refresh);
        refreshListView.setOnRefreshListener(this);

        lists = new ArrayList<>();
        client = new AsyncHttpClient();
        // 获取当前已保存过的 Token
        Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(getContext());
        access_token = oauth2AccessToken.getToken();
        params = new RequestParams();
        requestNet(STATE_PULL);

        if (adapter == null) {
            adapter = new ListViewInfoAdapter(lists, getContext());
        }
        refreshListView.setAdapter(adapter);
        refreshListView.setOnItemClickListener(this);
        refreshListView.setOnItemLongClickListener(this);

        return homeView;
    }

    /**
     * 网络请求
     */
    private void requestNet(final int state) {

        params.put("access_token", access_token);
        params.put("count", 20);
        params.put("page", 1);
        params.put("base_app", 0);
        client.get(Constants.URL_PUBLIC_LINE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("AllFragment", "onSuccess: --" + response.toString());
                try {
                    JSONArray statuses = (JSONArray) response.get("statuses");
                    for (int index = 0; index < statuses.length(); index++) {
                        statusesBean = new Info.StatusesBean();
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
                        //获取微博id
                        long id = jsonObject.getLong("id");
                        //转发数
                        int reposts_count = jsonObject.getInt("reposts_count");
                        //发表时间
                        String created_at = jsonObject.getString("created_at");
                        //发表设备
                        String strSource = jsonObject.getString("source");
                        String source = format(strSource);
                        //是否有图片
                        JSONArray pic_urls = jsonObject.getJSONArray("pic_urls");
                        List<Info.StatusesBean.PicUrlsBean> picUrlsBeans = new ArrayList<>();
                        if (pic_urls.length() != 0) {
                            for (int i = 0; i < pic_urls.length(); i++) {
                                JSONObject pics = (JSONObject) pic_urls.get(i);
                                Info.StatusesBean.PicUrlsBean picUrls = new Info.StatusesBean.PicUrlsBean();
                                picUrls.setThumbnail_pic(pics.getString("thumbnail_pic"));
                                picUrlsBeans.add(picUrls);
                            }
                        }
                        /**获取被转发的微博*/
                        final String s = jsonObject.toString();
                        if (s.contains("retweeted_status")) {
                            final JSONObject retweetedStatuse = jsonObject.getJSONObject("retweeted_status");
                            //转发的内容
                            final String reweetedText = retweetedStatuse.getString("text");
                            //原微博是否有图片
                            JSONArray pic_urls_retweeted = jsonObject.getJSONArray("pic_urls");
                            List<UserLine.StatusesBean.PicUrlsBean> picUrlsRetweeted = new ArrayList<>();
                            if (pic_urls_retweeted.length() != 0) {
                                for (int i = 0; i < pic_urls.length(); i++) {
                                    JSONObject pics = (JSONObject) pic_urls.get(i);
                                    UserLine.StatusesBean.PicUrlsBean picUrls = new UserLine.StatusesBean.PicUrlsBean();
                                    picUrls.setThumbnail_pic(pics.getString("thumbnail_pic"));
                                    picUrlsRetweeted.add(picUrls);
                                }
                            }
                            //原微博作者
                            final JSONObject retweetedUser = retweetedStatuse.getJSONObject("user");
                            //获取原微博作者昵称
                            final String screen_name_retweeted = retweetedUser.getString("screen_name");
                            /** 存放原微博信息 */
                            UserLine.StatusesBean retweetedStatusesBean = new UserLine.StatusesBean();
                            retweetedStatusesBean.setText(reweetedText);
                            UserLine.StatusesBean.UserBean retweetedUserBean = new UserLine.StatusesBean.UserBean();
                            retweetedUserBean.setScreen_name(screen_name_retweeted);
                            retweetedStatusesBean.setUser(retweetedUserBean);
                            retweetedStatusesBean.setPic_urls(picUrlsRetweeted);

                            statusesBean.setRetweeted_status(retweetedStatusesBean);
                        }


                        ////////////////////存放用户信息/////////////////////
                        User statuesUser = new User();
                        statuesUser.setScreen_name(screen_name);
                        statuesUser.setProfile_image_url(profile_image_url);
                        statuesUser.setVerified(verified);

                        statusesBean.setUser(statuesUser);
                        statusesBean.setText(text);
                        statusesBean.setAttitudes_count(attitudes_count);
                        statusesBean.setComments_count(comments_count);
                        statusesBean.setId(id);
                        statusesBean.setReposts_count(reposts_count);
                        //转换时间格式
                        DateFormat format = new DateFormat();
                        String newCreateTime = format.format(created_at);
                        statusesBean.setCreated_at(newCreateTime);
                        statusesBean.setSource(source);
                        statusesBean.setPic_urls(picUrlsBeans);

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
                Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
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
    public String format(String source) {
        String newSource;
        int start = source.indexOf("\">", 0);
        int end = source.indexOf("</a>", 0);
        if (start + 2 < end) {
            newSource = source.substring(start + 2, end);
        } else {
            newSource = "";
        }
        return newSource;
    }

    /**
     * listview的item点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Info.StatusesBean statusesBean = lists.get(position - 1);
        //微博id
        long itemId = statusesBean.getId();
        //赞
        int attitudes_count = statusesBean.getAttitudes_count();
        //评论
        int comments_count = statusesBean.getComments_count();
        //转发数
        int reposts_count = statusesBean.getReposts_count();
        //创建时间
        String created_at = statusesBean.getCreated_at();
        //设备
        String source = statusesBean.getSource();
        //文本内容
        String text = statusesBean.getText();
        //用户
        User user = statusesBean.getUser();
        //昵称
        String screen_name = user.getScreen_name();
        //是否加v
        boolean verified = user.isVerified();
        //头像
        String profile_image_url = user.getProfile_image_url();
        //是否带图片
        List<Info.StatusesBean.PicUrlsBean> pic_urls = statusesBean.getPic_urls();
        ArrayList<String> picsLists = new ArrayList<>();
        for (int i = 0; i < pic_urls.size(); i++) {
            String thumbnail_pic = pic_urls.get(i).getThumbnail_pic();
            picsLists.add(thumbnail_pic);
        }
        System.out.println("---item--id" + itemId);


        Intent intent = new Intent(getContext(), CommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("access_token", access_token);
        bundle.putLong("id", itemId);
        bundle.putInt("attitudes_count", attitudes_count);
        bundle.putInt("comments_count", comments_count);
        bundle.putInt("reposts_count", reposts_count);
        bundle.putString("created_at", created_at);
        bundle.putString("source", source);
        bundle.putString("text", text);
        bundle.putString("screen_name", screen_name);
        bundle.putBoolean("verified", verified);
        bundle.putString("profile_image_url", profile_image_url);
        bundle.putStringArrayList("picsLists", picsLists);

        /** 被转发的微博 */
        final UserLine.StatusesBean retweeted_status = statusesBean.getRetweeted_status();
        final Bundle retweetedBundle = new Bundle();
        if (retweeted_status != null) {
            final String retweetedText = retweeted_status.getText();
            final UserLine.StatusesBean.UserBean retweetedUserBean = retweeted_status.getUser();
            final String retweetedScreen_name = retweetedUserBean.getScreen_name();
            final List<UserLine.StatusesBean.PicUrlsBean> retweetedPic_urls = retweeted_status.getPic_urls();
            ArrayList<String> retweetedPics = new ArrayList<>();
            for (int i = 0; i < retweetedPic_urls.size(); i++) {
                final String thumbnail_pic = retweetedPic_urls.get(i).getThumbnail_pic();
                retweetedPics.add(thumbnail_pic);
            }
            bundle.putString("retweetedText", retweetedText);
            bundle.putString("retweetedScreen_name", retweetedScreen_name);
            bundle.putStringArrayList("retweetedPics", retweetedPics);
//            intent.putExtra("retweeted", retweetedBundle);
        }
//        else {
//            intent.putExtra("retweeted", "none");
//        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 长按事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_info, null);
        final TextView tvAdd = (TextView) dialogView.findViewById(R.id.tv_add);
        final TextView tvCancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        final TextView tvRepost = (TextView) dialogView.findViewById(R.id.tv_repost);

        tvAdd.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvRepost.setOnClickListener(this);

        Info.StatusesBean statusesBean = lists.get(position - 1);
        itemLongId = statusesBean.getId();

        builder.setView(dialogView);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

        return true;
    }

    /**
     * dialog点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(getContext());
        access_token = oauth2AccessToken.getToken();
        params = new RequestParams();
        client = new AsyncHttpClient();
        switch (v.getId()) {
            case R.id.tv_add:
                params.put("access_token", access_token);
                params.put("id", itemLongId);
                client.post(Constants.URL_FAVORITE_ADD, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(getContext(), "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.dismiss();
                break;
            case R.id.tv_cancel:
                params.put("access_token", access_token);
                params.put("id", itemLongId);
                client.post(Constants.URL_FAVORITE_CANCEL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Toast.makeText(getContext(), "取消收藏成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(getContext(), "取消收藏失败", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.dismiss();
                break;
            case R.id.tv_repost:
                final Intent intent = new Intent(getContext(), RepostInfoActivity.class);
                intent.putExtra("id", itemLongId);
                startActivityForResult(intent, 1);
                alertDialog.dismiss();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
