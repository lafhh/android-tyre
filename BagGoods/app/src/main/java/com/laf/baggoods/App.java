package com.laf.baggoods;

import android.app.Application;
import android.content.Context;

/**
 * Created by apple on 2017/3/8.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

}
