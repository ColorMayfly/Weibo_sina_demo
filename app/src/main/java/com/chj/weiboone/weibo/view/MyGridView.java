package com.chj.weiboone.weibo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决 GridView 与 ScrollView 冲突
 * @author Administrator
 *
 */
public class MyGridView extends GridView{

	public MyGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public MyGridView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
        // TODO Auto-generated constructor stub 
    } 
    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        // TODO Auto-generated method stub 
        int expandSpec = MeasureSpec.makeMeasureSpec(  
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
         
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 

}
