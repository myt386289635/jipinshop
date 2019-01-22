package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.ActivitySignBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.goodview.GoodView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/23
 * @Describe 签到页面
 */
public class SignActivity extends BaseActivity implements View.OnClickListener, SignView {

    public static final String eventbusTag = "SignActivity";

    @Inject
    SignPresenter mPresenter;

    private ActivitySignBinding mBinding;
    private Dialog mDialog;

    private List<TextView> mTextViews;
    private GoodView mGoodView;
    private List<Integer> pointArr;
    private List<View> mLineViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setSignView(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding.statusBar,this);

        mTextViews = new ArrayList<>();
        mTextViews.add(mBinding.signOneImg);
        mTextViews.add(mBinding.signTwoImg);
        mTextViews.add(mBinding.signThreeImg);
        mTextViews.add(mBinding.signForeImg);
        mTextViews.add(mBinding.signFiveImg);
        mTextViews.add(mBinding.signSixImg);
        mTextViews.add(mBinding.signSunImg);

        mLineViews = new ArrayList<>();
        mLineViews.add(mBinding.signOneLine);
        mLineViews.add(mBinding.signTwoLine);
        mLineViews.add(mBinding.signThreeLine);
        mLineViews.add(mBinding.signForeLine);
        mLineViews.add(mBinding.signFiveLine);
        mLineViews.add(mBinding.signSixLine);

        mGoodView = new GoodView(this);
        pointArr = new ArrayList<>();

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.signInfo(this.bindToLifecycle());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.sign_rule:
                //积分规则
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "rule.html")
                        .putExtra(WebActivity.title, "规则说明")
                );
                break;
            case R.id.sign_signed:
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.sign(this.bindToLifecycle());
                break;
            case R.id.sign_detail:
                //积分明细
                startActivity(new Intent(this, IntegralDetailActivity.class));
                break;
        }
    }

    /**
     * 获取签到信息成功回调
     */
    @Override
    public void getInfoSuc(SignBean signBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.signTotle.setText(signBean.getData().getPointAccount().getTotalPoint() + "");
        mBinding.signDayNum.setText(signBean.getData().getPointAccount().getTodayPoint() + "");
        mBinding.signSurplusNum.setText(signBean.getData().getPointAccount().getUsablePoint() + "");
        for (int i = 0; i < signBean.getData().getPointArr().size(); i++) {
            mTextViews.get(i).setText("+" + signBean.getData().getPointArr().get(i) + "");
        }
        for (int i = 0; i < signBean.getData().getDaysCount(); i++) {
            mTextViews.get(i).setText("");
            mTextViews.get(i).setBackgroundResource(R.mipmap.signin_sel);
            if(i < signBean.getData().getDaysCount() - 1){
                mLineViews.get(i).setBackgroundColor(getResources().getColor(R.color.color_FFC5BD));
            }
        }
        pointArr.clear();
        pointArr.addAll(signBean.getData().getPointArr());
        mBinding.signDays.setText(signBean.getData().getDaysCount() + "");
    }
    /**
     * 获取签到信息失败回调
     */
    @Override
    public void getInfoFaile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 签到成功
     */
    @Override
    public void signSuc(SignInsertBean signInsertBean) {
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint,signInsertBean.getData().getUsablePoint());
        BigDecimal bigDecimal = new BigDecimal(mBinding.signDays.getText().toString());
        mBinding.signDays.setText((bigDecimal.intValue() + 1 )+ "");
        mBinding.signSurplusNum.setText(signInsertBean.getData().getUsablePoint() + "");
        BigDecimal bigDecimal1 = new BigDecimal(mBinding.signDayNum.getText().toString());
        mBinding.signDayNum.setText((bigDecimal1.intValue() + signInsertBean.getAddPoint()) + "");
        BigDecimal bigDecimal2 = new BigDecimal(mBinding.signTotle.getText().toString());
        mBinding.signTotle.setText((bigDecimal2.intValue() + signInsertBean.getAddPoint()) + "");
        mGoodView.setText("+" + signInsertBean.getAddPoint() + "极币");
        mGoodView.setTextColor(getResources().getColor(R.color.color_E31436));
        mGoodView.show(mBinding.signCalendarView);
        for (int i = 0; i < 7; i++) {
            if (i < signInsertBean.getDaysCount()){
                mTextViews.get(i).setText("");
                mTextViews.get(i).setBackgroundResource(R.mipmap.signin_sel);
                if(i < signInsertBean.getDaysCount() - 1){
                    mLineViews.get(i).setBackgroundColor(getResources().getColor(R.color.color_FFC5BD));
                }else if(i < 6){
                    mLineViews.get(i).setBackgroundColor(getResources().getColor(R.color.color_ECECEC));
                }
            }else {
                mTextViews.get(i).setText("+" + pointArr.get(i) + "");
                mTextViews.get(i).setBackgroundResource(R.mipmap.signin_nor);
                if(i < 6){
                    mLineViews.get(i).setBackgroundColor(getResources().getColor(R.color.color_ECECEC));
                }
            }
        }
        EventBus.getDefault().post(new EditNameBus(SignActivity.eventbusTag));
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
    /**
     * 签到失败
     */
    @Override
    public void signFaile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
