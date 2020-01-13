package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class SignPresenter {

    private Repository mRepository;
    private SignView mView;

    public void setSignView(SignView signView) {
        mView = signView;
    }

    @Inject
    public SignPresenter(Repository repository) {
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

    /**
     * 查询7天签到状态
     * @param transformer
     */
    public void signInfo(LifecycleTransformer<SignBean> transformer){
        mRepository.sign()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(signBean -> {
                    if(signBean.getCode() == 0){
                        if(mView != null){
                            mView.getInfoSuc(signBean);
                        }
                    }else {
                        if(mView != null){
                            mView.getInfoFaile(signBean.getMsg());
                        }
                    }

                }, throwable -> {
                    if(mView != null){
                        mView.getInfoFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 签到
     * @param transformer
     */
    public void sign(LifecycleTransformer<SignInsertBean> transformer){
        mRepository.signInsert()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(signInsertBean -> {
                    if(signInsertBean.getCode() == 0){
                        if(mView != null){
                            mView.signSuc(signInsertBean);
                        }
                    }else {
                        if(mView != null){
                            mView.signFaile(signInsertBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.signFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 每日任务
     */
    public void DailytaskList(LifecycleTransformer<DailyTaskBean> transformer){
        mRepository.DailytaskList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(dailyTaskBean -> {
                    if(dailyTaskBean.getCode() == 0){
                        if(mView != null){
                            mView.getDayList(dailyTaskBean);
                        }
                    }else {
                        if(mView != null){
                            mView.getInfoFaile(dailyTaskBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.getInfoFaile(throwable.getMessage());
                    }
                });
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
