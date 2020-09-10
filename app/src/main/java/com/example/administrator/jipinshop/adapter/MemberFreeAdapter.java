package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ItemFreeMemberBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/8
 * @Describe 每月0元购
 */
public class MemberFreeAdapter extends RecyclerView.Adapter<MemberFreeAdapter.ViewHolder> {

    private List<MemberNewBean.DataBean.LevelDetail3Bean.ListBean> mList;
    private Context mContext;

    public MemberFreeAdapter(List<MemberNewBean.DataBean.LevelDetail3Bean.ListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemFreeMemberBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_free_member,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setBean(mList.get(position));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.mBinding.itemOtherPrice.setTv(true);
        viewHolder.mBinding.itemOtherPrice.setColor(R.color.color_9D9D9D);
        viewHolder.itemView.setOnClickListener(v -> {
            // todo 每月0元购
            ToastUtil.show("每月0元购");
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private ItemFreeMemberBinding mBinding;

        public ViewHolder(@NonNull ItemFreeMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
