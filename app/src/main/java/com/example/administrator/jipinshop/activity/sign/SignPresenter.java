package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.bean.TeacherBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.bean.SHARE_MEDIA;

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

    public void scrollTitle(RecyclerView recyclerView, RelativeLayout container){
        final float[] a = {0};
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                a[0] = a[0] + dy;//由于这块不会出现item插入\移动\删除，因此累加Y轴偏移量是可行的
                float b = a[0] / 1000;
                float max = (float) Math.min(1, b * 2);
                container.setAlpha(max);
            }
        });
    }

    //签到
    public void sign(LifecycleTransformer<SignInsertBean> transformer){
        mRepository.signInsert()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(signInsertBean -> {
                    if(signInsertBean.getCode() == 0){
                        mView.signSuc(signInsertBean);
                    }else{
                        mView.onFile(signInsertBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    //补签
    public void noSign(int day,LifecycleTransformer<SignInsertBean> transformer){
        mRepository.noSignin(day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(signInsertBean -> {
                    if(signInsertBean.getCode() == 0){
                        mView.noSignSuc(signInsertBean);
                    }else{
                        mView.onFile(signInsertBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    //赚钱页面
    public void getData(LifecycleTransformer<DailyTaskBean> transformer){
        mRepository.DailytaskIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(dailyTaskBean -> {
                    if(dailyTaskBean.getCode() == 0){
                        mView.onIndex(dailyTaskBean);
                    }else {
                        mView.onFile(dailyTaskBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
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
    public void mallList(int page ,LifecycleTransformer<MallBean> transformer){
        mRepository.mallList(page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(mallBean -> {
                    if(mallBean.getCode() == 0){
                        mView.onSuccess(mallBean);
                    }else {
                        mView.onFile(mallBean.getMsg());
                    }
                }, throwable ->{
                    mView.onFile(throwable.getMessage());
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

    //获取分享信息
    public void initShare(SHARE_MEDIA share_media, LifecycleTransformer<ShareInfoBean> transformer){
        mRepository.getShareInfo(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.initShare(share_media,bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    //分享获取极币
    public void taskFinish(String type ,LifecycleTransformer<TaskFinishBean> transformer){
        mRepository.taskFinish(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(taskFinishBean -> { }, throwable ->{ });
    }
}
