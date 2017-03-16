package com.laf.baggoods.ui.basic.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by apple on 2017/3/14.
 */

public abstract class BasicListAdapter<T> extends BaseListAdapter {
    private int layoutId;
    private ViewHolderUtils viewHolderUtils;
    private ViewHolderUtils.ViewHolder mCurrent = null;

    public BasicListAdapter(Context context,
                            List<T> list,
                            int layoutId,
                            int viewTypeCount) {
        super(context, list, viewTypeCount);
        this.layoutId = layoutId;
    }

    public BasicListAdapter(LayoutInflater inflater,
                            List<T> list,
                            int layoutId,
                            int viewTypeCount) {
        super(inflater, list, viewTypeCount);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, parent, false);
        }
        mCurrent = new ViewHolderUtils().build(convertView);
        setViewHolder(position, (T) getItem(position));
        return convertView;
    }

    protected View getViewById(int id) {
        return mCurrent.getViewById(id);
    }

    protected abstract void setViewHolder(int position, T bean);

}
