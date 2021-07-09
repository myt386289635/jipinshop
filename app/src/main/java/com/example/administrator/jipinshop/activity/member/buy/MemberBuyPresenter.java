package com.example.administrator.jipinshop.activity.member.buy;

import android.content.Context;

import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberBuyBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.WxPayBean;
import com.example.administrator.jipinshop.databinding.ActivityMemberBuyBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/12/8
 * @Describe
 */
public class MemberBuyPresenter {

    private Repository mRepository;
    private MemberBuyView mView;

    public void setView(MemberBuyView view) {
        mView = view;
    }

    @Inject
    public MemberBuyPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initCheckBox(Context context , ActivityMemberBuyBinding binding){
        //支付逻辑
        binding.buyAlipay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.buyWxpay.setChecked(!isChecked);
        });
        binding.buyWxpay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.buyAlipay.setChecked(!isChecked);
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

    //获取购买订单信息
    public void listVipList(LifecycleTransformer<MemberBuyBean> transformer){
        mRepository.listVipList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        mView.onCommenFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onCommenFile(throwable.getMessage());
                });
    }

    //极币支付
    public void pointPay(int type , LifecycleTransformer<SuccessBean> transformer){
        mRepository.pointPay(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onPoint();
                    }else {
                        mView.onCommenFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onCommenFile(throwable.getMessage());
                });
    }
}
