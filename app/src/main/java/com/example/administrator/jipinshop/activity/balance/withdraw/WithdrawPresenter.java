package com.example.administrator.jipinshop.activity.balance.withdraw;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.jipinshop.databinding.ActivityWithdrawBinding;
import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe
 */
public class WithdrawPresenter {

    private Repository mRepository;

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
}
