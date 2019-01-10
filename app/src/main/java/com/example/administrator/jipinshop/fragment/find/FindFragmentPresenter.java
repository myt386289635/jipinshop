package com.example.administrator.jipinshop.fragment.find;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FindFragmentPresenter {

    private Repository mRepository;
    private FindView mView;

    public void setView(FindView view) {
        mView = view;
    }

    @Inject
    public FindFragmentPresenter(Repository repository) {
        this.mRepository = repository;
    }

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void initTab(LifecycleTransformer<EvaluationTabBean> transformer){
        mRepository.findTab()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if(bean.getCode() == 0){
                        if(mView != null){
                            mView.onSucTab(bean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFaile(bean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFaile(throwable.getMessage());
                    }
                });
    }
}
