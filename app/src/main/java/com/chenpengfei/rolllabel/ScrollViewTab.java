package com.chenpengfei.rolllabel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chenpengfei on 2015/6/10.
 */
public class ScrollViewTab extends HorizontalScrollView{

    private LinearLayout tabsContainer;
    //单个tab的宽度
    private int tabWidth = 220;
    private Paint mPaint;
    private int left, top, right, buttom;
    private  int lineHeight = 12;
    private ViewPager viewPager;

    public ScrollViewTab(Context context) {
        super(context);
    }

    public ScrollViewTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initData(Context  context, String[] array, int screenWidth){
        setFillViewport(true);
        setHorizontalScrollBarEnabled(false);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        //linearlayout容器，tab添加到tabsContainer中
        tabsContainer = new LinearLayout(context);
        tabsContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setBackgroundColor(Color.RED);
        //如果所有tab的宽度小于屏幕的宽度就平分宽度
        if(screenWidth > tabWidth*array.length){
            tabWidth = screenWidth/array.length;
        }
        //tab
        for(int i = 0; i<array.length; i++){
            TextView tabTextView = new TextView(context);
            tabTextView.setTextSize(18);
            tabTextView.setBackgroundColor(Color.GREEN);
            tabTextView.setLayoutParams(new ViewGroup.LayoutParams(tabWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            tabTextView.setPadding(0, 40, 0, 40);
            tabTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            tabTextView.setText(array[i]);
            if(tabTextView.getParent()!=null)
                ((ViewGroup)tabTextView.getParent()).removeAllViews();
            tabTextView.setOnClickListener(new TabOnClickListener(i));
            tabsContainer.addView(tabTextView);
        }
        addView(tabsContainer);
    }

    public void setViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                left = i * tabWidth + (int)(tabWidth*v);
                invalidate();
                scroll();
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void scroll(){
        this.smoothScrollTo(left,0);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // 圈出一个矩形
        Rect rect = new Rect(left, top - lineHeight, right + left, buttom);
        canvas.drawRect(rect, mPaint); // 绘制该矩形
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置line的宽高
        top = getMeasuredHeight();
        right = tabWidth;
        buttom = top;
    }

    private class TabOnClickListener implements OnClickListener{

        private int position;

        public TabOnClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(position);
        }
    }
}
