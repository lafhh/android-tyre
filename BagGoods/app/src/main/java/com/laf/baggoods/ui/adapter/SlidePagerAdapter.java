package com.laf.baggoods.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.laf.baggoods.R;

import java.util.List;

/**
 * Created by apple on 2017/3/15.
 */

public class SlidePagerAdapter extends PagerAdapter {
    private static final String TAG = "SlidePagerAdapter";

    private Context context;
    private List<String> list;

    public SlidePagerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int length = list.size();
        String url = list.get(position % length);
        Log.d(TAG, "instantiateItem()=====" + url);
        //加载图片,调整图片大小
        ImageView img = createImageView(container, url);
//        TextView img = createTextView(container, url);
        return img;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        //每滑动一次调用3次
//        ImageView view = (ImageView) object;
//        int height = view.getDrawable().getIntrinsicHeight();
    }

    private ImageView createImageView(ViewGroup parent, String url) {
        ImageView img = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        img.setAdjustViewBounds(true);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        //假数据
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            img.setImageDrawable(context.getResources().getDrawable(R.drawable.brand_default, context.getTheme()));
        } else {
            img.setImageDrawable(context.getResources().getDrawable(R.drawable.brand_default));
        }
        parent.addView(img, params);
        return img;
    }

    private TextView createTextView(ViewGroup parent, String text) {
        TextView txt = new TextView(context);

        txt.setText(text);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int height = (int) (metrics.heightPixels * 0.3);
        Log.d(TAG, "createTextView()==" + String.valueOf(height));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height);

        txt.setLayoutParams(params);
        parent.addView(txt);
        return txt;
    }
}
