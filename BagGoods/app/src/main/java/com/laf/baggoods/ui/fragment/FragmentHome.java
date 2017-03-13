package com.laf.baggoods.ui.fragment;

import com.laf.baggoods.R;
import com.laf.baggoods.ui.basic.FragmentBase;
import com.laf.baggoods.ui.http.ImplHttpManager;
import com.laf.model.Advertisement;
import com.laf.model.Brand;
import com.laf.network.http.IHttpListener;
import com.laf.network.http.Response;

import java.util.ArrayList;

/**
 * Created by apple on 2017/3/6.
 */

public class FragmentHome extends FragmentBase {
    private static final String TAG = "FragmentHome";

    private ArrayList<Advertisement> ads = new ArrayList<>();

    private ArrayList<Brand> brands = new ArrayList<>();

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        requestHomeData();
    }

    @Override
    protected void addListener() {
    }

    private void requestHomeData() {
        new ImplHttpManager().requestHomeData(
            new IHttpListener() {
                @Override
                public void onResult(int action, Response response) {
                    //假数据
                }

                @Override
                public void onProgress(boolean isInProgress) {

                }
            }
        );
    }

}
