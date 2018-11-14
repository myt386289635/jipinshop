package com.example.administrator.jipinshop.fragment.mine;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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


    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void getMoney(Context context,LifecycleTransformer<AccountBean> transformer){
        mRepository.account()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(accountBean -> {
                    if(mView != null){
                        mView.successMoney(accountBean);
                    }
                }, throwable -> {
                    Toast.makeText(context, "佣金金额获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                    Log.d("MinePresenter", throwable.getMessage());
                });
    }

    public void modelUser(LifecycleTransformer<UserInfoBean> transformer){
        mRepository.modelUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(userInfoBean -> {
                    if(userInfoBean.getCode() == 200){
                        if(mView != null){
                            mView.successUserInfo(userInfoBean);
                        }
                    }else {
                        if(mView != null){
                            mView.FaileUserInfo(userInfoBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.FaileUserInfo("用户信息更新失败，请检查网络");
                    }
                    Log.d("MinePresenter", throwable.getMessage());
                });
    }

}
