package com.laf.baggoods.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.laf.baggoods.R;
import com.laf.baggoods.ui.basic.listview.BaseListAdapter;
import com.laf.baggoods.ui.basic.listview.ViewHolderUtils;
import com.laf.baggoods.util.NewContainer;
import com.laf.model.HomeDataValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/3/14.
 */

public class LvHomeAdapter extends BaseListAdapter {

    private static final String TAG = "LvHomeAdapter";

    private static final int ITEM_TYPE_COUNT = 3;
    private static final int TYPE_VIDEO = 0;
    private static final int TYPE_VIEWPAGER = 1;
    private static final int TYPE_LAYOUT = 2;

    private ViewHolderUtils.ViewHolder mCurrent = null;

    public LvHomeAdapter(Context context, ArrayList<HomeDataValue> list) {
        super(context, list, ITEM_TYPE_COUNT);
    }

    @Override
    public int getItemViewType(int position) {
        return ((HomeDataValue) getItem(position)).type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        if (type == 1) Log.d(TAG, "type = " + type + " ; convertView = " + convertView);
        if (convertView == null) {
            switch (type) {
                case TYPE_VIDEO:
                    break;
                case TYPE_VIEWPAGER:
                    //因为只有一个轮播，所以只在convertView为空的时候初始化一次,之后不需要更新数据
                    convertView = inflater.inflate(
                            R.layout.item_ad_viewpager,
                            parent,
                            false);
                    //分别测试viewpager#onMeasure()和pageradapter#onPrimaryItem()调用次数，决定在哪里设置viewpager的高度
//                    convertView = new SlideViewPager(context);
//                    convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                    mCurrent = new ViewHolderUtils().build(convertView);
                    ViewPager pager = (ViewPager) getViewById(R.id.pager_ad);
                    List<String> list = NewContainer.list();
                    for (int i = 0; i < 5; i++) {
                        list.add("page " + ++i);
                    }
                    SlidePagerAdapter adapter = new SlidePagerAdapter(context, list);
                    pager.setAdapter(adapter);
//                    ((ViewPager)convertView).setAdapter(adapter);
                    break;
                case TYPE_LAYOUT:
                    convertView = inflater.inflate(
                            R.layout.item_brands_layout,
                            parent,
                            false);
                    break;
            }
        }
        mCurrent = new ViewHolderUtils().build(convertView);
        setViewHolder(position, (HomeDataValue) getItem(position), type);

        return convertView;
    }

    protected View getViewById(int id) {
        return mCurrent.getViewById(id);
    }

    protected void setViewHolder(int position, HomeDataValue bean, int type) {
        switch (type) {
            case TYPE_VIDEO:
                break;
            case TYPE_LAYOUT:
                TextView textView = (TextView) getViewById(R.id.txt_test);
                break;
        }
    }


}
