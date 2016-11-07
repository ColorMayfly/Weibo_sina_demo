package com.chj.weiboone.weibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.entity.UserLine;
import com.chj.weiboone.weibo.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 我的微博适配器
 * Created by C on 2016/5/4.
 */
public class UserLineAdapter extends BaseAdapter{
    private Context mContext;
    private List<UserLine.StatusesBean> lists;

    public UserLineAdapter(Context mContext, List<UserLine.StatusesBean> lists) {
        this.mContext = mContext;
        this.lists = lists;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_info, parent, false);
            viewHolder.tvHead = (ImageView)convertView.findViewById(R.id.iv_head);
            viewHolder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tvTime = (TextView)convertView.findViewById(R.id.tv_time);
            viewHolder.tvSource = (TextView)convertView.findViewById(R.id.tv_source);
            viewHolder.tvText = (TextView)convertView.findViewById(R.id.tv_text);
            viewHolder.tvAttitudes = (TextView)convertView.findViewById(R.id.tv_attitudes);
            viewHolder.tvComments = (TextView)convertView.findViewById(R.id.tv_comments);
            viewHolder.tvReposts = (TextView)convertView.findViewById(R.id.tv_reposts);
            viewHolder.ivVerified = (ImageView)convertView.findViewById(R.id.iv_verified);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        UserLine.StatusesBean statusesBean = lists.get(position);

        UserLine.StatusesBean.UserBean user = statusesBean.getUser();
        viewHolder.tvName.setText(user.getScreen_name());
        String profile_image_url = user.getProfile_image_url();
        Picasso.with(mContext)
                .load(profile_image_url)
                .resize(50, 50)//转换图片以适应布局大小并减少内存占用
                .centerCrop()
                .transform(new CircleTransform())
                .placeholder(R.mipmap.ic_launcher)//Place holders-空白或者错误占位图片
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.tvHead);

        //是否加v
        if (user.isVerified()) {
            viewHolder.ivVerified.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivVerified.setVisibility(View.GONE);
        }

        viewHolder.tvText.setText(statusesBean.getText());
        viewHolder.tvAttitudes.setText(statusesBean.getAttitudes_count() +"");
        viewHolder.tvComments.setText(statusesBean.getComments_count() + "");
        viewHolder.tvTime.setText(statusesBean.getCreated_at());
        viewHolder.tvSource.setText(statusesBean.getSource());
        viewHolder.tvReposts.setText(statusesBean.getReposts_count() + "");


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
    }
}
