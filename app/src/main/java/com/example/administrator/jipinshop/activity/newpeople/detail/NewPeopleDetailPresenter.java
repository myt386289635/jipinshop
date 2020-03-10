package com.example.administrator.jipinshop.activity.newpeople.detail;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe
 */
public class NewPeopleDetailPresenter {

    private Repository mRepository;
    private NewPeopleDetailView mView;

    public void setView(NewPeopleDetailView view) {
        mView = view;
    }

    @Inject
    public NewPeopleDetailPresenter(Repository repository) {
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

    public void initBanner(List<String> mBannerList , Context context , List<ImageView> point, LinearLayout mDetailPoint, NoPageBannerAdapter mBannerAdapter){
        for (int i = 0; i < mBannerList.size(); i++) {
            ImageView imageView = new ImageView(context);
            point.add(imageView);
            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_down);
            } else {
                imageView.setImageResource(R.drawable.banner_up);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin =context.getResources().getDimensionPixelSize(R.dimen.x4);
            layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x4);
            mDetailPoint.addView(imageView, layoutParams);
        }
        mBannerAdapter.notifyDataSetChanged();
    }

    public void listSimilerGoods(Map<String,String> map, String otherGoodsId, LifecycleTransformer<SimilerGoodsBean> transformer){
        Map<String,String> parament = new HashMap<>();
        parament.putAll(map);
        parament.put("otherGoodsId",otherGoodsId);
        mRepository.listSimilerGoods(parament)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.LikeSuccess(bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    public void newGoodsDetail(String otherGoodsId, LifecycleTransformer<TBShoppingDetailBean> transformer){
        mRepository.newGoodsDetail(otherGoodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    public void apply(String allowanceGoodsId , LifecycleTransformer<ImageBean> transformer){
        mRepository.allowanceApply(allowanceGoodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onBuySuccess(bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    /**
     * 获取商品详情
     */
    public void getGoodsDescImgs(String otherGoodsId , LifecycleTransformer<SucBean<String>> transformer ){
        mRepository.getGoodsDescImgs(otherGoodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        if (mView != null){
                            mView.onDescImgs(bean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(bean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 生成商品海报
     */
    public void getTbkGoodsPoster(String otherGoodsId ,String path , String shareName  , String shareImage, LifecycleTransformer<ImageBean> transformer){
        mRepository.getTbkGoodsPoster(otherGoodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if(bean.getCode() == 0 && !TextUtils.isEmpty(bean.getData())) {
                        if (mView != null) {
                            mView.onShareSuc(bean,path,shareName);
                        }
                    }else {
                        if(mView != null){
                            mView.onShareFile(path,shareName,shareImage);
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onShareFile(path,shareName,shareImage);
                    }
                });
    }

}
