package com.chj.weiboone.weibo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.chj.weiboone.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片浏览适配器
 * Created by C on 2016/5/14.
 */
public class PhotoPagerAdapter extends PagerAdapter{
//    private static final int[] sDrawables = { R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
//            R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper };

    private Context mContext;
    private List<String> lists;

    public PhotoPagerAdapter(Context mContext, List<String> lists) {
        this.mContext = mContext;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mContext);
//        ImageView photoView = new ImageView(mContext);
//        photoView.setImageResource(sDrawables[position]);
//        photoView.setTag();
        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        Picasso.with(mContext)
                .load(lists.get(position))
                .placeholder(R.mipmap.default_big)
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {
                    }
                });

        // Now just add PhotoView to ViewPager and return it
//        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(photoView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
