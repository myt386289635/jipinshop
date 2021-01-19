package com.example.administrator.jipinshop.fragment.member;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.member.buy.MemberBuyActivity;
import com.example.administrator.jipinshop.activity.member.family.FamilyActivity;
import com.example.administrator.jipinshop.activity.member.zero.ZeroActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.adapter.HomePageAdapter;
import com.example.administrator.jipinshop.adapter.KTPagerAdapter3;
import com.example.administrator.jipinshop.adapter.MemberFreeAdapter;
import com.example.administrator.jipinshop.adapter.MemberMoreAllAdapter;
import com.example.administrator.jipinshop.adapter.MemberVideoAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.databinding.FragmentMemberNewBinding;
import com.example.administrator.jipinshop.fragment.member.cheap.CheapFragment;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.example.administrator.jipinshop.view.textview.CenteredImageSpan;
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/8/25
 * @Describe 新版会员页面
 */
public class MemberFragment extends DBBaseFragment implements View.OnClickListener, OnRefreshListener, MemberView,  ViewPager.OnPageChangeListener, MemberVideoAdapter.OnItem, MemberMoreAllAdapter.OnItem, MemberFreeAdapter.OnItemClick {

    @Inject
    MemberPresenter mPresenter;
    @Inject
    AppStatisticalUtil appStatisticalUtil;

    private FragmentMemberNewBinding mBinding;
    private String type = "1";// 1:fragment 2:activity
    private Boolean once = true;
    private int userLevel = 0;//用户身份的
    private int openFamily = 1; //0关闭，1开启
    //更多权益
    private List<MemberNewBean.DataBean.VipBoxListBean> vipBoxList;
    private MemberMoreAllAdapter mMoreAllAdapter;
    //广告
    private List<MemberNewBean.DataBean.MessageListBean> mAdList;
    //每月0元购
    private List<MemberNewBean.DataBean.LevelDetail3Bean.ListBean> mFreeList;
    private MemberFreeAdapter mFreeAdapter;
    //特惠购列表
    private List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> mCheapList;
    private List<Fragment> mFragments;
    private HomePageAdapter mHomeAdapter;
    private List<ImageView> point;
    //视频会员月卡
    private List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> mVideoList;
    private MemberVideoAdapter mVideoAdapter;
    //吃喝玩乐
    private List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> mPlayList;
    private MemberVideoAdapter mPlayAdapter;
    //轮播图
    private KTPagerAdapter3 pagerAdapter;
    private List<ImageView> pagerPoint;
    private List<EvaluationTabBean.DataBean.AdListBean> pagerList;
    private int currentItem = 0;
    private int count = 0;
    //支付宝支付回调
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 100){
                if (count > 1){
                    currentItem = currentItem % (count + 1) + 1;
                    if (currentItem == 1) {
                        mBinding.memberAd.setCurrentItem(currentItem, false);
                        mHandler.sendEmptyMessage(100);
                    }else{
                        mBinding.memberAd.setCurrentItem(currentItem);
                        mHandler.sendEmptyMessageDelayed(100,5000);
                    }
                }
            }
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    private Boolean isSpace = false;//是否展开的。默认为false
    private Boolean isyear = false;//是否打开仅年卡购买

    public static MemberFragment getInstance(String type , boolean isyear){
        MemberFragment fragment = new MemberFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",type);//1:fragment 2:activity
        bundle.putBoolean("isyear", isyear);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_new,container,false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        mBinding.swipeToLoad.setOnRefreshListener(this);
        type = getArguments().getString("type","1");
        isyear = getArguments().getBoolean("isyear",false);
        //呼吸灯动画
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(getContext(), R.anim.free_scale);
        mBinding.memberUserPay.startAnimation(animation);

        //广告
        mAdList = new ArrayList<>();
        //更多权益
        vipBoxList = new ArrayList<>();
        mMoreAllAdapter = new MemberMoreAllAdapter(vipBoxList,getContext());
        mMoreAllAdapter.setOnItem(this);
        mBinding.memberMoreContainer.setLayoutManager(new GridLayoutManager(getContext(),4));
        mBinding.memberMoreContainer.setAdapter(mMoreAllAdapter);
        mBinding.memberMoreContainer.setNestedScrollingEnabled(false);
        mBinding.memberMoreContainer.setFocusable(false);//拒绝首次进入页面时滑动
        //每月0元购
        mFreeList = new ArrayList<>();
        mBinding.memberFree.setLayoutManager(new GridLayoutManager(getContext(),3));
        mBinding.memberFree.setNestedScrollingEnabled(false);
        mFreeAdapter = new MemberFreeAdapter(mFreeList,getContext());
        mFreeAdapter.setOnItemClick(this);
        mBinding.memberFree.setAdapter(mFreeAdapter);
        mBinding.memberFree.setFocusable(false);//拒绝首次进入页面时滑动
        //特惠购列表
        point = new ArrayList<>();
        mCheapList = new ArrayList<>();
        mFragments = new ArrayList<>();
        mHomeAdapter = new HomePageAdapter(getChildFragmentManager());
        mHomeAdapter.setFragments(mFragments);
        mBinding.memberCheap.setAdapter(mHomeAdapter);
        mBinding.memberCheap.addOnPageChangeListener(this);
        //视频月卡
        mVideoList = new ArrayList<>();
        mVideoAdapter = new MemberVideoAdapter(mVideoList,getContext(),1);
        mBinding.memberVideo.setLayoutManager(new GridLayoutManager(getContext(),4));
        mBinding.memberVideo.setNestedScrollingEnabled(false);
        mBinding.memberVideo.setAdapter(mVideoAdapter);
        mBinding.memberVideo.setFocusable(false);
        //吃喝玩乐
        mPlayList = new ArrayList<>();
        mPlayAdapter = new MemberVideoAdapter(mPlayList,getContext(),2);
        mPlayAdapter.setOnItem(this);
        mBinding.memberPlay.setLayoutManager(new GridLayoutManager(getContext(),4));
        mBinding.memberPlay.setNestedScrollingEnabled(false);
        mBinding.memberPlay.setAdapter(mPlayAdapter);
        mBinding.memberPlay.setFocusable(false);
        //人物广告
        pagerAdapter = new KTPagerAdapter3(getContext());
        pagerPoint  = new ArrayList<>();
        pagerList = new ArrayList<>();
        pagerAdapter.setList(pagerList);
        pagerAdapter.setPoint(pagerPoint);
        pagerAdapter.setImgCenter(false);
        mBinding.memberAd.setAdapter(pagerAdapter);
        mBinding.memberAd.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pagerPoint.size(); i++) {
                    if (i == toRealPosition(position)) {
                        pagerPoint.get(i).setImageResource(R.drawable.banner_down3);
                    } else {
                        pagerPoint.get(i).setImageResource(R.drawable.banner_up3);
                    }
                }
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 0://No operation
                        if (currentItem == 0) {
                            mBinding.memberAd.setCurrentItem(count, false);
                        } else if (currentItem == count + 1) {
                            mBinding.memberAd.setCurrentItem(1, false);
                        }
                        break;
                    case 1:
                        if (currentItem == count + 1) {
                            mBinding.memberAd.setCurrentItem(1, false);
                        } else if (currentItem == 0) {
                            mBinding.memberAd.setCurrentItem(count, false);
                        }
                        break;
                }
            }
        });
        mBinding.memberAd.setOnTouchListener(new TouchViewPager.OnTouchListener() {
            @Override
            public void startAutoPlay() {
                mHandler.removeMessages(100);
                mHandler.sendEmptyMessageDelayed(100,5000);
            }

            @Override
            public void stopAutoPlay() {
                mHandler.removeMessages(100);
            }
        });

        if (type.equals("2")){
            SPUtils.getInstance().put(CommonDate.memberNotice, false);
            mBinding.swipeToLoad.post(() -> {
                mBinding.swipeToLoad.setRefreshing(true);
            });
            mBinding.memberBlack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.member_share:
                //咨询客服
                String wx = mBinding.memberWxCode.getText().toString().replace(getResources().getString(R.string.member_wx),"");
                DialogUtil.LoginDialog(getContext(), "官方客服微信：" + wx, "复制", "取消", v1 -> {
                    ClipboardManager clip = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("jipinshop", wx);
                    clip.setPrimaryClip(clipData);
                    ToastUtil.show("微信号复制成功");
                    SPUtils.getInstance().put(CommonDate.CLIP, wx);
                });
                break;
            case R.id.member_black:
                if (getActivity() != null){
                    getActivity().finish();
                }
                break;
            case R.id.member_buy:
                //购买
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                appStatisticalUtil.addEvent("huiyuan.click", this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
                if (isyear){
                    startActivityForResult(new Intent(getContext(), MemberBuyActivity.class)
                                    .putExtra("isBuy","1")
                                    .putExtra("level","2")
                            ,300);
                }else {
                    startActivityForResult(new Intent(getContext(), MemberBuyActivity.class)
                                    .putExtra("isBuy","1")
                            ,300);
                }
                break;
            case R.id.member_userPay:
                //续费
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                if (isyear){
                    startActivityForResult(new Intent(getContext(), MemberBuyActivity.class)
                                    .putExtra("isBuy","2")
                                    .putExtra("level","2")
                            ,300);
                }else {
                    startActivityForResult(new Intent(getContext(), MemberBuyActivity.class)
                                    .putExtra("isBuy","2")
                            ,300);
                }
                break;
            case R.id.member_adContainer:
                //人员广告
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), InvitationNewActivity.class));
                break;
            case R.id.member_copyServer:
            case R.id.member_copy:
                //复制导师微信
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                ClipboardManager clip = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("jipinshop", mBinding.memberWxCode.getText().toString().replace(getResources().getString(R.string.member_wx),""));
                clip.setPrimaryClip(clipData);
                SPUtils.getInstance().put(CommonDate.CLIP,  mBinding.memberWxCode.getText().toString().replace(getResources().getString(R.string.member_wx),""));
                ToastUtil.show("复制成功");
                break;
            case R.id.member_shop2Container:
                //跳转到特惠购
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), CheapBuyActivity.class));
                break;
            case R.id.member_playUp:
            case R.id.member_playMore:
                //吃喝玩乐展开更多
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.memberPlayContainer.getLayoutParams();
                if (!isSpace){
                    //未展开
                    layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    isSpace  = true;
                    mBinding.memberPlayUp.setVisibility(View.VISIBLE);
                    mBinding.memberPlayMore.setVisibility(View.GONE);
                }else {
                    //已展开
                    layoutParams.height = (int) getResources().getDimension(R.dimen.y801);
                    isSpace = false;
                    mBinding.memberPlayMore.setVisibility(View.VISIBLE);
                    mBinding.memberPlayUp.setVisibility(View.GONE);
                }
                mBinding.memberPlayContainer.setLayoutParams(layoutParams);
                break;
            case R.id.member_family:
                //全家共享
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                if (userLevel == 0){
                    startActivityForResult(new Intent(getContext(), MemberBuyActivity.class)
                            .putExtra("isBuy","1")
                    ,300);
                    return;
                }
                if (openFamily == 1){
                    startActivity(new Intent(getContext(), FamilyActivity.class)
                            .putExtra("userLevel", userLevel)
                    );
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.levelIndex(this.bindToLifecycle());
    }

    @Override
    public void onSuccess(MemberNewBean bean) {
        mBinding.swipeToLoad.setRefreshing(false);
        openFamily = bean.getOpenFamily();
        mBinding.setBean(bean.getData());
        mBinding.executePendingBindings();
        SpannableString string = new SpannableString("   " + bean.getRemind());
        CenteredImageSpan imageSpan = new CenteredImageSpan(getContext(),R.mipmap.member1_notice);
        string.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mBinding.memberMoneyNotice.setText(string);
        //广告
        mAdList.clear();
        mAdList.addAll(bean.getData().getMessageList());
        mPresenter.adFlipper(getContext(),mBinding.viewFlipper,mAdList);
        //更多权益
        vipBoxList.clear();
        vipBoxList.addAll(bean.getData().getVipBoxList());
        mMoreAllAdapter.notifyDataSetChanged();
        //特权一
        GlideApp.loderImage(getContext(),bean.getData().getLevelDetail1().getImg(),mBinding.memberMoney,0,0);
        if (bean.getData().getLevelDetail3() == null){
            //周卡月卡没有特权二
            mBinding.memberFreeFContainer.setVisibility(View.GONE);
        }else {
            //特权二
            mBinding.memberFreeFContainer.setVisibility(View.VISIBLE);
            mFreeList.clear();
            mFreeList.addAll(bean.getData().getLevelDetail3().getList());
            mFreeAdapter.notifyDataSetChanged();
            if (TextUtils.isEmpty(bean.getData().getLevelDetail3().getTitle3())){
                mBinding.memberFreeTitle.setVisibility(View.GONE);
            }else {
                mBinding.memberFreeTitle.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(bean.getData().getLevelDetail3().getTitle4())){
                mBinding.memberFreeFee.setVisibility(View.GONE);
            }else {
                mBinding.memberFreeFee.setVisibility(View.VISIBLE);
            }
        }
        //特权三
        mCheapList.clear();
        mCheapList.addAll(bean.getData().getLevelDetail4().getList());
        mFragments.clear();
        mFragments.add(CheapFragment.getInstence(0,mCheapList));
        mFragments.add(CheapFragment.getInstence(6,mCheapList));
        mHomeAdapter.updateData(mFragments);
        mPresenter.initBanner(mFragments,getContext(),point,mBinding.memberPoint);
        if (mCheapList.get(5).getStatus() == 0 || mCheapList.get(5).getStatus() == 2){
            mBinding.memberCheap.setCurrentItem(0);
        }else {
            mBinding.memberCheap.setCurrentItem(1);
        }
        if (TextUtils.isEmpty(bean.getData().getLevelDetail4().getTitle3())){
            mBinding.memberMonthTitle.setVisibility(View.GONE);
        }else {
            mBinding.memberMonthTitle.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(bean.getData().getLevelDetail4().getTitle4())){
            mBinding.memberMonthFee.setVisibility(View.GONE);
        }else {
            mBinding.memberMonthFee.setVisibility(View.VISIBLE);
        }
        //特权四
        GlideApp.loderCircleImage(getContext(),bean.getData().getLevelDetail5().getList().get(0),mBinding.memberFamilyOne,0,0);
        GlideApp.loderCircleImage(getContext(),bean.getData().getLevelDetail5().getList().get(1),mBinding.memberFamilyTwo,0,0);
        GlideApp.loderCircleImage(getContext(),bean.getData().getLevelDetail5().getList().get(2),mBinding.memberFamilyThree,0,0);
        GlideApp.loderCircleImage(getContext(),bean.getData().getLevelDetail5().getList().get(3),mBinding.memberFamilyFore,0,0);
        if (TextUtils.isEmpty(bean.getData().getLevelDetail5().getTitle3())){
            mBinding.memberFamilyTitle.setVisibility(View.GONE);
        }else {
            mBinding.memberFamilyTitle.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(bean.getData().getLevelDetail5().getTitle4())){
            mBinding.memberFamilyFee.setVisibility(View.GONE);
        }else {
            mBinding.memberFamilyFee.setVisibility(View.VISIBLE);
        }
        //特权五 视频
        if (bean.getData().getLevel() == 1 || bean.getData().getLevel() == 3){
            mBinding.memberVideoFContainer.setVisibility(View.GONE);
        }else {
            mBinding.memberVideoFContainer.setVisibility(View.VISIBLE);
            mVideoList.clear();
            mVideoList.addAll(bean.getData().getLevelDetail7().getList());
            mVideoAdapter.notifyDataSetChanged();
            if (TextUtils.isEmpty(bean.getData().getLevelDetail7().getTitle3())){
                mBinding.memberVideoTitle.setVisibility(View.GONE);
            }else {
                mBinding.memberVideoTitle.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(bean.getData().getLevelDetail7().getTitle4())){
                mBinding.memberVideoFee.setVisibility(View.GONE);
            }else {
                mBinding.memberVideoFee.setVisibility(View.VISIBLE);
            }
            if (bean.getData().getLevel() == 2 && bean.getData().getLevelDetail7().getTitle3().equals("待领取")){
                mBinding.memberServerContainer.setVisibility(View.VISIBLE);
            }else {
                mBinding.memberServerContainer.setVisibility(View.GONE);
            }
        }
        //特权六 会员极币
        GlideApp.loderImage(getContext(),bean.getData().getLevelDetail6().getImg(),mBinding.memberSignImg,0,0);
        //特权七
        mPlayList.clear();
        mPlayList.addAll(bean.getData().getLevelDetail8().getList());
        mPlayAdapter.notifyDataSetChanged();
        mBinding.memberPlayUp.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.memberPlayContainer.getLayoutParams();
        if (mPlayList.size() > 16){
            isSpace = false;
            mBinding.memberPlayMore.setVisibility(View.VISIBLE);
            layoutParams.height = (int) getResources().getDimension(R.dimen.y801);
        }else {
            isSpace = true;
            layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            mBinding.memberPlayContainer.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.y40));
            mBinding.memberPlayMore.setVisibility(View.GONE);
        }
        mBinding.memberPlayContainer.setLayoutParams(layoutParams);
        //人物广告
        pagerList.clear();
        if (bean.getData().getAdList().size() > 1){
            for (int i = 0; i <= bean.getData().getAdList().size() + 1; i++) {//轮播图数据
                if (i == 0){//加入最后一个
                    pagerList.add(bean.getData().getAdList().get(bean.getData().getAdList().size() - 1));
                }else if ( i == (bean.getData().getAdList().size() + 1)){//加入第一个
                    pagerList.add(bean.getData().getAdList().get(0));
                }else {//正常数据
                    pagerList.add(bean.getData().getAdList().get(i - 1));
                }
            }
        }else{
            pagerList.addAll(bean.getData().getAdList());
        }
        initBanner();
        //身份处理  0 普通 ， 1 月卡 ，2年卡
        userLevel = bean.getData().getLevel();
        if (bean.getData().getLevel() == 0){
            mBinding.memberAdContainer.setVisibility(View.VISIBLE);
            mBinding.memberMemberContainer.setVisibility(View.GONE);
            mBinding.memberTitle1.setText("开通VIP可享更多权益");
            mBinding.memberNoMemberContainer.setVisibility(View.VISIBLE);
        }else {
            mBinding.memberAdContainer.setVisibility(View.GONE);
            mBinding.memberNoMemberContainer.setVisibility(View.GONE);
            mBinding.memberMemberContainer.setVisibility(View.VISIBLE);
            if (bean.getData().getLevel() == 1){
                mBinding.memberTitle1.setText("月卡VIP特权");
                mBinding.memberTab.setImageResource(R.mipmap.member1_month_tab);
                mBinding.memberMemberContainer.setBackgroundResource(R.mipmap.member1_month_bg);
                mBinding.memberUserName.setTextColor(getContext().getResources().getColor(R.color.color_E7C19F));
                mBinding.memberUserTime.setTextColor(getContext().getResources().getColor(R.color.color_E7C19F));
                mBinding.memberUserPay.setVisibility(View.VISIBLE);
                mBinding.memberUserPay.setBackgroundResource(R.drawable.bg_e8a971);
                mBinding.memberUserPay.setTextColor(getContext().getResources().getColor(R.color.color_white));
            }else if (bean.getData().getLevel() == 2){
                mBinding.memberTitle1.setText("年卡VIP特权");
                mBinding.memberTab.setImageResource(R.mipmap.member1_year_tab);
                mBinding.memberMemberContainer.setBackgroundResource(R.mipmap.member1_year_bg);
                mBinding.memberUserName.setTextColor(getContext().getResources().getColor(R.color.color_433A37));
                mBinding.memberUserTime.setTextColor(getContext().getResources().getColor(R.color.color_433A37));
                mBinding.memberUserPay.setVisibility(View.VISIBLE);
                mBinding.memberUserPay.setBackgroundResource(R.drawable.bg_342f2f);
                mBinding.memberUserPay.setTextColor(getContext().getResources().getColor(R.color.color_E7C19F));
            }else {
                mBinding.memberTitle1.setText("周卡VIP特权");
                mBinding.memberTab.setImageResource(R.mipmap.member1_week_tab);
                mBinding.memberMemberContainer.setBackgroundResource(R.mipmap.member1_month_bg);
                mBinding.memberUserName.setTextColor(getContext().getResources().getColor(R.color.color_E7C19F));
                mBinding.memberUserTime.setTextColor(getContext().getResources().getColor(R.color.color_E7C19F));
                mBinding.memberUserPay.setVisibility(View.VISIBLE);
                mBinding.memberUserPay.setBackgroundResource(R.drawable.bg_e8a971);
                mBinding.memberUserPay.setTextColor(getContext().getResources().getColor(R.color.color_white));
            }
        }
        once = false;
    }

    @Override
    public void onFile(String error) {
        mBinding.swipeToLoad.setRefreshing(false);
        ToastUtil.show(error);
        once = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!once){
            mPresenter.levelIndex(this.bindToLifecycle());
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < point.size(); i++) {
            if (i == position % mFragments.size()){
                point.get(i).setImageResource(R.drawable.banner_down3);
            }else {
                point.get(i).setImageResource(R.drawable.banner_up3);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

    //吃喝玩乐跳转
    @Override
    public void onItem(int position) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        if (userLevel == 0){
            startActivityForResult(new Intent(getContext(), MemberBuyActivity.class)
                    .putExtra("isBuy","1")
            ,300);
            return;
        }
        ShopJumpUtil.openBanner(getContext(),mPlayList.get(position).getType(),
                mPlayList.get(position).getTargetId(),mPlayList.get(position).getTitle(),
                mPlayList.get(position).getSource() , mPlayList.get(position).getRemark());
    }

    //点击会员权益滑动到固定位置
    @Override
    public void onItemSlide(int position) {
        switch (vipBoxList.get(position).getType()){
            case 1001://滑动到权限一
                mBinding.swipeTarget.scrollTo(0,mBinding.memberBg3.getTop());
                break;
            case 1002://滑动到权限二
                mBinding.swipeTarget.scrollTo(0,mBinding.memberBg5.getTop());
                break;
            case 1003://滑动到权限三
                mBinding.swipeTarget.scrollTo(0,mBinding.memberBg4.getTop());
                break;
            case 1004://滑动到权限4
                mBinding.swipeTarget.scrollTo(0,mBinding.memberBg7.getTop());
                break;
            case 1005://滑动到权限5
                mBinding.swipeTarget.scrollTo(0,mBinding.memberBg9.getTop());
                break;
            case 1006://滑动到权限6
                mBinding.swipeTarget.scrollTo(0,mBinding.memberBg8.getTop());
                break;
            case 1007://滑动到权限7
                mBinding.swipeTarget.scrollTo(0,mBinding.memberBg10.getTop());
                break;
        }
    }

    //返回真实的位置
    public int toRealPosition(int position) {
        int realPosition = 0;
        if (count != 0) {
            realPosition = (position - 1) % count;
        }
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }

    public void initBanner() {
        if (pagerList.size() > 1){
            count = pagerList.size() - 2;
        }else{
            count = pagerList.size();
        }
        mHandler.removeMessages(100);//刷新时，要删除handler的请求
        mHandler.sendEmptyMessageDelayed(100,5000);
        pagerPoint.clear();
        mBinding.memberAdPoint.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            pagerPoint.add(imageView);
            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_down3);
            } else {
                imageView.setImageResource(R.drawable.banner_up3);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.x4);
            layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.x4);
            mBinding.memberAdPoint.addView(imageView, layoutParams);
        }
        pagerAdapter.notifyDataSetChanged();
        if (count > 1){
            mBinding.memberAdPoint.setVisibility(View.VISIBLE);
            mBinding.memberAd.setCurrentItem(1,false);
        }else{
            mBinding.memberAdPoint.setVisibility(View.INVISIBLE);
            mBinding.memberAd.setCurrentItem(0,false);
        }
        mBinding.memberAd.setOffscreenPageLimit(pagerList.size() - 1);//防止图片重叠的情况
    }

    //每月0元购跳转
    @Override
    public void onZeroBuy() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        if (userLevel == 0){
            startActivityForResult(new Intent(getContext(), MemberBuyActivity.class)
                    .putExtra("isBuy","1")
            ,300);
            return;
        }
        startActivity(new Intent(getContext(), ZeroActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == 200){//支付会员成功返回弹出弹框
            String level = data.getStringExtra("level");
            DialogUtil.paySucDialog(getContext(), level, v -> {
                mPresenter.initShare(this.bindToLifecycle());
                mPresenter.taskFinish(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            });
        }
    }

    @Override
    public void onCommenFile(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void initShare(ShareInfoBean bean) {
        new ShareUtils(getActivity(), SHARE_MEDIA.WEIXIN)
                .shareImage(getActivity(), bean.getData().getImgUrl());
    }
}
