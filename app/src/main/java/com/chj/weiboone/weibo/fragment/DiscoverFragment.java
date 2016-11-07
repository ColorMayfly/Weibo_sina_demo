package com.chj.weiboone.weibo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.AccessTokenKeeper;
import com.chj.weiboone.weibo.Constants;
import com.chj.weiboone.weibo.util.PinYinUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 发现
 * Created by C on 2016/5/1.
 */
public class DiscoverFragment extends Fragment{


    private EditText search;
    private String access_token;
    private RequestParams params;
    private List<String> names;
    private ListView listView;
    private DiscoverAdapter adapter;
    private View discoverView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (discoverView == null) {
            discoverView = inflater.inflate(R.layout.fragment_discover, null);
            final TextView tvTitle = (TextView) discoverView.findViewById(R.id.tv_title);
            tvTitle.setText("发现");
            search = (EditText) discoverView.findViewById(R.id.discover);
            listView = (ListView) discoverView.findViewById(R.id.listview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    search.setText("");
                    names.clear();
                    adapter.notifyDataSetChanged();


                }
            });

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    System.out.println("---beforeTextChanged");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    System.out.println("---onTextChanged");
                    names = new ArrayList<>();
                    try {
                        // 获取当前已保存过的 Token
                        Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(getContext());
                        access_token = oauth2AccessToken.getToken();
                        params = new RequestParams();
                        params.put("access_token", access_token);
                        //汉字转换拼音
                        final String pinYin = PinYinUtil.getPinYin(s.toString());
//                        String encode = URLEncoder.encode(s.toString(), "utf-8");
                        String encode = URLEncoder.encode(pinYin, "utf-8");
                        System.out.println("---encode" + encode);
                        params.put("q", encode);
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(Constants.URL_DISCOVER, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                System.out.println("---JSONObject" + response.toString());
                            }

                            /**
                             * 此方法有效
                             * [
                             {
                             "uid":1249159055,
                             "followers_count":50139537,
                             "screen_name":"苏芩"
                             }]
                             *
                             * @param statusCode
                             * @param headers
                             * @param response
                             */
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                super.onSuccess(statusCode, headers, response);
                                System.out.println("---JSONArray" + response.toString());
                                int length = response.length();
                                for (int i = 0; i < length; i++) {
                                    try {
                                        JSONObject jsonObject = (JSONObject) response.get(i);
                                        //获取uid
                                        String uid = jsonObject.getString("uid");
                                        //获取screen_name
                                        String screen_name = jsonObject.getString("screen_name");
                                        //获取粉丝数
                                        final String followers_count = jsonObject.getString("followers_count");

                                        names.add(screen_name);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                adapter = new DiscoverAdapter();
                                listView.setAdapter(adapter);

                                for (int i = 0; i < names.size(); i++) {
                                    System.out.println("---" + names.get(i));
                                }
                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    System.out.println("---afterTextChanged");
                    if (search.getText().toString().isEmpty()) {
                        names.clear();
                    }
                }
            });
        }

        return discoverView;
    }

    /**
     * 适配器
     */
    class DiscoverAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return names.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.discover_adapter, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_name);
            textView.setText(names.get(position));
            return convertView;
        }
    }
    
}
