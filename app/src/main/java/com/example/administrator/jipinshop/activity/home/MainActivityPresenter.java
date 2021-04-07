package com.example.administrator.jipinshop.activity.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {

    private Repository mRepository;
    private MainView mView;

    public void setView(MainView view) {
        mView = view;
    }

    @Inject
    public MainActivityPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void initTabLayout(Context context , TabLayout tabLayout){
        View view2 = LayoutInflater.from(context).inflate(R.layout.tablayout_item2, null);
        tabLayout.getTabAt(0).setCustomView(view2);
        View view3 = LayoutInflater.from(context).inflate(R.layout.tablayout_item3,null);
        tabLayout.getTabAt(1).setCustomView(view3);
        View view1 = LayoutInflater.from(context).inflate(R.layout.tablayout_item1,null);
        tabLayout.getTabAt(2).setCustomView(view1);
        View view4 = LayoutInflater.from(context).inflate(R.layout.tablayout_item4, null);
        tabLayout.getTabAt(3).setCustomView(view4);
        View view5 = LayoutInflater.from(context).inflate(R.layout.tablayout_item5, null);
        tabLayout.getTabAt(4).setCustomView(view5);
        //水波纹颜色
        tabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
    }

    //拦截tab事件
    public void initTab(Context context , TabLayout tabLayout , List<Fragment> mFragments){
        for (int i = 0; i < mFragments.size(); i++) {
            View tab = (View) tabLayout.getTabAt(i).getCustomView().getParent();
            tab.setOnTouchListener((view, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                        if (ClickUtil.isFastDoubleClick(800)) {
                        } else {
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                        return true; // 拦截
                    }
                }
                return false;// 不拦截
            });
        }
    }

    public void getAppVersion(LifecycleTransformer<AppVersionbean> transformer){
        mRepository.getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(appVersionbean -> {
                    if(appVersionbean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(appVersionbean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile();
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile();
                    }
                });
    }

    /**
     * 获取弹窗信息
     */
    public void getPopInfo(LifecycleTransformer<PopInfoBean> transformer){
        mRepository.getPopInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(popInfoBean -> {
                    if (popInfoBean.getCode() == 0){
                        if(mView != null){
                            mView.onDialogSuc(popInfoBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onDialogFile();
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onDialogFile();
                    }
                });
    }

    /**
     * 通过淘口令获取商品信息
     */
    public void getGoodsByTkl(String tkl, LifecycleTransformer<TklBean> transformer){
        mRepository.getGoodsByTkl(tkl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(tklBean -> {
                    if (tklBean.getCode() == 0){
                        mView.onTklDialog(tklBean,tkl);
                    }
                }, throwable -> {

                });
    }

    public void addInvitationCode(String invitationCode, Dialog dialog, LifecycleTransformer<SuccessBean> transformer){
        mRepository.addInvitationCode(invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        mView.onInvitationSuc(dialog);
                    }else {
                        mView.onInvitationFile(successBean.getMsg());
                    }
                }, throwable -> {
                    mView.onInvitationFile(throwable.getMessage());
                });
    }

    //app广告数据
    public void adList(LifecycleTransformer<SucBean<EvaluationTabBean.DataBean.AdListBean>> transformer){
        mRepository.adList("15")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(adListBeanSucBean -> {
                    if (adListBeanSucBean.getCode() == 0){
                        if (mView != null){
                            mView.onAdList(adListBeanSucBean);
                        }
                    }
                }, throwable -> {});

    }

    //获取隐私协议版本号
    public void getPrivateVersion(LifecycleTransformer<ImageBean> transformer){
        mRepository.getPrivateVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onStartPrivate(bean);
                    }else {
                        mView.onStartFile();
                    }
                }, throwable -> {
                    mView.onStartFile();
                });
    }

    public void getNewDialog(LifecycleTransformer<PopBean> transformer){
        mRepository.getPopInfo("3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(popBean -> {
                    if (popBean.getCode() == 0) {
                        mView.onNewDialogSuc(popBean);
                    }
                }, throwable -> {});
    }

    public void getGroupDialog(LifecycleTransformer<PopBean> transformer){
        mRepository.getGroupDialog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0) {
                        mView.onGroupDialogSuc(bean);
                    }else {
                        mView.onGroupDialogSuc(null);
                    }
                }, throwable -> {
                    mView.onGroupDialogSuc(null);
                });
    }

    //下单分享获得极币
    public void taskFinish(LifecycleTransformer<TaskFinishBean> transformer){
        mRepository.taskFinish("28")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(taskFinishBean -> { }, throwable ->{ });
    }

    //下单分享数据
    public void buyShare(SHARE_MEDIA share_media,LifecycleTransformer<ShareInfoBean> transformer){
        mRepository.getShareInfo(8)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.buyShare(share_media , bean);
                    }else {
                        mView.onInvitationFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onInvitationFile(throwable.getMessage());
                });
    }

    //获取下单弹窗
    public void getbuyDialog(LifecycleTransformer<PopInfoBean> transformer){
        mRepository.getPopInfoOther("5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(popBean -> {
                    if (popBean.getCode() == 0) {
                        mView.onBuyDialogSuc(popBean);
                    }
                }, throwable -> {});
    }
}
