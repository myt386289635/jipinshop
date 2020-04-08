package com.example.administrator.jipinshop.activity.setting.bind;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/4/7
 * @Describe
 */
public class BindWXPresenter {

    Repository mRepository;
    private BindWXView mView;

    public void setView(BindWXView view) {
        mView = view;
    }

    @Inject
    public BindWXPresenter(Repository repository) {
        mRepository = repository;
    }

    public void SaveUserInfo(String content, LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> map = new HashMap<>();
        map.put("wechat",content);
        mRepository.userUpdate(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess();
                        }
                    }else {
                        if (mView != null)
                            mView.onFile(successBean.getMsg());
                    }
                }, throwable -> {
                    if (mView != null)
                        mView.onFile(throwable.getMessage());
                });
    }
}
