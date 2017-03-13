package com.laf.baggoods.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.laf.baggoods.App;

/**
 * Created by apple on 2017/3/12.
 */

public class SysTools {

    private static Context mContext = App.getContext();

    private static ConnectivityManager connectivityManager
            = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

//    public SysTools(Context context) {
//        mContext = context;
//        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//    }

    /**
     * 获取屏幕分辨率
     * @return 分辨率 格式如：1920*1680
     */
    public static String getResolution() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dMetrics = new DisplayMetrics();
        display.getMetrics(dMetrics);
        int width = dMetrics.widthPixels;
        int height = dMetrics.heightPixels;

        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(width))
            .append("*")
            .append(String.valueOf(height));

        return sb.toString();
    }

}
