package com.example.administrator.jipinshop.activity.wellcome.index;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityIndexBinding;
import com.example.administrator.jipinshop.fragment.index.IndexPicFragment;
import com.example.administrator.jipinshop.fragment.index.IndexVideoFragment;
import com.gyf.barlibrary.BarHide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/4/15
 * @Describe 引导页（图片与视频的混合）
 */
public class IndexMixActivity extends BaseActivity {

    private ActivityIndexBinding mBinding;
    private List<Fragment> mFragments;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_index);
        mImmersionBar.reset()
                .transparentStatusBar()
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .statusBarDarkFont(true, 0f)
                .init();
        initView();
    }

    private void initView() {

        mFragments = new ArrayList<>();
        mFragments.add(IndexPicFragment.getInstence());
        mFragments.add(IndexVideoFragment.getInstence());
        mAdapter = new HomeAdapter(getSupportFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.detailPoint.setVisibility(View.GONE);
    }
}
