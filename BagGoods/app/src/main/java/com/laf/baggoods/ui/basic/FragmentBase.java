package com.laf.baggoods.ui.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.laf.baggoods.App;

/**
 * Created by apple on 2017/3/8.
 */

public abstract class FragmentBase extends Fragment {

    protected Context mContext;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    protected View mMainView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mContext = App.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;

        setContentView(getFragmentId());
        initView();
        addListener();
        return mMainView;
    }

    public interface OnFragmentInteractionListener {

    }

    protected void initView() {}

    protected void addListener() {}

    protected abstract int getFragmentId();

    protected void setContentView(int id) {
        mMainView = mInflater.inflate(id, mContainer, false);
    }

}

