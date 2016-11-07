package com.chj.weiboone.weibo.util;

import android.content.Context; 
import android.util.AttributeSet; 
import android.widget.ListView; 
 /**
  * 解决ListView与ScrollView冲突
  * @author Administrator
  *
  */
public class MyListView extends ListView{ 
 
    public MyListView(Context context) { 
        super(context); 
        // TODO Auto-generated constructor stub 
    } 
    public MyListView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
        // TODO Auto-generated constructor stub 
    } 
    public MyListView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
        // TODO Auto-generated constructor stub 
    } 
    
    // 动态计算子控件在屏幕中所需占用的控件（也就是限制listView的宽度和高度）
    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        // TODO Auto-generated method stub 
        int expandSpec = MeasureSpec.makeMeasureSpec(  
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
         
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 
 
} 
