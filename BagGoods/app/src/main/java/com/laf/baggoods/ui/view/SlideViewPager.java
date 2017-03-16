package com.laf.baggoods.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * Created by apple on 2017/3/16.
 */

public class SlideViewPager extends ViewPager {

    public SlideViewPager(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("laf", "onMeasure()");//每滑动一次就调用一次
    }
}
