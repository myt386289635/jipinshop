package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TeacherBean;
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

    //签到
    public void sign(int type , LifecycleTransformer<SignInsertBean> transformer){
        mRepository.signInsert()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(signInsertBean -> {
                    if(signInsertBean.getCode() == 0){
                        if(mView != null){
                            mView.signSuc(signInsertBean);
                        }
                    }else{
                        if (type == 1){
                            if (mView != null)
                                mView.signFile(signInsertBean.getCode(),signInsertBean.getMsg());
                        }else {
                            if(mView != null){
                                mView.onFile(signInsertBean.getMsg());
                            }
                        }
                    }
                }, throwable -> {
                    if (type == 1) {
                        if (mView != null)
                            mView.signFile(400,throwable.getMessage());
                    } else {
                        if (mView != null) {
                            mView.onFile(throwable.getMessage());
                        }
                    }
                });
    }

    //每日任务
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
                            mView.onFile(dailyTaskBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    //填写邀请码
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

    //极币商城
    public void mallList(LifecycleTransformer<MallBean> transformer){
        mRepository.mallList("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(mallBean -> {
                    if(mallBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(mallBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(mallBean.getMsg());
                        }
                    }
                }, throwable ->{
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void  getParentInfo(LifecycleTransformer<TeacherBean> transformer){
        mRepository.parentInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0) {
                        mView.onTeacher(bean);
                    } else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
