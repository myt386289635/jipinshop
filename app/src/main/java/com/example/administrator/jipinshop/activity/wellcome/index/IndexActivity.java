package com.example.administrator.jipinshop.activity.wellcome.index;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.adapter.IndexAdapter;
import com.example.administrator.jipinshop.databinding.ActivityIndexBinding;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/9/1
 * @Describe 引导页(纯图片)
 */
public class IndexActivity extends AppCompatActivity implements IndexAdapter.OnLick, ViewPager.OnPageChangeListener {

    private ActivityIndexBinding mBinding;

    private IndexAdapter mAdapter;
    private List<Integer> mList;
    private List<ImageView> point;
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
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .statusBarDarkFont(true, 0f)
                .init();

        mList = new ArrayList<>();
        point = new ArrayList<>();
        mList.add(R.mipmap.guide_android1);
        mList.add(R.mipmap.guide_android2);
        mList.add(R.mipmap.guide_android3);
//        mList.add(R.mipmap.guide_android4);
        mAdapter = new IndexAdapter(this,mList);
        mAdapter.setOnLick(this);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(mList.size() -1);
        initBanner();
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

    public void initBanner(){
        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView = new ImageView(this);
            point.add(imageView);
            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_down);
            } else {
                imageView.setImageResource(R.drawable.banner_up);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin =getResources().getDimensionPixelSize(R.dimen.x4);
            layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.x4);
            mBinding.detailPoint.addView(imageView, layoutParams);
        }
        mBinding.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if ( position == (mList.size() - 1)){
            mBinding.detailPoint.setVisibility(View.GONE);
        }else {
            mBinding.detailPoint.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < point.size(); i++) {
            if (i == position){
                point.get(i).setImageResource(R.drawable.banner_down);
            }else {
                point.get(i).setImageResource(R.drawable.banner_up);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
