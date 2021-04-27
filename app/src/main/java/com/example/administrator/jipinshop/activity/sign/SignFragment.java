package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.home.HomeDetailActivity;
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.mall.detail.MallDetailActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity;
import com.example.administrator.jipinshop.activity.setting.bind.BindWXActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.activity.sign.market.MarketActivity;
import com.example.administrator.jipinshop.activity.sreach.TBSreachActivity;
import com.example.administrator.jipinshop.adapter.SignAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TeacherBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.databinding.ActivitySignBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog2;
import com.qubian.mob.AdManager;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2021/3/16
 * @Describe 赚钱页面
 */
public class SignFragment extends DBBaseFragment implements View.OnClickListener, SignAdapter.OnItem, SignView, OnLoadMoreListener, ShareBoardDialog2.onShareListener {

    @Inject
    SignPresenter mPresenter;
    @Inject
    AppStatisticalUtil appStatisticalUtil;

    private ActivitySignBinding mBinding;
    private String type;//1:fragment 2:activity
    private Dialog mDialog;
    private List<MallBean.DataBean> mList;
    private SignAdapter mAdapter;
    private Boolean once = true;
    private int page = 2;
    private Boolean refersh = true;//记录是刷新还是加载
    private DailyTaskBean mBean;
    private ShareBoardDialog2 mShareBoardDialog;

    public static SignFragment getInstance(String type){
        SignFragment fragment = new SignFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",type);//1:fragment 2:activity
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "正在加载...");
            mDialog.show();
            mPresenter.getData(this.bindToLifecycle());
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_sign,container,false);
        mBinding.setListener(this);
        mBaseFragmentComponent.inject(this);
        mPresenter.setSignView(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        type = getArguments().getString("type","1");
        mBinding.inClude.titleTv.setText("赚钱");
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        mPresenter.scrollTitle(mBinding.swipeTarget,mBinding.titleContainer);
        if (type.equals("1")){
            mBinding.inClude.titleBack.setVisibility(View.GONE);
        }else {
            mBinding.inClude.titleBack.setVisibility(View.VISIBLE);
        }

        mList = new ArrayList<>();
        mAdapter = new SignAdapter(getContext(),mList);
        mAdapter.setOnItem(this);
        mAdapter.setType(type);
        mAdapter.setAppStatisticalUtil(appStatisticalUtil);
        mAdapter.setTransformer(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        mBinding.swipeTarget.setLayoutManager(new GridLayoutManager(getContext(),2));
        mBinding.swipeTarget.setAdapter(mAdapter);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);

        if (type.equals("2")){
            mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "正在加载...");
            mDialog.show();
            mPresenter.getData(this.bindToLifecycle());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                if (getActivity() != null){
                    getActivity().finish();
                }
                break;
            case R.id.sign_detail:
                //积分明细
                startActivity(new Intent(getContext(), IntegralDetailActivity.class));
                break;
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.mallList(page,this.bindToLifecycle());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!once &&
                !TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,"").trim())){
            //登陆之后且不是第一次进入
            mPresenter.getData(this.bindToLifecycle());
        }
    }

    @Override
    public void onIndex(DailyTaskBean bean) {
        mBean = bean;
        mAdapter.setBean(mBean);
        page = 2;
        refersh = true;
        mPresenter.mallList(page,this.bindToLifecycle());
        once = false;
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        stopLoading();
        ToastUtil.show(error);
        once = false;
    }

    @Override
    public void onSuccess(MallBean bean) {
        if (refersh){
            if (mDialog != null && mDialog.isShowing()){
                mDialog.dismiss();
            }
            if (bean.getData() != null && bean.getData().size() != 0){
                //有数据
                mList.clear();
                mList.addAll(bean.getData());
            }else {
                ToastUtil.show("暂无数据");
            }
            mAdapter.notifyDataSetChanged();//刷新其他数据
        }else {
            stopLoading();
            if (bean.getData() != null && bean.getData().size() != 0){
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    public void stopLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled()) {
                mBinding.swipeToLoad.setLoadMoreEnabled(true);
                mBinding.swipeToLoad.setLoadingMore(false);
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                mBinding.swipeToLoad.setLoadingMore(false);
            }
        }
    }

    @Override
    public void onMoreDetail(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(), MallDetailActivity.class)
                    .putExtra("goodsId",mList.get(position).getId())
                    .putExtra("isActivityGoods",mList.get(position).getType())
            );
        }
    }

    //点击新人任务
    @Override
    public void onLeft() {
        mAdapter.setSet(0);
        mAdapter.notifyDataSetChanged();
    }

    //点击日常任务
    @Override
    public void onRight() {
        mAdapter.setSet(1);
        mAdapter.notifyDataSetChanged();
    }

    //任务跳转
    @Override
    public void onRuleJump(int set ,int position) {
        if (set == 0){
            //左边
            dayJump(mBean.getData().getList1().get(position).getLocation(),
                    mBean.getData().getList1().get(position).getLocationId(),
                    mBean.getData().getList1().get(position).getLocationTitle());
        }else {
            //右边
            dayJump(mBean.getData().getList2().get(position).getLocation(),
                    mBean.getData().getList2().get(position).getLocationId(),
                    mBean.getData().getList2().get(position).getLocationTitle());
        }
    }

    @Override
    public void onHotDetail(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(), MallDetailActivity.class)
                    .putExtra("goodsId",mBean.getData().getGoodsList().get(position).getId())
                    .putExtra("isActivityGoods",mBean.getData().getGoodsList().get(position).getType())
            );
        }
    }

    //顶部关闭页面按钮
    @Override
    public void onFinish() {
        if (getActivity() != null){
            getActivity().finish();
        }
    }

    //签到
    @Override
    public void onSign() {
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        mDialog.show();
        mPresenter.sign(this.bindToLifecycle());
    }

    //补签
    @Override
    public void onNoSign(int day) {
        DialogUtil.signDialog(getContext(), "立即补签", "补签金额：" + mBean.getData().getNeedPoint() + "极币",
                R.mipmap.sign_bg5, v -> {
                    mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
                    mDialog.show();
                    mPresenter.noSign(day,this.bindToLifecycle());
                });
    }

    //签到成功回调
    @Override
    public void signSuc(SignInsertBean signInsertBean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBean.getData().setPoint(signInsertBean.getPoint());
        mBean.getData().getSigninList().clear();
        mBean.getData().getSigninList().addAll(signInsertBean.getData());
        mBean.getData().setIsSignin(1);
        for (int i = 0; i < mBean.getData().getList2().size(); i++) {
            if (mBean.getData().getList2().get(i).getType() == 10){
                //是签到任务
                mBean.getData().getList2().get(i).setStatus("1");
                mBean.getData().getList2().get(i).setTitle("签到(1/1)");
                break;
            }
        }
        mAdapter.setBean(mBean);
        mAdapter.notifyDataSetChanged();
        if (signInsertBean.getDaysCount() != 7){
            //1-6天
            DialogUtil.signDialog(getContext(),"签到成功","连续签到7天最高获得100极币"
            ,R.mipmap.sign_bg4, null);
        }else {
            //第7天
            DialogUtil.signSvn(getContext(), signInsertBean.getAddPoint(), v -> {
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog2.getInstance(0);
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getChildFragmentManager(), "ShareBoardDialog2");
                }
            });
        }
    }

    //补签成功回调
    @Override
    public void noSignSuc(SignInsertBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBean.getData().setPoint(bean.getPoint());
        mBean.getData().getSigninList().clear();
        mBean.getData().getSigninList().addAll(bean.getData());
        mAdapter.setBean(mBean);
        mAdapter.notifyDataSetChanged();
        if (bean.getDaysCount() != 7){
            DialogUtil.signDialog(getContext(),"补签成功","连续签到7天最高获得100极币"
                    ,R.mipmap.sign_bg4, null);
        }else {
            DialogUtil.signSvn(getContext(), bean.getAddPoint(), v -> {
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog2.getInstance(0);
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getChildFragmentManager(), "ShareBoardDialog2");
                }
            });
        }
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        mDialog.show();
        mPresenter.initShare(share_media,this.bindToLifecycle());
        mPresenter.taskFinish("30",this.bindToLifecycle());
    }

    @Override
    public void onLink() {
        mPresenter.initShare(null,this.bindToLifecycle());
        mPresenter.taskFinish("30",this.bindToLifecycle());
    }

    @Override
    public void initShare(SHARE_MEDIA share_media, ShareInfoBean bean) {
        if (share_media != null){
            new ShareUtils(getContext(),share_media,mDialog)
                    .shareWeb(getActivity(), bean.getData().getLink(),bean.getData().getTitle(),bean.getData().getDesc(),bean.getData().getImgUrl(),R.mipmap.share_logo);
        }else {
            ClipboardManager clip = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("jipinshop",  bean.getData().getLink());
            clip.setPrimaryClip(clipData);
            ToastUtil.show("复制成功");
            SPUtils.getInstance().put(CommonDate.CLIP, bean.getData().getLink());
        }
    }

    //每日任务的跳转逻辑
    public void dayJump(int location , String id , String title){
        switch (location){
            case 1://跳转到首页
                EventBus.getDefault().post(new ChangeHomePageBus(0));
                break;
            case 3://跳转到评测
                Intent intent = new Intent();
                intent.setClass(getContext(), HomeNewActivity.class);
                intent.putExtra("type",HomeNewActivity.evaluation);
                startActivity(intent);
                break;
            case 4://跳转到邀请页面
                startActivity(new Intent(getContext(), InvitationNewActivity.class));
                break;
            case 6://跳转到新人免单
                startActivity(new Intent(getContext(), NewFreeActivity.class));
                break;
            case 7://编辑个人资料
                startActivity(new Intent(getContext(), MyInfoActivity.class)
                        .putExtra("bgImg",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bgImg))
                        .putExtra("sign",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userSign))
                );
                break;
            case 8://填写邀请码
                DialogUtil.invitationDialog(getContext(), (invitationCode, dialog, inputManager) -> {
                    if (TextUtils.isEmpty(invitationCode)){
                        ToastUtil.show("请输入邀请码");
                        return;
                    }
                    mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
                    mDialog.show();
                    mPresenter.addInvitationCode(invitationCode, dialog, inputManager,this.bindToLifecycle());
                });
                break;
            case 9://签到
                mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
                mDialog.show();
                mPresenter.sign(this.bindToLifecycle());
                break;
            case 10://搜索
                startActivity(new Intent(getContext(), TBSreachActivity.class));
                break;
            case 11://分享发圈
                EventBus.getDefault().post(new ChangeHomePageBus(3));
                break;
            case 12://授权淘宝
                TaoBaoUtil.openTB(getContext(), () -> {
                    ToastUtil.show("已完成授权");
                });
                break;
            case 13://填写微信号
                startActivity(new Intent(getContext(), BindWXActivity.class));
                break;
            case 14://添加导师微信
                mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
                mDialog.show();
                mPresenter.getParentInfo(this.bindToLifecycle());
                break;
            case 15://填写调查问卷
                startActivity(new Intent(getContext(), WebActivity.class)
                        .putExtra(WebActivity.url, id)
                        .putExtra(WebActivity.title, "调查问卷")
                );
                break;
            case 16://应用市场好评
                startActivity(new Intent(getContext(), MarketActivity.class)
                        .putExtra(MarketActivity.url, RetrofitModule.JP_H5_URL + "new-free/appAppraise?token="
                                + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                        .putExtra(MarketActivity.title, "应用市场好评")
                );
                break;
            case 17://关注公众号
                startActivity(new Intent(getContext(), MarketActivity.class)
                        .putExtra(MarketActivity.url, RetrofitModule.JP_H5_URL + "new-free/attentionWeChat?token="
                                + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token))
                        .putExtra(MarketActivity.title, "关注公众号")
                );
                break;
            case 18://绑定小程序
                IWXAPI api = WXAPIFactory.createWXAPI(getContext(), "wxfd2e92db2568030a");
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = "gh_b0a86c45468d";
                req.path = "pages/tabMain/main/main";
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版1体验版2和正式版0
                api.sendReq(req);
                break;
            case 19://专题页
                startActivity(new Intent(getContext(), HomeDetailActivity.class)
                        .putExtra("id", id)
                        .putExtra("title", title)
                        .putExtra("isSign", true)
                );
                break;
            case 20://专题页(无倒计时)
                startActivity(new Intent(getContext(), HomeDetailActivity.class)
                        .putExtra("id", id)
                        .putExtra("title", title)
                );
                break;
            case 21://观看激励视频
                lookAd();
                break;
        }
    }

    public void lookAd(){
        mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
        mDialog.show();
        AdManager.loadRewardVideoAd("1371288606810849283", "","", "极品城", AdManager.Orientation.VIDEO_VERTICAL, getActivity(), new AdManager.RewardVideoAdLoadListener() {
            @Override
            public void onAdFail(String s) {//广告加载失败
                if (mDialog != null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
                ToastUtil.show(s);
            }

            @Override
            public void onAdClose() {//视频被关闭
                if (mDialog != null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
            }

            @Override
            public void onRewardVideoCached() {
                //视频广告加载完成，此时播放视频不卡顿
                if (mDialog != null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
                AdManager.playRewardVideoAd(getActivity());//播放
            }

            @Override
            public void onRewardVerify() {//激励视频触发激励（观看视频大于一定时长或者视频播放完毕）
                mPresenter.taskFinish("31",SignFragment.this.bindToLifecycle());
            }
        });
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
    public void onTeacher(TeacherBean bean) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        DialogUtil.teacherDialog(getContext(),bean.getData().getWechat(),bean.getData().getAvatar());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //释放所有激励视频广告资源，建议onDestroy中调用
        AdManager.destroyRewardVideoAdAll();
    }
}
