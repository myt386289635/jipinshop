package com.example.administrator.jipinshop.fragment.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.WxPayBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/8/26
 * @Describe
 */
public class MemberPresenter {

    private Repository mRepository;
    private MemberView mView;

    public void setView(MemberView view) {
        mView = view;
    }

    @Inject
    public MemberPresenter(Repository repository) {
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

    public void adFlipper(Context context ,ViewFlipper viewFlipper , List<MemberNewBean.DataBean.MessageListBean> adList){
        viewFlipper.removeAllViews();
        for (int i = 0; i < adList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_flipper,null);
            ImageView item_image = view.findViewById(R.id.item_image);
            TextView item_name = view.findViewById(R.id.item_name);
            item_name.setTextColor(context.getResources().getColor(R.color.color_774A12));
            item_name.setText(adList.get(i).getContent());
            GlideApp.loderCircleImage(context,adList.get(i).getAvatar(),item_image,0,0);
            viewFlipper.addView(view);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
    }

    //获取会员信息
    public void levelIndex(LifecycleTransformer<MemberNewBean> transformer){
        mRepository.levelIndex()
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

    //获取微信支付信息
    public void wxpay(String type , LifecycleTransformer<WxPayBean> transformer){
        mRepository.wxpay(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onWxPay(bean);
                    }else {
                        mView.onCommenFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onCommenFile(throwable.getMessage());
                });
    }

    //获取支付宝支付信息
    public void alipay(String type , LifecycleTransformer<ImageBean> transformer){
        mRepository.alipay(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onAlipay(bean);
                    }else {
                        mView.onCommenFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onCommenFile(throwable.getMessage());
                });
    }
}
