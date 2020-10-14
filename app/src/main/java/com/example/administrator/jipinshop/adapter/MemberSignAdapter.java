package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ItemSignMemberBinding;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/9
 * @Describe 会员极币任务
 */
public class MemberSignAdapter extends RecyclerView.Adapter<MemberSignAdapter.ViewHolder> {

    private List<MemberNewBean.DataBean.LevelDetail6Bean.ListBeanXX> mList;
    private Context mContext;

    public MemberSignAdapter(List<MemberNewBean.DataBean.LevelDetail6Bean.ListBeanXX> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSignMemberBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sign_member,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mBinding.setBean(mList.get(i));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.itemView.setOnClickListener(v -> {
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                return;
            }
            mContext.startActivity(new Intent(mContext, SignActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemSignMemberBinding mBinding;

        public ViewHolder(@NonNull ItemSignMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
