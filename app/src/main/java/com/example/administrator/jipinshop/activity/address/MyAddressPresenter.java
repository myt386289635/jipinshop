package com.example.administrator.jipinshop.activity.address;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.bean.AddressBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe
 */
public class MyAddressPresenter {

    private Repository mRepository;
    private MyAddressView mView;

    public void setView(MyAddressView view) {
        mView = view;
    }

    @Inject
    public MyAddressPresenter(Repository repository) {
        mRepository = repository;
    }

    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * 查询收货地址
     */
    public void addresslist(LifecycleTransformer<AddressBean> transformer){
        mRepository.addresslist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(addressBean -> {
                    if(addressBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(addressBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(addressBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除收货地址
     */
    public void addressDelete(int position,String addressId,LifecycleTransformer<SuccessBean> transformer){
        mRepository.addressDelete(addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccessDelete(position);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileDelete(throwable.getMessage());
                    }
                });
    }

    /**
     * 设置默认地址
     */
    public void addressSetDefault(int position,String addressId,LifecycleTransformer<SuccessBean> transformer){
        mRepository.addressSetDefault(addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccessDefault(position);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileDelete(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileDelete(throwable.getMessage());
                    }
                });
    }

}
