package com.example.administrator.jipinshop.activity.sreach.result;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.TBSreachResultBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.DeviceUuidFactory;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/12/3
 * @Describe
 */
public class TBSreachResultPresenter {

    private Repository mRepository;
    private TBSreachResultView mView;

    public void setView(TBSreachResultView view) {
        mView = view;
    }

    @Inject
    public TBSreachResultPresenter(Repository repository) {
        mRepository = repository;
    }

    public void searchTBGoods(Context context,String asc , String keyword, String orderByType , int page, String type , LifecycleTransformer<TBSreachResultBean> transformer){
        Map<String,String> hashMap =  DeviceUuidFactory.getIdfa(context);
        if (!TextUtils.isEmpty(asc)){
            hashMap.put("asc",asc);
        }
        hashMap.put("keyword",keyword);
        hashMap.put("orderByType",orderByType);
        hashMap.put("page",page + "");
        hashMap.put("type" , type);
        mRepository.searchTBGoods(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(tbSreachResultBean -> {
                    if (tbSreachResultBean.getCode() == 0){
                        mView.onSuccess(tbSreachResultBean);
                    }else {
                        mView.onFile(tbSreachResultBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

}
