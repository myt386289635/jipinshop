package com.example.administrator.jipinshop.activity.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;

/**
 * @author 莫小婷
 * @create 2018/8/23
 * @Describe 赚钱页面 (单开页)
 */
public class SignActivity extends BaseActivity {

    // 定义
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction =  supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_fragment, SignFragment.getInstance("2")).commit();
    }
}
