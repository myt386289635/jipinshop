package com.example.administrator.jipinshop.activity.balance;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.balance.withdraw.WithdrawActivity;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.ScoreStatusBean;
import com.example.administrator.jipinshop.bean.eventbus.WithdrawBus;
import com.example.administrator.jipinshop.databinding.ActivityWalletBinding;
import com.example.administrator.jipinshop.fragment.balance.budget.BudgetDetailFragment;
import com.example.administrator.jipinshop.fragment.balance.withdraw.WithdrawDetailFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe 我的钱包页面
 */
public class MyWalletActivity extends BaseActivity implements View.OnClickListener, MyWalletView {

    @Inject
    MyWalletPresenter mPresenter;

    private ActivityWalletBinding mBinding;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;

    float startX = 0;
    float startY = 0;
    float xDistance = 0;
    float yDistance = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding  = DataBindingUtil.setContentView(this,R.layout.activity_wallet);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mBinding.setListener(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar,this);
        mPresenter.setStatusBarHight(mBinding.titleTop,mBinding.title,this);
//        mPresenter.initTitleLayout(mBinding.statusBar,mBinding.appbar,mBinding.titleContainer,mBinding.titleText);

        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mFragments = new ArrayList<>();
        mFragments.add(BudgetDetailFragment.getInstance());
        mFragments.add(WithdrawDetailFragment.getInstance());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mPresenter.initTabLayout(this,mBinding.tabLayout);

        mPresenter.myCommssionSummary(this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_record:
                //我要提现
                startActivity(new Intent(this, WithdrawActivity.class)
                        .putExtra("price",mBinding.titleMoneny.getText().toString())
                );
                break;
            case R.id.title_totleText:
                //总资产解释
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"commission-rule.html")
                        .putExtra(WebActivity.title,"规则说明")
                );
                break;
        }
    }


    /**
     * 解决AppBarLayout头布局过大与ViewPager手势冲突出现的滑动卡顿问题
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        final float curX = ev.getX();
        final float curY = ev.getY();

        xDistance += Math.abs(curX - startX);
        yDistance += Math.abs(curY - startY);

        if (xDistance >= yDistance) {
            //横向滑动
            mBinding.viewPager.setNoScroll(false);
        } else {
            //垂直滑动
            mBinding.viewPager.setNoScroll(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onSuccess(MyWalletBean bean) {
        mBinding.setBean(bean.getData());
    }

    @Override
    public void onFile(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void onScoreSuc(ScoreStatusBean bean) {
        if (bean.getData() <= 0 ){//data值大于0，评价过了。data等于0未评价
            DialogUtil.scoreDialog(this, (score, content, scoreFlag) -> {
                mPresenter.addScore(content,score,scoreFlag,this.bindToLifecycle());
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void setRefersh(WithdrawBus bus){
        if (bus != null){
            mPresenter.myCommssionSummary(this.bindToLifecycle());
            mPresenter.getScoreStatus(this.bindToLifecycle());
        }
    }
}
