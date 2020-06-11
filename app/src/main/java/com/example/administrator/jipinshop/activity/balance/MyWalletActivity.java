package com.example.administrator.jipinshop.activity.balance;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.balance.detail.WalletDetailActivity;
import com.example.administrator.jipinshop.activity.balance.history.WalletHistoryActivity;
import com.example.administrator.jipinshop.activity.balance.withdraw.WithdrawActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ScoreStatusBean;
import com.example.administrator.jipinshop.bean.eventbus.WithdrawBus;
import com.example.administrator.jipinshop.databinding.ActivityWalletBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding  = DataBindingUtil.setContentView(this,R.layout.activity_wallet);
        mBaseActivityComponent.inject(this);
        mBinding.setListener(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("我的收益");
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
                        .putExtra("price",mBinding.walletMoney.getText().toString())
                );
                break;
            case R.id.title_totleText:
                //总资产解释
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"commission-rule.html")
                        .putExtra(WebActivity.title,"规则说明")
                );
                break;
            case R.id.wallet_today_detail:
                //日详情
                startActivity(new Intent(this, WalletDetailActivity.class));
                break;
            case R.id.wallet_month_detail:
                //月详情
                startActivity(new Intent(this, WalletDetailActivity.class));
                break;
            case R.id.wallet_today:
                //日历史概况
                startActivity(new Intent(this, WalletHistoryActivity.class));
                break;
            case R.id.wallet_month:
                //月历史概况
                startActivity(new Intent(this, WalletHistoryActivity.class));
                break;
        }
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
//            mPresenter.myCommssionSummary(this.bindToLifecycle());
            mPresenter.getScoreStatus(this.bindToLifecycle());
        }
    }
}
