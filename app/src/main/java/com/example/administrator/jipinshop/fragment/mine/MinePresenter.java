package com.example.administrator.jipinshop.fragment.mine;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.databinding.FragmentMineBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
}
