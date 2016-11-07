package com.chj.weiboone.weibo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chj.weiboone.R;
import com.chj.weiboone.weibo.entity.Friendships;
import com.chj.weiboone.weibo.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * 粉丝列表适配器
 *
 * Created by C on 2016/5/2.
 */
public class FollowersAdapter extends BaseAdapter{
    private Context mContext;
    private List<Friendships.UsersBean> lists;

    public FollowersAdapter(Context mContext, List<Friendships.UsersBean> lists) {
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
            //注意：此处改为inflate(R.layout.adapter_listview_followers, parent, false)会出问题
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_followers, null);
            viewHolder.ivHead = (ImageView)convertView.findViewById(R.id.iv_head);
            viewHolder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tvDescription = (TextView)convertView.findViewById(R.id.tv_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Friendships.UsersBean usersBean = lists.get(position);
        String avatar_hd = usersBean.getAvatar_hd();
        Picasso.with(mContext)
                .load(avatar_hd)
                .resize(70, 70)
                .centerCrop()
                .transform(new CircleTransform())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.ivHead);
        String screen_name = usersBean.getScreen_name();
        Log.i("FollowersAdapter", "screen_name: ---" + screen_name);
        viewHolder.tvName.setText(usersBean.getScreen_name());
        viewHolder.tvDescription.setText(usersBean.getDescription());

        return convertView;
    }

    class ViewHolder {
        ImageView ivHead;
        TextView tvName;
        TextView tvDescription;
    }
}
