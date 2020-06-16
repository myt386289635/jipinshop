package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.WalletHistoryBean;
import com.example.administrator.jipinshop.databinding.ItemWalletHistroyBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe
 */
public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {

    private List<WalletHistoryBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public WalletHistoryAdapter(List<WalletHistoryBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        ItemWalletHistroyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_wallet_histroy,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.itemDetail.setOnClickListener(v -> {
            mOnItem.onItemClick(position);
        });
        holder.mBinding.setDate(mList.get(position));
        holder.mBinding.executePendingBindings();
        if (mList.get(position).getDay() == 0){
            holder.mBinding.itemTime.setText(mList.get(position).getYear() + "年" + mList.get(position).getMonth() + "月");
        }else {
            holder.mBinding.itemTime.setText(mList.get(position).getYear() + "年" + mList.get(position).getMonth() + "月" + mList.get(position).getDay() + "日");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        ItemWalletHistroyBinding mBinding;

        public ViewHolder(@NonNull ItemWalletHistroyBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void onItemClick(int position);
    }
}
