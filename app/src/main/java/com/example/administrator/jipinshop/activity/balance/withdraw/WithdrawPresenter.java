package com.example.administrator.jipinshop.activity.balance.withdraw;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SucBeanT;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TaobaoAccountBean;
import com.example.administrator.jipinshop.bean.WithdrawBean;
import com.example.administrator.jipinshop.databinding.ActivityWithdrawBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.bean.SHARE_MEDIA;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe
 */
public class WithdrawPresenter {

    private Repository mRepository;
    private WithdrawView mView;

    public void setView(WithdrawView view) {
        mView = view;
    }

    @Inject
    public WithdrawPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initMoneyEdit(ActivityWithdrawBinding binding){
        binding.withdrawPay.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if (source.equals(".") && dest.toString().length() == 0) {
                return "0.";
            }
            if (dest.toString().contains(".") && source.equals(".")) {
                return "";
            }
            if (dest.toString().contains(".")) {
                int index = dest.toString().indexOf(".");
                int length = dest.toString().substring(index).length();
                if (length == 3) {
                    return "";
                }
            }
            return null;
        }});
        binding.withdrawName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                binding.withdrawNameText.setVisibility(View.GONE);
            }else {
                if (TextUtils.isEmpty(binding.withdrawName.getText().toString())){
                    binding.withdrawNameText.setVisibility(View.VISIBLE);
                }else {
                    binding.withdrawNameText.setVisibility(View.GONE);
                }
            }
        });
        binding.withdrawNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                binding.withdrawNumberText.setVisibility(View.GONE);
            }else {
                if (TextUtils.isEmpty(binding.withdrawNumber.getText().toString())){
                    binding.withdrawNumberText.setVisibility(View.VISIBLE);
                }else {
                    binding.withdrawNumberText.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getWithdrawNote(LifecycleTransformer<WithdrawBean> transformer){
        mRepository.getWithdrawNote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(withdrawBean -> {
                    if (withdrawBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(withdrawBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(withdrawBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void withdraw(String realname , String account, String amount,LifecycleTransformer<SucBeanT<String>> transformer){
        mRepository.withdraw(realname, account, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (mView != null){
                            mView.onWithdrawSuccess(successBean.getData());
                        }
                    }else {
                        if (mView != null){
                            mView.onWithdrawFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onWithdrawFile(throwable.getMessage());
                    }
                });
    }

    public void taobaoAccount(LifecycleTransformer<TaobaoAccountBean> transformer){
        mRepository.taobaoAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(taobaoAccountBean -> {
                    if (taobaoAccountBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccessAccount(taobaoAccountBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onWithdrawFile(taobaoAccountBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onWithdrawFile(throwable.getMessage());
                    }
                });
    }

    //会员购买分享
    public void initShare(int type ,SHARE_MEDIA share_media,LifecycleTransformer<ShareInfoBean> transformer){
        mRepository.getShareInfo(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.initShare(share_media,bean);
                    }else {
                        mView.onWithdrawFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onWithdrawFile(throwable.getMessage());
                });
    }
}
