package com.chenpengfei.rolllabel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  @author  Chenpengfei
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrollViewTab scrollViewTab = (ScrollViewTab) findViewById(R.id.scroll_view_id);
        String[] array = {"音乐", "互联网", "科技","财经" , "娱乐", "体育", "性"};
        scrollViewTab.initData(MainActivity.this, array, getScreenWidth());
        //pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ArrayList<View> viewList = new ArrayList<View>();
        for(int i = 0; i<array.length ; i++){
            View contentView = getLayoutInflater().inflate(R.layout.item_one, null);
            ((TextView)contentView.findViewById(R.id.cid)).setText("我就是" + array[i]);
            viewList.add(contentView);
        }
        viewPager.setAdapter(new ViewPagerAdapter(viewList));
        scrollViewTab.setViewPager(viewPager);
    }

    //得到屏幕的宽度
    private int getScreenWidth(){
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
}
