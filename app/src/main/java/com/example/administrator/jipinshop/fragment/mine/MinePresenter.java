package com.example.administrator.jipinshop.fragment.mine;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.databinding.FragmentMineBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe
 */
public class MinePresenter {

    Repository mRepository;
    MineView mView;

    public void setView(MineView view) {
        mView = view;
    }

    @Inject
    public MinePresenter(Repository repository) {
        mRepository = repository;
    }


    public void setStatusBarHight(FragmentMineBinding mBinding , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = mBinding.statusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void modelUser(LifecycleTransformer<UserInfoBean> transformer){
        mRepository.modelUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(userInfoBean -> {
                    if(userInfoBean.getCode() == 0){
                        if(mView != null){
                            mView.successUserInfo(userInfoBean);
                        }
                    }else {
                        if(mView != null){
                            mView.FaileUserInfo(userInfoBean);
                        }
                    }
                }, throwable -> {
                    ToastUtil.show("用户信息更新失败，请检查网络");
                });
    }

    /**
     * 更新用户信息 （与获取用户信息接口一样，只是用来实时更新用户信息）
     */
    public void updateInfo(LifecycleTransformer<UserInfoBean> transformer){
        mRepository.modelUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(userInfoBean -> {
                    if(userInfoBean.getCode() == 0){
                        if(mView != null){
                            mView.successUpdateInfo(userInfoBean);
                        }
                    }
                }, throwable -> {

                });
    }

    /**
     * 获取钱包未读消息
     */
    public void unMessage(LifecycleTransformer<UnMessageBean> ransformer){
        mRepository.unMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(unMessageBean -> {
                    if(mView != null){
                        mView.unMessageSuc(unMessageBean);
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.unMessageFaile(throwable.getMessage());
                    }
                });
    }


    public void myCommssionSummary(LifecycleTransformer<MyWalletBean> transformer){
        mRepository.myCommssionSummary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(myWalletBean -> {
                    if (myWalletBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(myWalletBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(myWalletBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void initBadgeView(QBadgeView mQBadgeView, ImageView imageView, Badge.OnDragStateChangedListener onDragStateChangedListener){
        mQBadgeView.bindTarget(imageView)
                .setBadgeTextSize(8,true)
                .setBadgeGravity( Gravity.END | Gravity.TOP)
                .setBadgePadding(2,true)
                .setGravityOffset(0,0,true)
                .setBadgeBackgroundColor(0xffFF0000)
                .setOnDragStateChangedListener(onDragStateChangedListener);
    }

    public void addInvitationCode(String invitationCode, Dialog dialog, InputMethodManager inputManager, LifecycleTransformer<SuccessBean> transformer){
        mRepository.addInvitationCode(invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        mView.onCodeSuc(dialog,inputManager,successBean);
                    }else {
                        mView.onFile(successBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
