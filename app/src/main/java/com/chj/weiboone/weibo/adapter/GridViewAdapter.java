package com.chj.weiboone.weibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chj.weiboone.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * gridview适配器
 *
 * Created by C on 2016/5/8.
 */
public class GridViewAdapter extends BaseAdapter{
    private Context mContext;
    private List<String> lists;

    public GridViewAdapter(Context mContext, List<String> lists) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_adapter, null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_gridview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Picasso.with(mContext)
                .load(lists.get(position))
//                .resize(100, 100)
                .fit()
                .placeholder(R.mipmap.default_small)
                .error(R.mipmap.default_small)
                .into(viewHolder.imageView);

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
    }
}
