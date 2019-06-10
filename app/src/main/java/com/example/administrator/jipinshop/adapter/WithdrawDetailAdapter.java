package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.WithdrawDetailBean;
import com.example.administrator.jipinshop.databinding.ItemWithdrawContentBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/4
 * @Describe 提现明细
 */
public class WithdrawDetailAdapter extends RecyclerView.Adapter<WithdrawDetailAdapter.ContentViewHolder>{

    private List<WithdrawDetailBean.DataBean> mList;
    private Context mContext;

    public WithdrawDetailAdapter(List<WithdrawDetailBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ItemWithdrawContentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_withdraw_content,viewGroup,false);
        ContentViewHolder holder = new ContentViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.binding.itemContainer.getLayoutParams();
        if (position == 0){
            params.topMargin = (int) mContext.getResources().getDimension(R.dimen.y30);
            params.bottomMargin = 0;
            if (mList.size() == 1){
                holder.binding.itemContainer.setBackgroundResource(R.drawable.bg_balance);
            }else {
                holder.binding.itemContainer.setBackgroundResource(R.drawable.bg_balance_one);
            }
        }else if (position == mList.size() - 1){
            params.topMargin = 0;
            params.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.y30);
            holder.binding.itemContainer.setBackgroundResource(R.drawable.bg_balance_last);
        }else {
            params.topMargin = 0;
            params.bottomMargin = 0;
            holder.binding.itemContainer.setBackgroundResource(R.drawable.bg_balance_other);
        }
        holder.binding.itemContainer.setLayoutParams(params);
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private ItemWithdrawContentBinding binding;

        public ContentViewHolder(ItemWithdrawContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
