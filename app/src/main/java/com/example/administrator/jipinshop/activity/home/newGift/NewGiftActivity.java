package com.example.administrator.jipinshop.activity.home.newGift;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.cheapgoods.zCheapBuyFragment;
import com.example.administrator.jipinshop.activity.home.comprehensive.HomeDetailFragment;
import com.example.administrator.jipinshop.activity.home.food.TakeOutFragment;
import com.example.administrator.jipinshop.activity.home.newGift.video.NewVideoFragment;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeFragment;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.adapter.KTTabAdapter8;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityNewGiftBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/3/8
 * @Describe 新人五重礼
 */
public class NewGiftActivity extends BaseActivity implements View.OnClickListener, KTTabAdapter8.OnItem {

    private ActivityNewGiftBinding mBinding;
    //tab
    private KTTabAdapter8 mTabAdapter;
    private List<String> titles;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;
    private int currentItem = 0;//当前页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_new_gift);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("新人五重礼");
        currentItem  =  getIntent().getIntExtra("currentItem",0);
        String str = "https://jipincheng.cn/new_user.png?" + System.currentTimeMillis();
        GlideApp.loderImage(this,str,mBinding.giftBanner,0,0);
        //选择tab
        CommonNavigator commonNavigator = new CommonNavigator(this);
        titles = new ArrayList<>();
        titles.add("新人免单");
        titles.add("108元补贴");
        titles.add("66元红包");
        titles.add("5折视频会员");
        titles.add("1元购");
        mTabAdapter = new KTTabAdapter8(titles,this);
        commonNavigator.setLeftPadding((int)getResources().getDimension(R.dimen.x15));
        commonNavigator.setRightPadding((int)getResources().getDimension(R.dimen.x15));
        commonNavigator.setAdapter(mTabAdapter);
        mBinding.giftTab.setNavigator(commonNavigator);
        //初始化内容
        mFragments = new ArrayList<>();
        mFragments.add(NewFreeFragment.getInstance());//新人免单
        mFragments.add(zCheapBuyFragment.getInstance(true,"2"));//108补贴
        mFragments.add(TakeOutFragment.getInstance());//外卖集合
        mFragments.add(NewVideoFragment.getInstance());//5折视频会员
        mFragments.add(HomeDetailFragment.getInstance("8","1元购", "2"));
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mBinding.viewPager.setCurrentItem(currentItem);
        mBinding.giftTab.onPageSelected(currentItem);
        mBinding.giftTab.onPageScrolled(currentItem,0.0F, 0);
        mBinding.viewPager.setNoScroll(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.gift_share:
                ToastUtil.show("分享");
                break;
        }
    }

    //tab点击的位置
    @Override
    public void onClick(int position) {
        mBinding.giftTab.onPageSelected(position);
        mBinding.giftTab.onPageScrolled(position,0.0F, 0);
        mBinding.viewPager.setCurrentItem(position);
    }

    public AppBarLayout getBar(){
        return mBinding.appbar;
    }
}
