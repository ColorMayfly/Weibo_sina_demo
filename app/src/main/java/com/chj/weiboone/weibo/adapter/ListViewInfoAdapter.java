package com.chj.weiboone.weibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.activity.FollowersActivity;
import com.chj.weiboone.weibo.activity.InfoActivity;
import com.chj.weiboone.weibo.activity.PhotoViewActivity;
import com.chj.weiboone.weibo.entity.Info;
import com.chj.weiboone.weibo.entity.User;
import com.chj.weiboone.weibo.entity.UserLine;
import com.chj.weiboone.weibo.util.IntentSpan;
import com.chj.weiboone.weibo.view.CircleTransform;
import com.chj.weiboone.weibo.view.MyGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C on 2016/5/1.
 */
public class ListViewInfoAdapter extends BaseAdapter{
    private List<Info.StatusesBean> lists;
    private Context mContext;
    private ImageView ivOnePic;
    private MyGridView gridviewPics;
    private ImageView tvHead;
    private TextView tvName;
    private TextView tvTime;
    private TextView tvSource;
    private TextView tvText;
    private TextView tvAttitudes;
    private TextView tvComments;
    private TextView tvReposts;
    private ImageView ivVerified;
    //图片集合
    private ArrayList<String> picsUrl;
    private LinearLayout llRetweeted;
    private TextView tvRetweetedName;
    private TextView tvRetweetedText;
    private MyGridView retweetedGridview;

    public ListViewInfoAdapter(List<Info.StatusesBean> lists, Context mContext) {
        this.lists = lists;
        this.mContext = mContext;
        picsUrl = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Info.StatusesBean statusesBean = lists.get(position);
        List<Info.StatusesBean.PicUrlsBean> pic_urls = statusesBean.getPic_urls();
        //发布的图片
        int picSize = pic_urls.size();
        if (picSize == 0) {
            //无图片
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_info, null);
        } else if (picSize == 1) {
            //只有一张图片
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_info_one_pic, null);
            ivOnePic = (ImageView)convertView.findViewById(R.id.iv_one_pic);
        } else {
            //多张图片
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_info_gridview_pics, null);
            gridviewPics = (MyGridView)convertView.findViewById(R.id.gridview_pics);
        }
        tvHead = (ImageView)convertView.findViewById(R.id.iv_head);
        tvName = (TextView)convertView.findViewById(R.id.tv_name);
        tvTime = (TextView)convertView.findViewById(R.id.tv_time);
        tvSource = (TextView)convertView.findViewById(R.id.tv_source);
        tvText = (TextView)convertView.findViewById(R.id.tv_text);
        tvAttitudes = (TextView)convertView.findViewById(R.id.tv_attitudes);
        tvComments = (TextView)convertView.findViewById(R.id.tv_comments);
        tvReposts = (TextView)convertView.findViewById(R.id.tv_reposts);
        ivVerified = (ImageView)convertView.findViewById(R.id.iv_verified);
        /** 原微博 */
        llRetweeted = (LinearLayout) convertView.findViewById(R.id.layout_retweeter_gridview_pics);
        tvRetweetedName = (TextView) convertView.findViewById(R.id.tv_retweeteed_name);
        tvRetweetedText = (TextView) convertView.findViewById(R.id.tv_retweeter_text);
        retweetedGridview = (MyGridView) convertView.findViewById(R.id.gridview_retweeter_pics);

        //拥有一张图片
        if (picSize == 1) {
            Info.StatusesBean.PicUrlsBean picUrlsBean = pic_urls.get(0);
            String thumbnail_pic = picUrlsBean.getThumbnail_pic();
            String large_pic = thumbnail_pic.replace("thumbnail", "large");
            if (picsUrl != null) {
                picsUrl.clear();
            }
            picsUrl.add(large_pic);
            ivOnePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PhotoViewActivity.class);
                    intent.putStringArrayListExtra("picsUrl", picsUrl);
                    mContext.startActivity(intent);
                }
            });
            Picasso.with(mContext)
                    .load(large_pic)
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.mipmap.default_small)
                    .error(R.mipmap.default_small)
                    .into(ivOnePic);
        }
        //拥有多张图片
        if (picSize > 1) {
            //存放图片网址集合
            List<String> lists = new ArrayList<>();
            //gridview适配器
            GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext, lists);
            gridviewPics.setClickable(false);
            gridviewPics.setFocusable(false);
            gridviewPics.setAdapter(gridViewAdapter);
            if (picsUrl != null) {
                picsUrl.clear();
            }
            for (int i = 0; i < picSize; i++) {
                String thumbnail_pic = pic_urls.get(i).getThumbnail_pic();
                String large_pic = thumbnail_pic.replace("thumbnail", "large");
                lists.add(large_pic);
                picsUrl.add(large_pic);
                gridViewAdapter.notifyDataSetChanged();
            }
            gridviewPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, PhotoViewActivity.class);
                    intent.putStringArrayListExtra("picsUrl", picsUrl);
                    mContext.startActivity(intent);
                }
            });
        }

        final User user = statusesBean.getUser();
        tvName.setText(user.getScreen_name());
        tvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, InfoActivity.class);
                intent.putExtra("screen_name", user.getScreen_name());
                mContext.startActivity(intent);
            }
        });
        String profile_image_url = user.getProfile_image_url();
        Picasso.with(mContext)
                .load(profile_image_url)
                .resize(50, 50)//转换图片以适应布局大小并减少内存占用
                .centerCrop()
                .transform(new CircleTransform())
                .placeholder(R.mipmap.head)//Place holders-空白或者错误占位图片
                .error(R.mipmap.head)
                .into(tvHead);

        //是否加v
        if (user.isVerified()) {
            ivVerified.setVisibility(View.VISIBLE);
        } else {
            ivVerified.setVisibility(View.GONE);
        }

        //文本内容
        String text = statusesBean.getText();
        tvText.setText(text);
        /**
         * 提取热点词汇  #...#
         */
//        addHotLink(mContext, tvText, text);

        tvAttitudes.setText(statusesBean.getAttitudes_count() + "");
        tvComments.setText(statusesBean.getComments_count() + "");
        tvTime.setText(statusesBean.getCreated_at());
        tvSource.setText(statusesBean.getSource());
        tvReposts.setText(statusesBean.getReposts_count() + "");

        /** 被转发的微博 */
        final UserLine.StatusesBean retweeted_status = statusesBean.getRetweeted_status();
        if (retweeted_status != null) {
            llRetweeted.setVisibility(View.VISIBLE);
            //原微博内容
            final String retweetedText = retweeted_status.getText();
            tvRetweetedText.setText("\t" + retweetedText);
            //是否有图片
            final List<UserLine.StatusesBean.PicUrlsBean> retweeted_statusPic_urls = retweeted_status.getPic_urls();
            if (retweeted_statusPic_urls.size() != 0) {
                retweetedGridview.setVisibility(View.VISIBLE);
                List<String> retweetedPics = new ArrayList<>();
                for (int i = 0; i < retweeted_statusPic_urls.size(); i++) {
                    final UserLine.StatusesBean.PicUrlsBean picUrlsBean = retweeted_statusPic_urls.get(i);
                    String thumbnail_pic = picUrlsBean.getThumbnail_pic();
                    String large_pic = thumbnail_pic.replace("thumbnail", "large");
                    retweetedPics.add(large_pic);
                }
                GridViewAdapter retweetedGridViewAdapter = new GridViewAdapter(mContext, retweetedPics);
                retweetedGridview.setFocusable(false);
                retweetedGridview.setClickable(false);
                retweetedGridview.setAdapter(retweetedGridViewAdapter);
            } else {
                retweetedGridview.setVisibility(View.GONE);
            }
            //原微博作者
            final UserLine.StatusesBean.UserBean retweetedUser = retweeted_status.getUser();
            final String retweetedUserScreen_name = retweetedUser.getScreen_name();
            tvRetweetedName.setText("@" + retweetedUserScreen_name);
            llRetweeted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(mContext, InfoActivity.class);
                    intent.putExtra("screen_name", retweetedUserScreen_name);
                    mContext.startActivity(intent);
                }
            });
        } else {
            llRetweeted.setVisibility(View.GONE);
        }

        return convertView;
    }



    class ViewHolder {
        ImageView tvHead;//头像
        TextView tvName;//昵称
        TextView tvTime;//发博日期
        TextView tvSource;//发博设备
        TextView tvText;//内容
        TextView tvAttitudes;//点赞
        TextView tvComments;//评论
        TextView tvReposts;//转发
        ImageView ivVerified;//是否加v认证

        ImageView ivOnePic;//一张图片的布局
        GridView gridviewPics;//gridview布局
    }

    /**
     * 添加超链接    #...#
     *
     * @param context 上下文
     * @param view TextView
     * @param text 超链接文本
     */
    public void addHotLink(final Context context, TextView view, String text) {
//        SpannableStringBuilder style = new SpannableStringBuilder(text);
//        //设置背景颜色
////            style.setSpan(new BackgroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        //设置文字颜色
//        style.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        /**
         * 提取热点词汇  #...#
         */
        int startHot = text.indexOf("#");
        int endHot = text.indexOf("#", startHot + 1) + 1;
        /**
         * 提取at @某某
         */

        if (startHot + 1 < endHot) {
            SpannableString linkStr = new SpannableString(text);
            final Intent intent = new Intent();
            intent.setClass(context, FollowersActivity.class);
            linkStr.setSpan(new IntentSpan(new View.OnClickListener() {
                public void onClick(View view) {
                    context.startActivity(intent);
//                    view.setForeground(new ColorDrawable(Color.RED));
                }
            }), startHot, endHot, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.setText(linkStr);
            view.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            view.setText(text);
        }


    }
}
