package com.example.administrator.jipinshop.activity.info.editsign;

import android.app.Dialog;

import com.example.administrator.jipinshop.activity.info.editname.EditNameView;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/8/21
 * @Describe
 */
public class EditSignPresenter {

    private Repository mRepository;
    private EditNameView mView;

    public void setView(EditNameView view) {
        mView = view;
    }

    @Inject
    public EditSignPresenter(Repository repository) {
        mRepository = repository;
    }

    public void SaveUserInfo(String content, LifecycleTransformer<SuccessBean> transformer, Dialog dialog){
        Map<String,String> map = new HashMap<>();
        map.put("detail",content);
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
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    ToastUtil.show(throwable.getMessage());
                });
    }
}
