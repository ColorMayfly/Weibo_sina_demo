package com.chj.weiboone.weibo.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.adapter.CommentAdapter;
import com.chj.weiboone.weibo.adapter.GridViewAdapter;
import com.chj.weiboone.weibo.entity.Comments;
import com.chj.weiboone.weibo.util.DateFormat;
import com.chj.weiboone.weibo.util.MyListView;
import com.chj.weiboone.weibo.util.SystemBarTintManager;
import com.chj.weiboone.weibo.view.CircleTransform;
import com.chj.weiboone.weibo.view.MyGridView;
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
 * 评论页
 * Created by C on 2016/5/15.
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestParams params;
    private AsyncHttpClient client;
    private ImageView ivHead;
    private TextView tvName;
    private TextView tvTime;
    private TextView tvSource;
    private TextView tvText;
    private TextView tvAttitudes;
    private TextView tvComments;
    private TextView tvReposts;
    private ImageView ivVerified;
    private ImageView ivOnePic;
    private MyGridView gridviewPics;
    private MyListView listView;
    private String access_token1;
    private long id;
    private List<Comments.CommentsBean> lists = new ArrayList<>();
    private CommentAdapter adapter;
    private EditText etComment;
    private Button btnSendComment;
    private SystemBarTintManager tintManager;
    private LinearLayout llNoComments;
    private LinearLayout llRetweeted;
    private TextView tvRetweetedName;
    private TextView tvRetweetedText;
    private MyGridView retweetedGridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initWindow();
        initView();
        addContents();
        requestNet();
    }

    private void initView() {
        final TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("评论");
        ivHead = (ImageView)findViewById(R.id.iv_head);
        tvName = (TextView)findViewById(R.id.tv_name);
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvSource = (TextView)findViewById(R.id.tv_source);
        tvText = (TextView)findViewById(R.id.tv_text);
        tvAttitudes = (TextView)findViewById(R.id.tv_attitudes);
        tvComments = (TextView)findViewById(R.id.tv_comments);
        tvReposts = (TextView)findViewById(R.id.tv_reposts);
        ivVerified = (ImageView)findViewById(R.id.iv_verified);
        ivOnePic = (ImageView)findViewById(R.id.iv_one_pic);
        gridviewPics = (MyGridView)findViewById(R.id.gridview_pics);
        listView = (MyListView) findViewById(R.id.lv_comments);
        etComment = (EditText) findViewById(R.id.et_commment);
        btnSendComment = (Button) findViewById(R.id.btn_send_comment);
        btnSendComment.setOnClickListener(this);
        adapter = new CommentAdapter(this, lists);
        listView.setAdapter(adapter);

        //无评论
        llNoComments = (LinearLayout) findViewById(R.id.ll_no_comments);
        /** 转发微博 */
        llRetweeted = (LinearLayout) findViewById(R.id.layout_retweeter_gridview_pics);
        tvRetweetedName = (TextView) findViewById(R.id.tv_retweeteed_name);
        tvRetweetedText = (TextView) findViewById(R.id.tv_retweeter_text);
        retweetedGridview = (MyGridView) findViewById(R.id.gridview_retweeter_pics);

    }

    /**
     * 微博内容
     */
    private void addContents() {
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
//        Bundle bundle = intent.getBundleExtra("auth");
        //access_token
        access_token1 = bundle.getString("access_token");
        //微博id
        id = bundle.getLong("id");
        //赞
        final int attitudes_count = bundle.getInt("attitudes_count");
        tvAttitudes.setText(attitudes_count + "");
        //评论
        final int comments_count = bundle.getInt("comments_count");
        tvComments.setText(comments_count + "");
        //转发
        final int reposts_count = bundle.getInt("reposts_count");
        tvReposts.setText(reposts_count + "");
        //创建时间
        final String created_at = bundle.getString("created_at");
        tvTime.setText(created_at);
        //设备
        final String source = bundle.getString("source");
        tvSource.setText(source);
        //内容
        final String text = bundle.getString("text");
        tvText.setText(text);
        //昵称
        final String screen_name = bundle.getString("screen_name");
        tvName.setText(screen_name);
        //是否加v
        final boolean verified = bundle.getBoolean("verified");
        if (verified) {
            ivVerified.setVisibility(View.VISIBLE);
        } else {
            ivVerified.setVisibility(View.GONE);
        }
        //头像
        final String profile_image_url = bundle.getString("profile_image_url");
        Picasso.with(this)
                .load(profile_image_url)
                .resize(50, 50)
                .centerCrop()
                .transform(new CircleTransform())
                .placeholder(R.mipmap.head)
                .error(R.mipmap.head)
                .into(ivHead);
        //内容中的图片
        final ArrayList<String> picsLists = bundle.getStringArrayList("picsLists");
        //一张图片
        if (picsLists.size() == 1) {
            //图片网址
            final String s = picsLists.get(0);
            ivOnePic.setVisibility(View.VISIBLE);
            ivOnePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CommentActivity.this, PhotoViewActivity.class);
                    intent.putStringArrayListExtra("picsUrl", picsLists);
                    CommentActivity.this.startActivity(intent);
                }
            });
            Picasso.with(this)
                    .load(s.replace("thumbnail", "large"))
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(ivOnePic);
        }
        //多张图片
        if (picsLists.size() > 1) {
            gridviewPics.setVisibility(View.VISIBLE);
            final ArrayList<String> lists = new ArrayList<>();
            for (int i = 0; i < picsLists.size(); i++) {
                lists.add(picsLists.get(i).replace("thumbnail", "large"));
            }
            GridViewAdapter gridViewAdapter = new GridViewAdapter(this, lists);
            gridviewPics.setAdapter(gridViewAdapter);
            gridviewPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CommentActivity.this, PhotoViewActivity.class);
                    intent.putStringArrayListExtra("picsUrl", lists);
                    CommentActivity.this.startActivity(intent);
                }
            });
        }
        /** 转发内容 */
        //作者
        final String retweetedScreen_name = bundle.getString("retweetedScreen_name");
        //内容
        final String retweetedText = bundle.getString("retweetedText");
        //图片
        final ArrayList<String> retweetedPics = bundle.getStringArrayList("retweetedPics");
        if (retweetedScreen_name != null && !retweetedScreen_name.equals("")) {
            if (retweetedScreen_name != null || retweetedScreen_name.equals("")) {
                llRetweeted.setVisibility(View.VISIBLE);
                tvRetweetedName.setText("@" + retweetedScreen_name);
                tvRetweetedText.setText(retweetedText);
                if (retweetedPics.size() != 0) {
                    retweetedGridview.setVisibility(View.VISIBLE);
                    final ArrayList<String> picsUrl = new ArrayList<>();
                    for (int i = 0; i < retweetedPics.size(); i++) {
                        final String replaceUrl = retweetedPics.get(i).replace("thumbnail", "large");
                        picsUrl.add(replaceUrl);
                    }
                    GridViewAdapter gridViewAdapter = new GridViewAdapter(this, picsUrl);
                    retweetedGridview.setAdapter(gridViewAdapter);
                    retweetedGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(CommentActivity.this, PhotoViewActivity.class);
                            intent.putStringArrayListExtra("picsUrl", picsUrl);
                            CommentActivity.this.startActivity(intent);
                        }
                    });
                } else {
                    retweetedGridview.setVisibility(View.GONE);
                }
            } else {
                llRetweeted.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 网络请求
     */
    private void requestNet() {
        System.out.println("---long" + id);
        client = new AsyncHttpClient();
        params = new RequestParams();
        // 获取当前已保存过的 Token
        Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(this);
        String token = oauth2AccessToken.getToken();
        params.put("access_token", token);
        params.put("id", id);
        params.put("count", 10);
        client.get(Constants.URL_COMMENTS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
              System.out.println("---response" + response.toString());
                try {
                    final JSONArray comments = response.getJSONArray("comments");
                    for (int i = 0; i < comments.length(); i++) {
                        final JSONObject jsonObject = (JSONObject) comments.get(i);
                        //评论内容
                        final String text = jsonObject.getString("text");
                        //设备
                        final String source = jsonObject.getString("source");
                        //时间
                        final String created_at = jsonObject.getString("created_at");
                        //转换时间格式
                        DateFormat format = new DateFormat();
                        String newCreateTime = format.format(created_at);
                        //评论人
                        final JSONObject user = jsonObject.getJSONObject("user");
                        //昵称
                        final String screen_name = user.getString("screen_name");
                        //头像
                        final String profile_image_url = user.getString("profile_image_url");

                        Comments.CommentsBean commentsBean = new Comments.CommentsBean();
                        commentsBean.setText(text);
                        commentsBean.setSource(format(source));
                        commentsBean.setCreated_at(newCreateTime);
                        final Comments.CommentsBean.UserBean userBean = new Comments.CommentsBean.UserBean();
                        userBean.setScreen_name(screen_name);
                        userBean.setProfile_image_url(profile_image_url);
                        commentsBean.setUser(userBean);

                        lists.add(commentsBean);
                        adapter.notifyDataSetChanged();
                    }
                    //是否展示无评论页面
                    if (lists.size() == 0) {
                        llNoComments.setVisibility(View.VISIBLE);
                    } else {
                        llNoComments.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("---onFailure" + errorResponse.toString());
            }
        });
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
     * 发表评论
     * @param v
     */
    @Override
    public void onClick(View v) {
        final String comment = etComment.getText().toString();
        if (comment.isEmpty()) {
            Toast.makeText(CommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
        } else {
            params = new RequestParams();
            Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(this);
            String access_token = oauth2AccessToken.getToken();
            params.put("access_token", access_token);
            params.put("id", id);
            params.put("comment", comment);
            client = new AsyncHttpClient();
            client.post(Constants.URL_SEND_COMMENT, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    etComment.setText("");
                    lists.clear();
                    requestNet();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(CommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    System.out.println("---onFailure" + errorResponse.toString());
                }
            });
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
