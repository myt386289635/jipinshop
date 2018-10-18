package com.example.administrator.jipinshop.activity.wellcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.adapter.IndexAdapter;
import com.example.administrator.jipinshop.databinding.ActivityIndexBinding;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/9/1
 * @Describe 引导页
 */
public class IndexActivity extends AppCompatActivity implements IndexAdapter.OnLick {

    private ActivityIndexBinding mBinding;

    private IndexAdapter mAdapter;
    private List<Integer> mList;
    private ImmersionBar mImmersionBar;

    private Boolean tag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_index);
        if (Build.VERSION.SDK_INT >= 28) {
            //适配9.0刘海
            NotchUtil.notch(this);
        }
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();

        mList = new ArrayList<>();
        mList.add(R.mipmap.guide_android1);
        mList.add(R.mipmap.guide_android2);
        mList.add(R.mipmap.guide_android3);
        mList.add(R.mipmap.guide_android4);
        mAdapter = new IndexAdapter(this,mList);
        mAdapter.setOnLick(this);
        mBinding.viewPager.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null)
            mImmersionBar.destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    @Override
    public void onLinkgo() {
        if(tag){
            startActivity(new Intent(IndexActivity.this, MainActivity.class));
            finish();
            tag = false;
        }
    }
}
