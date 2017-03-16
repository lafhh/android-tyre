package com.laf.baggoods.ui.activity;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.laf.baggoods.R;
import com.laf.baggoods.ui.basic.BaseActivity;
import com.laf.baggoods.ui.basic.FragmentBase;
import com.laf.baggoods.ui.fragment.FragmentCart;
import com.laf.baggoods.ui.fragment.FragmentHome;
import com.laf.baggoods.ui.fragment.FragmentMessage;
import com.laf.baggoods.ui.fragment.FragmentMine;
import com.laf.model.HomeDataValue;

/**
 * Created by apple on 17/3/3.
 */
public class HomePageActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = "HomePageActivity";

    private FragmentManager fm;
    private FragmentHome mHomeFragment;
    private FragmentCart mCartFragment;
    private FragmentMessage mMessageFragment;
    private FragmentMine mMineFragment;
    private Fragment mCurrent;

    private RelativeLayout mHomeLayout;
    private RelativeLayout mCartLayout;
    private RelativeLayout mMessageLayout;
    private RelativeLayout mMineLayout;

    private ImageView mHomeImgView;
    private ImageView mCartImgView;
    private TextView mMessageImgView;
    private ImageView mMineImgView;
    private TextView mHomeTxtView;
    private TextView mCartTxtView;
    private TextView mMessageTxtView;
    private TextView mMineTxtView;

    protected int getId() {
        return R.layout.activity_home_page;
    }

    protected void initView() {
        mHomeLayout = (RelativeLayout) findViewById(R.id.layout_tab_home);
        mCartLayout = (RelativeLayout) findViewById(R.id.layout_tab_cart);
        mMessageLayout = (RelativeLayout) findViewById(R.id.layout_tab_message);
        mMineLayout = (RelativeLayout) findViewById(R.id.layout_tab_mine);

        mHomeImgView = (ImageView) findViewById(R.id.img_home);
        mCartImgView = (ImageView) findViewById(R.id.img_cart);
        mMessageImgView = (TextView) findViewById(R.id.img_message);
        mMineImgView = (ImageView) findViewById(R.id.img_mine);
        mHomeTxtView = (TextView) findViewById(R.id.txt_home);
        mCartTxtView = (TextView) findViewById(R.id.txt_cart);
        mMessageTxtView = (TextView) findViewById(R.id.txt_message);
        mMineTxtView = (TextView) findViewById(R.id.txt_mine);

        mHomeFragment = new FragmentHome();
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.layout_content, mHomeFragment);
        fragmentTransaction.commit();
    }

    protected void addListener() {
        mHomeLayout.setOnClickListener(this);
        mCartLayout.setOnClickListener(this);
        mMessageLayout.setOnClickListener(this);
        mMineLayout.setOnClickListener(this);
    }

    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        int colorOn = ContextCompat.getColor(this, R.color.red_tab);
        Log.d(TAG, "tab on: int color====== " + colorOn);
        int colorOff = ContextCompat.getColor(this, R.color.gray_tab);
        switch (v.getId()) {
            case R.id.layout_tab_home:
                mHomeImgView.setImageResource(R.drawable.ico_baggoods_on);
                mCartImgView.setImageResource(R.drawable.ico_cart_off);
                mMessageImgView.setBackgroundResource(R.drawable.ico_message_off);
                mMineImgView.setImageResource(R.drawable.ico_mine_off);
                mHomeTxtView.setTextColor(colorOn);
                mCartTxtView.setTextColor(colorOff);
                mMessageTxtView.setTextColor(colorOff);
                mMineTxtView.setTextColor(colorOff);

                hideFragment(mCartFragment, fragmentTransaction);
                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mHomeFragment == null) {
                    mHomeFragment = new FragmentHome();
                    fragmentTransaction.add(R.id.layout_content, mHomeFragment);
                } else {
                    mCurrent = mHomeFragment;
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case R.id.layout_tab_cart:
                mCartImgView.setImageResource(R.drawable.ico_cart_on);
                mHomeImgView.setImageResource(R.drawable.ico_baggoods_off);
                mMessageImgView.setBackgroundResource(R.drawable.ico_message_off);
                mMineImgView.setImageResource(R.drawable.ico_mine_off);
                mCartTxtView.setTextColor(colorOn);
                mHomeTxtView.setTextColor(colorOff);
                mMessageTxtView.setTextColor(colorOff);
                mMineTxtView.setTextColor(colorOff);

                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mCartFragment == null) {
                    mCartFragment = new FragmentCart();
                    fragmentTransaction.add(R.id.layout_content, mCartFragment);
                } else {
                    mCurrent = mCartFragment;
                    fragmentTransaction.show(mCartFragment);
                }
                break;
            case R.id.layout_tab_message:
//                mMessageImgView.setBackgroundResource(R.drawable.ico_message_on);
                mCartImgView.setImageResource(R.drawable.ico_cart_off);
                mHomeImgView.setImageResource(R.drawable.ico_baggoods_off);
                mMineImgView.setImageResource(R.drawable.ico_mine_off);
                mMessageTxtView.setTextColor(colorOn);
                mCartTxtView.setTextColor(colorOff);
                mHomeTxtView.setTextColor(colorOff);
                mMineTxtView.setTextColor(colorOff);

                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mCartFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mMessageFragment == null) {
                    mMessageFragment = new FragmentMessage();
                    fragmentTransaction.add(R.id.layout_content, mMessageFragment);
                } else {
                    mCurrent = mMessageFragment;
                    fragmentTransaction.show(mMessageFragment);
                }
                break;
            case R.id.layout_tab_mine:
                mMineImgView.setImageResource(R.drawable.ico_mine_on);
                mHomeImgView.setImageResource(R.drawable.ico_baggoods_off);
                mCartImgView.setImageResource(R.drawable.ico_cart_off);
                mMessageImgView.setBackgroundResource(R.drawable.ico_message_off);
                mMineTxtView.setTextColor(colorOn);
                mHomeTxtView.setTextColor(colorOff);
                mCartTxtView.setTextColor(colorOff);
                mMessageTxtView.setTextColor(colorOff);

                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mCartFragment, fragmentTransaction);
                hideFragment(mMessageFragment, fragmentTransaction);
                if (mMineFragment == null) {
                    mMineFragment = new FragmentMine();
                    fragmentTransaction.add(R.id.layout_content, mMineFragment);
                } else {
                    mCurrent = mMineFragment;
                    fragmentTransaction.show(mMineFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    public void handlerUIMessage(Message msg) {
        switch (msg.what) {
            case FragmentHome.MESSAGE_HOME_DATA:
                mHomeFragment.updateUI();
                break;
        }

    }
}
