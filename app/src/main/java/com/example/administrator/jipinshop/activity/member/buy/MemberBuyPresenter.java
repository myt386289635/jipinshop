package com.example.administrator.jipinshop.activity.member.buy;

import android.content.Context;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.ActivityMemberBuyBinding;
import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/12/8
 * @Describe
 */
public class MemberBuyPresenter {

    private Repository mRepository;

    @Inject
    public MemberBuyPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initCheckBox(Context context ,ActivityMemberBuyBinding binding){
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
            }else {
                binding.yearOtherContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
                binding.yearOtherTag.setBackgroundResource(R.drawable.bg_f1f1f1);
                binding.yearOtherTag.setTextColor(context.getResources().getColor(R.color.color_9D9D9D));
            }
        });
    }
}
