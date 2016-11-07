package com.chj.weiboone.weibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.entity.Comments;
import com.chj.weiboone.weibo.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by C on 2016/5/16.
 */
public class CommentAdapter extends BaseAdapter{
    private Context mContext;
    private List<Comments.CommentsBean> lists;

    public CommentAdapter(Context mContext, List<Comments.CommentsBean> lists) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.ivPic = (ImageView) convertView.findViewById(R.id.tv_pic);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvText = (TextView) convertView.findViewById(R.id.tv_text);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tvSource = (TextView) convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final Comments.CommentsBean commentsBean = lists.get(position);
        viewHolder.tvTime.setText(commentsBean.getCreated_at());
        viewHolder.tvSource.setText(commentsBean.getSource());
        viewHolder.tvText.setText(commentsBean.getText());
        final Comments.CommentsBean.UserBean user = commentsBean.getUser();
        viewHolder.tvName.setText(user.getScreen_name());
        Picasso.with(mContext)
                .load(user.getProfile_image_url())
                .transform(new CircleTransform())
                .into(viewHolder.ivPic);

        return convertView;
    }
    class ViewHolder {
        ImageView ivPic;
        TextView tvName;
        TextView tvText;
        TextView tvSource;
        TextView tvTime;
    }
}
