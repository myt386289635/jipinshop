package com.example.administrator.jipinshop.activity.info.editname;

import android.app.Dialog;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/9/30
 * @Describe
 */
public class EditNamePresent {

    Repository mRepository;
    private EditNameView mView;

    public void setView(EditNameView view) {
        mView = view;
    }

    @Inject
    public EditNamePresent(Repository repository) {
        mRepository = repository;
    }

    public void SaveUserInfo(String content, LifecycleTransformer<SuccessBean> transformer, Dialog dialog){
        Map<String,String> map = new HashMap<>();
        map.put("nickname",content);
        mRepository.userUpdate(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if(mView != null){
                        mView.Success(successBean);
                    }
                }, throwable -> {

                });
    }

}
