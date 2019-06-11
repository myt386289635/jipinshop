package com.example.administrator.jipinshop.activity.sign.invitation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.InvitationNewAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityInvitation2Binding;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.view.viewpager.transformer.ScalePagerTransformer;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe 邀请好友页面更改
 */
public class InvitationNewActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ActivityInvitation2Binding mBinding;
    private InvitationNewAdapter mAdapter;
    private List<String> mList;
    private int position = 0;//当前是第几个位置，默认为第一个

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_invitation2);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("邀请好友");
        mList = new ArrayList<>();
        mList.add("http://jipincheng.cn/4074f0cb11c940aaa3f2ff90bb28c01f");
        mList.add("http://jipincheng.cn/4074f0cb11c940aaa3f2ff90bb28c01f");
        mList.add("http://jipincheng.cn/4074f0cb11c940aaa3f2ff90bb28c01f");
        mList.add("http://jipincheng.cn/4074f0cb11c940aaa3f2ff90bb28c01f");
        mAdapter = new InvitationNewAdapter(this,mList);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(mList.size() - 1);
        mBinding.viewPager.setPageMargin((int) getResources().getDimension(R.dimen.x16));
        //解决了viewpager只有中间划动的问题
        mBinding.viewpagerContainer.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            v.performClick();
            return mBinding.viewPager.dispatchTouchEvent(event);
        });
        ScalePagerTransformer transformer = new ScalePagerTransformer();
        mBinding.viewPager.setPageTransformer(true,transformer);
        mBinding.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.share_wechat:
                new ShareUtils(this, SHARE_MEDIA.WEIXIN)
                        .shareImage(this, mList.get(position));
                break;
            case R.id.share_pyq:
                new ShareUtils(this, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .shareImage(this, mList.get(position));
                break;
            case R.id.share_weibo:
                new ShareUtils(this, SHARE_MEDIA.SINA)
                        .shareImage(this, mList.get(position));
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        position = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }
}
