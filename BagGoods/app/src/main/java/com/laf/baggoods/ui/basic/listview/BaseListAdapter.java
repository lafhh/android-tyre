package com.laf.baggoods.ui.basic.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by apple on 2017/3/14.
 */

public abstract class BaseListAdapter extends BaseAdapter {
    private static final String TAG = "BaseListAdapter";
    protected static final int VIEW_TYPE_SINGLE = 1;

    protected LayoutInflater inflater;
    protected Context context;
    protected List<?> list;
    private int viewTypeCount = VIEW_TYPE_SINGLE;

    public BaseListAdapter(Context context, List<?> list, int count) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        viewTypeCount = count;
    }

    public BaseListAdapter(LayoutInflater inflater, List<?> list, int count) {
        this.context = inflater.getContext();
        this.inflater = inflater;
        this.list = list;
        viewTypeCount = count;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return viewTypeCount;
    }
}
