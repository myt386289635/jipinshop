package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.adapter.ExtendableListViewAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.databinding.ActivitySignBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/23
 * @Describe 签到页面
 */
public class SignActivity extends BaseActivity implements View.OnClickListener, SignView, ExtendableListViewAdapter.OnClickItem {

    public static final String eventbusTag = "SignActivity";

    @Inject
    SignPresenter mPresenter;

    private ActivitySignBinding mBinding;
    private Dialog mDialog;

    private List<TextView> mTextViews, mDayTextViews;
    private List<Integer> pointArr;

    private List<DailyTaskBean.DataBean> groupList;
    private ExtendableListViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setSignView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("任务中心");

        mTextViews = new ArrayList<>();
        mTextViews.add(mBinding.signOneImg);
        mTextViews.add(mBinding.signTwoImg);
        mTextViews.add(mBinding.signThreeImg);
        mTextViews.add(mBinding.signForeImg);
        mTextViews.add(mBinding.signFiveImg);
        mTextViews.add(mBinding.signSixImg);
        mTextViews.add(mBinding.signSunImg);
        mDayTextViews = new ArrayList<>();
        mDayTextViews.add(mBinding.signOneText);
        mDayTextViews.add(mBinding.signTwoText);
        mDayTextViews.add(mBinding.signThreeText);
        mDayTextViews.add(mBinding.signForeText);
        mDayTextViews.add(mBinding.signFiveText);
        mDayTextViews.add(mBinding.signSixText);
        mDayTextViews.add(mBinding.signSunText);
        pointArr = new ArrayList<>();

        mBinding.expandList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        groupList = new ArrayList<>();
        mAdapter = new ExtendableListViewAdapter(groupList,this);
        mAdapter.setOnClickItem(this);
        mBinding.expandList.setAdapter(mAdapter);
        mBinding.expandList.setFocusable(false);

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.signInfo(this.bindToLifecycle());
        mPresenter.DailytaskList(this.bindToLifecycle());//每日任务
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
        mBinding.signCode.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint) + "");
        for (int i = 0; i < signBean.getData().getPointArr().size(); i++) {
            mTextViews.get(i).setText("+" + signBean.getData().getPointArr().get(i) + "");
        }
        for (int i = 0; i < signBean.getData().getDaysCount(); i++) {
            mTextViews.get(i).setText("");
            mTextViews.get(i).setBackgroundResource(R.mipmap.signin_sel);
            mDayTextViews.get(i).setText("已签");
            mDayTextViews.get(i).setTextColor(getResources().getColor(R.color.color_E25838));
        }
        pointArr.clear();
        pointArr.addAll(signBean.getData().getPointArr());
        if (signBean.getData().getDaysCount() >= 7){
            mBinding.signTomorrowNum.setText("明日签到极币+" + pointArr.get(0));
        }else {
            mBinding.signTomorrowNum.setText("明日签到极币+" + pointArr.get(signBean.getData().getDaysCount()));
        }
        if (signBean.getData().getSignin() == 0){
            //0未签到
            mPresenter.sign(this.bindToLifecycle());//签到
        }else {
            //1已签到
            if(mDialog != null && mDialog.isShowing()){
                mDialog.dismiss();
            }
        }
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
        mBinding.signCode.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint) + "");
        if (signInsertBean.getDaysCount() >= 7){
            mBinding.signTomorrowNum.setText("明日签到极币+" + pointArr.get(0));
        }else {
            mBinding.signTomorrowNum.setText("明日签到极币+" + pointArr.get(signInsertBean.getDaysCount()));
        }
        for (int i = 0; i < 7; i++) {
            if (i < signInsertBean.getDaysCount()){
                mTextViews.get(i).setText("");
                mTextViews.get(i).setBackgroundResource(R.mipmap.signin_sel);
                mDayTextViews.get(i).setText("已签");
                mDayTextViews.get(i).setTextColor(getResources().getColor(R.color.color_E25838));
            }else {
                mTextViews.get(i).setText("+" + pointArr.get(i) + "");
                mTextViews.get(i).setBackgroundResource(R.drawable.bg_ffc0b2);
                mDayTextViews.get(i).setText((i + 1)+ "天");
                mDayTextViews.get(i).setTextColor(getResources().getColor(R.color.color_9D9D9D));
            }
        }
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show("签到成功");
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

    @Override
    public void getDayList(DailyTaskBean bean) {
        groupList.addAll(bean.getData());
        mAdapter.notifyDataSetChanged();
        for(int i = 0; i < mAdapter.getGroupCount(); i++){
            mBinding.expandList.expandGroup(i);
        }
    }

    @Override
    public void onClickItem(int location, int position) {
        dayJump(location);
        //添加统计
        UAppUtil.sign(this,groupList.get(position).getType());
    }

    /**
     * 每日任务的跳转逻辑
     */
    public void dayJump(int location){
        switch (location){
            case 1://跳转到首页
                EventBus.getDefault().post(new ChangeHomePageBus(0));
                finish();
                break;
            case 3://跳转到评测
                EventBus.getDefault().post(new ChangeHomePageBus(1));
                finish();
                break;
            case 4://跳转到邀请页面
                startActivity(new Intent(this, InvitationNewActivity.class));
                break;
            case 6://跳转到免单首页
                EventBus.getDefault().post(new ChangeHomePageBus(3));
                finish();
                break;
            case 7://编辑个人资料
                startActivity(new Intent(this, MyInfoActivity.class)
                        .putExtra("bgImg",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bgImg))
                        .putExtra("sign",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userSign))
                );
                break;
            case 8://填写邀请码
                DialogUtil.invitationDialog(this, (invitationCode, dialog, inputManager) -> {
                    if (TextUtils.isEmpty(invitationCode)){
                        ToastUtil.show("请输入邀请码");
                        return;
                    }
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                    mDialog.show();
                    mPresenter.addInvitationCode(invitationCode, dialog, inputManager,this.bindToLifecycle());
                });
                break;
        }
    }

    @Override
    public void onCodeSuc(Dialog dialog, InputMethodManager inputManager, SuccessBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(bean.getMsg());
        if (dialog.getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
        dialog.dismiss();
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
