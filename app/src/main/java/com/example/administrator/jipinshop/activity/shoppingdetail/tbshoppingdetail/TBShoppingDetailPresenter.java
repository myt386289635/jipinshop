package com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter;
import com.example.administrator.jipinshop.bean.ClickUrlBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
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
 * @create 2019/11/26
 * @Describe
 */
public class TBShoppingDetailPresenter {

    private Repository mRepository;
    private TBShoppingDetailView mView;

    public void setView(TBShoppingDetailView view) {
        mView = view;
    }

    @Inject
    public TBShoppingDetailPresenter(Repository repository) {
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

    public void tbGoodsDetail(int type, String otherGoodsId , String source, LifecycleTransformer<TBShoppingDetailBean> transformer){
        mRepository.tbGoodsDetail(otherGoodsId,source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        if (type == 1){
                            mView.onSuccess(bean);
                        }else {
                            mView.onCollect(bean);
                        }
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    public void listSimilerGoods(Map<String,String> map,String otherGoodsId, LifecycleTransformer<SimilerGoodsBean> transformer){
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

    /**
     * 添加收藏
     */
    public void collectInsert(String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("type", "8");
        hashMap.put("targetId",goodsId);
        mRepository.collectInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0) {
                        if (mView != null) {
                            mView.onSucCollectInsert();
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 删除收藏
     */
    public void collectDelete(String goodsId , LifecycleTransformer<SuccessBean> transformer){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("type", "8");
        hashMap.put("targetId",goodsId);
        mRepository.collectDelete(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(successBean.getCode() == 0) {
                        if (mView != null) {
                            mView.onSucCollectDelete();
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 获取专属淘客链接
     */
    public void getGoodsClickUrl(String source , String otherGoodsId , LifecycleTransformer<ClickUrlBean> transformer){
        mRepository.getGoodsClickUrl(source, otherGoodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        if (mView != null){
                            mView.onBuyLinkSuccess(bean);
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

}
