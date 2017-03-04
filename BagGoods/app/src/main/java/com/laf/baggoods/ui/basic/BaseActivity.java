package com.laf.baggoods.ui.basic;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by apple on 17/3/3.
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getId());
        initView();
        addListener();
        //入栈znn
    }

    protected abstract int getId();

    protected abstract void initView();

    protected abstract void addListener();
}
