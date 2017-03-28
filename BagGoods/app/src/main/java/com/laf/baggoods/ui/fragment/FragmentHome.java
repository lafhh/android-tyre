package com.laf.baggoods.ui.fragment;

import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.laf.baggoods.R;
import com.laf.baggoods.http.ImplHttpManager;
import com.laf.baggoods.ui.adapter.LvHomeAdapter;
import com.laf.baggoods.ui.basic.BaseActivity;
import com.laf.baggoods.ui.basic.FragmentBase;
import com.laf.model.HomeDataValue;
import com.laf.network.http.IHttpListener;
import com.laf.network.http.Response;
import com.laf.qrcode.zxing.app.CaptureActivity;

import java.util.ArrayList;

/**
 * Created by apple on 2017/3/6.
 */

public class FragmentHome extends FragmentBase implements OnClickListener {
    private static final String TAG = "FragmentHome";
    public static final int MESSAGE_HOME_DATA = 1;

    private ArrayList<HomeDataValue> homeDatas = new ArrayList<>();

    private TextView mTxtViewQrcode;
    private ListView mLvHome;

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mTxtViewQrcode = (TextView) mMainView.findViewById(R.id.txt_bar_code);
        mLvHome = (ListView) mMainView.findViewById(R.id.lv_home);
//        requestHomeData();
        updateUI();
    }

    @Override
    protected void addListener() {
        mTxtViewQrcode.setOnClickListener(this);
    }

    /**
     * 请求首页数据,通过获取的数据更新UI
     */
    private void requestHomeData() {
        Log.d(TAG, Thread.currentThread().getName());
        new ImplHttpManager().requestHomeData(
            new IHttpListener() {
                @Override
                public void onResult(int action, Response response) {

                    Log.d(TAG, Thread.currentThread().getName());
//                    Log.d(TAG, "on success: " + response.getData().toString());
                    //获取返回数据
                    Message msg = Message.obtain();
                    msg.what = MESSAGE_HOME_DATA;
                    ((BaseActivity)getActivity()).getHandler().sendMessage(msg);
                }

                @Override
                public void onProgress(boolean isInProgress) {

                }
            }
        );
    }

    public void updateUI() {
        for (int i = 0; i < 20; i++) {
            HomeDataValue value = new HomeDataValue();
            value.id = i;
            if (i == 0) value.type = 1;
//                        else if (i == 4) value.type = 2;
            else value.type = 2;
            homeDatas.add(value);
        }
        mLvHome.setAdapter(new LvHomeAdapter(mContext, homeDatas));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_bar_code:
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivity(intent);
                break;
        }
    }
}
