package com.example.administrator.jipinshop.activity.message.system;

import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class SystemMessagePresenter {

    private Repository mRepository;
    private SystemMessageView mView;

    public void setView(SystemMessageView view) {
        mView = view;
    }

    @Inject
    public SystemMessagePresenter(Repository repository) {
        mRepository = repository;
    }

    /**
     * 获取消息列表
     */
    public void messageAll(String page ,LifecycleTransformer<SystemMessageBean> ransformer){
        mRepository.messageAll(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(systemMessageBean -> {
                    if(systemMessageBean.getCode() == 200){
                        if(mView != null){
                            mView.Success(systemMessageBean);
                        }
                    }else {
                        if(mView != null){
                            mView.Faile(systemMessageBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.Faile(throwable.getMessage());
                    }
                });
    }
}
