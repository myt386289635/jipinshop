package com.example.administrator.jipinshop.activity.member.buy;

import android.content.Context;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberBuyBean;
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
        //会员卡选择逻辑
        binding.monthCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                binding.monthContainer.setBackgroundResource(R.drawable.bg_member_buy);
                binding.monthTag.setBackgroundResource(R.drawable.bg_fae1bf_1);
                binding.monthTag.setTextColor(context.getResources().getColor(R.color.color_996A31));
                mView.onInit("1");
                binding.yearCheckBox.setChecked(false);
                binding.weekCheckBox.setChecked(false);
            }else {
                binding.monthContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
                binding.monthTag.setBackgroundResource(R.drawable.bg_f1f1f1);
                binding.monthTag.setTextColor(context.getResources().getColor(R.color.color_9D9D9D));
            }
        });
        binding.yearCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                binding.yearContainer.setBackgroundResource(R.drawable.bg_member_buy);
                binding.yearTag.setBackgroundResource(R.drawable.bg_fae1bf_1);
                binding.yearTag.setTextColor(context.getResources().getColor(R.color.color_996A31));
                mView.onInit("2");
                binding.monthCheckBox.setChecked(false);
                binding.weekCheckBox.setChecked(false);
            }else {
                binding.yearContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
                binding.yearTag.setBackgroundResource(R.drawable.bg_f1f1f1);
                binding.yearTag.setTextColor(context.getResources().getColor(R.color.color_9D9D9D));
            }
        });
        binding.weekCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                binding.weekContainer.setBackgroundResource(R.drawable.bg_member_buy);
                binding.weekTag.setBackgroundResource(R.drawable.bg_fae1bf_1);
                binding.weekTag.setTextColor(context.getResources().getColor(R.color.color_996A31));
                mView.onInit("3");
                binding.monthCheckBox.setChecked(false);
                binding.yearCheckBox.setChecked(false);
            }else {
                binding.weekContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
                binding.weekTag.setBackgroundResource(R.drawable.bg_f1f1f1);
                binding.weekTag.setTextColor(context.getResources().getColor(R.color.color_9D9D9D));
            }
        });
        binding.monthOtherCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.yearOtherCheckBox.setChecked(!isChecked);
            if (isChecked){
                binding.monthOtherContainer.setBackgroundResource(R.drawable.bg_member_buy);
                binding.monthOtherTag.setBackgroundResource(R.drawable.bg_fae1bf_1);
                binding.monthOtherTag.setTextColor(context.getResources().getColor(R.color.color_996A31));
                mView.onInit("1");
            }else {
                binding.monthOtherContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
                binding.monthOtherTag.setBackgroundResource(R.drawable.bg_f1f1f1);
                binding.monthOtherTag.setTextColor(context.getResources().getColor(R.color.color_9D9D9D));
            }
        });
        binding.yearOtherCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.monthOtherCheckBox.setChecked(!isChecked);
            if (isChecked){
                binding.yearOtherContainer.setBackgroundResource(R.drawable.bg_member_buy);
                binding.yearOtherTag.setBackgroundResource(R.drawable.bg_fae1bf_1);
                binding.yearOtherTag.setTextColor(context.getResources().getColor(R.color.color_996A31));
                mView.onInit("2");
            }else {
                binding.yearOtherContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
                binding.yearOtherTag.setBackgroundResource(R.drawable.bg_f1f1f1);
                binding.yearOtherTag.setTextColor(context.getResources().getColor(R.color.color_9D9D9D));
            }
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
                    if (bean.getCode() == 0 && bean.getData().size() >= 2){
                        mView.onSuccess(bean);
                    }else if (bean.getCode() == 0){
                        mView.onCommenFile("接口错误，请联系管理员");
                    }else {
                        mView.onCommenFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onCommenFile(throwable.getMessage());
                });
    }
}
