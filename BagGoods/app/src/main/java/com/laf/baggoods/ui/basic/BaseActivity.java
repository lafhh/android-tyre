package com.laf.baggoods.ui.basic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import java.lang.ref.WeakReference;

/**
 * Created by apple on 17/3/3.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected final Handler mUIHandler = new UIHandler(this);

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

    private static class UIHandler extends Handler {
        private final WeakReference<BaseActivity> activity;

        public UIHandler(BaseActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity baseActivity = activity.get();

            if (baseActivity != null) {
                baseActivity.handlerUIMessage(msg);
            }
        }
    }

    public Handler getHandler() {
        return this.mUIHandler;
    }

    protected void handlerUIMessage(Message msg) {}

}
