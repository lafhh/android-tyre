package com.laf.baggoods.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import com.laf.baggoods.R;
import com.laf.imageloader.decode.ImageDecodingInfo;
import com.laf.imageloader.download.ImageDownLoader;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by apple on 2017/3/23.
 */

public class TestActivity extends Activity {

    String TAG = "TestActivity";
    ImageView img1;
    ImageView img2;
    ImageView img3;
    Handler mHandler = new Mhandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    img1 = (ImageView) findViewById(R.id.img_1);
    img2 = (ImageView) findViewById(R.id.img_2);
    img3 = (ImageView) findViewById(R.id.img_3);


        String imgUri = "http://120.27.195.220/ddAdmin/upload/4a1b49a6-282e-41bc-8393-bb64f0fcf61d.jpg";
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        final ImageDecodingInfo info = new ImageDecodingInfo();
        info.setImgUri(imgUri);
        info.setInScaled(true);
        info.setInDensity(TypedValue.DENSITY_DEFAULT);
        info.setTargetDensity(metrics.densityDpi);
        info.setTargetWidth(metrics.widthPixels);

        final ImageDownLoader loader = new ImageDownLoader();
//        loader.decodeFromResource(getResources(), R.drawable.bg_header, 318, 126);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = loader.decodeFromStream(info, TestActivity.this);
                    Message msg = Message.obtain();
                    msg.obj = bitmap;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    Log.d(TAG, "io..e");
                    e.printStackTrace();
                }
            }
        }).start();

    }

    protected void handleMessage(Message msg) {
        Bitmap bitmap = (Bitmap) msg.obj;
        img1.setImageBitmap(bitmap);
    }

    public static class Mhandler extends Handler {
        private final WeakReference<TestActivity> activity;

        public Mhandler(TestActivity a) {
            activity = new WeakReference<>(a);
        }

        @Override
        public void handleMessage(Message msg) {
            TestActivity a = activity.get();
            a.handleMessage(msg);
        }
    }
}
