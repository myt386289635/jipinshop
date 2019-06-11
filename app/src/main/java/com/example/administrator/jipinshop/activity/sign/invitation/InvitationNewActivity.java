package com.example.administrator.jipinshop.activity.sign.invitation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.InvitationNewAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityInvitation2Binding;
import com.example.administrator.jipinshop.view.viewpager.transformer.ScalePagerTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe 邀请好友页面更改
 */
public class InvitationNewActivity extends BaseActivity implements View.OnClickListener {

    private ActivityInvitation2Binding mBinding;
    private InvitationNewAdapter mAdapter;
    private List<String> mList;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }
}
