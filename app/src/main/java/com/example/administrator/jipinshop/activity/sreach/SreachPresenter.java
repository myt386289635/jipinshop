package com.example.administrator.jipinshop.activity.sreach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.google.android.flexbox.FlexboxLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SreachPresenter {

    Repository mRepository;
    private SreachView mView;

    public void setView(SreachView view) {
        mView = view;
    }

    @Inject
    public SreachPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initHot(final Context context, final FlexboxLayout flexboxLayout,List<SreachHistoryBean.DataBean.HotWordListBean> hotText) {
        for (int i = 0; i < hotText.size(); i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
            textView.setText(hotText.get(i).getWord());
            delete.setVisibility(View.GONE);
            int finalI = i;
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.jump("2", textView.getText().toString(), finalI);
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }


    /**
     * 获取搜索记录
     */
    public void searchLog(String type ,LifecycleTransformer<SreachHistoryBean> transformer){
        mRepository.searchLog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(sreachHistoryBean -> {
                    if(sreachHistoryBean.getCode() == 0){
                        if(type.equals("1")){
                            if(mView != null){
                                mView.Success(sreachHistoryBean);
                            }
                        }else {
                            if(mView != null){
                                mView.SuccessHistory(sreachHistoryBean);
                            }
                        }
                    }else {
                        if(mView != null){
                            mView.onFaile(sreachHistoryBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除所有搜索记录
     */
    public void deleteAll(LifecycleTransformer<SuccessBean> transformer){
        mRepository.searchDeleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.SuccessDeleteAll(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFaile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFaile(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除单条搜索记录
     */
    public void searchDelete(int position ,String id,LifecycleTransformer<SuccessBean> transformer){
        mRepository.searchDelete(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.SuccessDelete(position,successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFaile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFaile(throwable.getMessage());
                    }
                });
    }
}
