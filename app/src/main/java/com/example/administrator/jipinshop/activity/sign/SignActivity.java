package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.home.HomeDetailActivity;
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.mall.detail.MallDetailActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity;
import com.example.administrator.jipinshop.activity.setting.bind.BindWXActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.activity.sign.market.MarketActivity;
import com.example.administrator.jipinshop.activity.sreach.TBSreachActivity;
import com.example.administrator.jipinshop.activity.web.dzp.BigWheelWebActivity;
import com.example.administrator.jipinshop.activity.web.hb.HBWebView2;
import com.example.administrator.jipinshop.adapter.KTSignAdapter;
import com.example.administrator.jipinshop.adapter.SignMallAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ActionHBBean;
import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TeacherBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.databinding.ActivitySignBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/23
 * @Describe 签到页面
 */
public class SignActivity extends BaseActivity implements View.OnClickListener, SignView, SignMallAdapter.OnItemListener, KTSignAdapter.OnClickJump {

    public static final String eventbusTag = "SignActivity";

    @Inject
    SignPresenter mPresenter;

    private ActivitySignBinding mBinding;
    private Dialog mDialog;
    private DailyTaskBean.AdBean ad2;
    private DailyTaskBean.AdBean ad1;
    private Boolean once = true;
    //每日任务
    private List<DailyTaskBean.DataBean> mDayRule;
    private KTSignAdapter mDayAdapter;
    //完善用户信息
    private List<DailyTaskBean.DataBean> mUserRule;
    private KTSignAdapter mUserAdapter;
    //极币商城
    private List<MallBean.DataBean> mList;
    private SignMallAdapter mMallAdapter;

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
        mBinding.inClude.titleTv.setText("极币中心");

        //每日任务
        mDayRule = new ArrayList<>();
        mBinding.signDayRules.setLayoutManager(new LinearLayoutManager(this));
        mDayAdapter = new KTSignAdapter(mDayRule,this);
        mDayAdapter.setOnClickJump(this);
        mDayAdapter.setType(1);
        mBinding.signDayRules.setAdapter(mDayAdapter);
        mBinding.signDayRules.setNestedScrollingEnabled(false);

        //完成用户信息任务
        mUserRule =  new ArrayList<>();
        mBinding.signUserRules.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter = new KTSignAdapter(mUserRule,this);
        mUserAdapter.setOnClickJump(this);
        mUserAdapter.setType(2);
        mBinding.signUserRules.setAdapter(mUserAdapter);
        mBinding.signUserRules.setNestedScrollingEnabled(false);

        //积分商城
        mList = new ArrayList<>();
        mBinding.signMall.setLayoutManager(new GridLayoutManager(this, 2));
        mMallAdapter = new SignMallAdapter(this, mList);
        mMallAdapter.setOnItemListener(this);
        mBinding.signMall.setAdapter(mMallAdapter);
        mBinding.signMall.setNestedScrollingEnabled(false);

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.sign(1,this.bindToLifecycle());//签到
        mPresenter.mallList(this.bindToLifecycle());//极币商城
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
            case R.id.sign_vpButton:
                //动态按钮
                if (ad1 == null){
                    return;
                }
                ShopJumpUtil.openBanner(this,ad1.getType(),
                        ad1.getObjectId(),ad1.getName(),
                        ad1.getSource() , ad1.getRemark());
                break;
            case R.id.sign_h5Container:
                //大转盘
                if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (ad2 == null){
                    return;
                }
                if (ad2.getType().equals("30")){
                    Intent intent = new Intent();
                    intent.setClass(this, BigWheelWebActivity.class);
                    intent.putExtra(BigWheelWebActivity.url, ad2.getObjectId());
                    intent.putExtra(BigWheelWebActivity.title,"大转盘");
                    intent.putExtra(BigWheelWebActivity.sign , 1);
                    startActivity(intent);
                }else {
                    ShopJumpUtil.openBanner(this,ad2.getType(),
                            ad2.getObjectId(),ad2.getName(),
                            ad2.getSource(),ad2.getRemark());
                }
                break;
        }
    }

    //签到成功
    @Override
    public void signSuc(SignInsertBean signInsertBean) {
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint,signInsertBean.getData().getUsablePoint());
        mBinding.signCode.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint) + "");
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show("签到成功");
        mPresenter.DailytaskList(this.bindToLifecycle());//每日任务
    }

    //签到失败
    @Override
    public void signFile(int code,String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (code != 630)
            ToastUtil.show(error);
        mPresenter.DailytaskList(this.bindToLifecycle());//每日任务
    }

    //任务列表
    @Override
    public void getDayList(DailyTaskBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.signCode.setText(bean.getPoint() + "");
        ad2 = bean.getAd2();
        ad1 = bean.getAd1();
        mBinding.setAd(bean.getAd1());
        mBinding.executePendingBindings();
        mDayRule.clear();
        mUserRule.clear();
        mDayRule.addAll(bean.getData());
        mUserRule.addAll(bean.getList2());
        mDayAdapter.notifyDataSetChanged();
        mUserAdapter.notifyDataSetChanged();
        once = false;
    }

    //极币商城
    @Override
    public void onSuccess(MallBean bean) {
        mList.clear();
        mList.addAll(bean.getData());
        mMallAdapter.notifyDataSetChanged();
    }

    //跳转极币商城详情页
    @Override
    public void onItemIntegralDetail(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(this, MallDetailActivity.class)
                    .putExtra("goodsId",mList.get(position).getId())
                    .putExtra("isActivityGoods",mList.get(position).getType())
            );
        }
    }

    @Override
    public void onHBID(ActionHBBean bean) {
        if (TextUtils.isEmpty(bean.getData())){
            DialogUtil.hbWebDialog(this, v -> {
                startActivity(new Intent(this, HBWebView2.class)
                        .putExtra(HBWebView2.url, RetrofitModule.JP_H5_URL + "new-free/getRedPacket?isfirst=true&token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                        .putExtra(HBWebView2.title, "天天领现金")
                );
            });
        }else{
            startActivity(new Intent(this, HBWebView2.class)
                    .putExtra(HBWebView2.url, RetrofitModule.JP_H5_URL + "new-free/getRedPacket?token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                    .putExtra(HBWebView2.title, "天天领现金")
            );
        }
    }

    @Override
    public void onHBFlie() {
        DialogUtil.hbWebDialog(this, v -> {
            startActivity(new Intent(this, HBWebView2.class)
                    .putExtra(HBWebView2.url, RetrofitModule.JP_H5_URL + "new-free/getRedPacket?isfirst=true&token=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                    .putExtra(HBWebView2.title, "天天领现金")
            );
        });
    }

    @Override
    public void onTeacher(TeacherBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        DialogUtil.teacherDialog(this,bean.getData().getWechat(),bean.getData().getAvatar());
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
        once = false;
    }

    @Override
    public void onDayJump(int pos) {
        dayJump(mDayRule.get(pos).getLocation() ,mDayRule.get(pos).getLocationId(),mDayRule.get(pos).getLocationTitle());
    }

    @Override
    public void onJump(int pos) {
        dayJump(mUserRule.get(pos).getLocation(), mUserRule.get(pos).getLocationId(), mUserRule.get(pos).getLocationTitle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!once){
            //刷新任务
            mPresenter.DailytaskList(this.bindToLifecycle());
        }
    }

    //每日任务的跳转逻辑
    public void dayJump(int location , String id , String title){
        switch (location){
            case 1://跳转到首页
                EventBus.getDefault().post(new ChangeHomePageBus(0));
                finish();
                break;
            case 3://跳转到评测
                Intent intent = new Intent();
                intent.setClass(this, HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.evaluation);
                startActivity(intent);
                break;
            case 4://跳转到邀请页面
                startActivity(new Intent(this, InvitationNewActivity.class));
                break;
            case 6://跳转到新人免单
                startActivity(new Intent(this, NewFreeActivity.class));
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
            case 9:
                //调用签到接口
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                mPresenter.sign(2,this.bindToLifecycle());
                break;
            case 10://搜索
                startActivity(new Intent(this, TBSreachActivity.class));
                break;
            case 11://分享发圈
                EventBus.getDefault().post(new ChangeHomePageBus(3));
                finish();
                break;
            case 12://授权淘宝
                TaoBaoUtil.openTB(this, () -> {
                    ToastUtil.show("已完成授权");
                });
                break;
            case 13://填写微信号
                startActivity(new Intent(this, BindWXActivity.class));
                break;
            case 14://添加导师微信
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                mPresenter.getParentInfo(this.bindToLifecycle());
                break;
            case 15://填写调查问卷
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, id)
                        .putExtra(WebActivity.title, "调查问卷")
                );
                break;
            case 16://应用市场好评
                startActivity(new Intent(this, MarketActivity.class));
                break;
            case 17://关注公众号
                DialogUtil.wxDialog(this, "关注公众号", "微信服务号名称：", "微信关注极品城公众号，并绑定账号");
                break;
            case 18://绑定小程序
                DialogUtil.wxDialog(this, "绑定小程序", "微信小程序：", "微信搜索极品城小程序，并绑定账号");
                break;
            case 19://专题页
                startActivity(new Intent(this, HomeDetailActivity.class)
                        .putExtra("id", id)
                        .putExtra("title", title)
                        .putExtra("isSign", true)
                );
                break;
        }
    }
}
