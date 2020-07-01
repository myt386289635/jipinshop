package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.PrizeLogBean;
import com.example.administrator.jipinshop.databinding.ItemLuckBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/7/1
 * @Describe 大转盘 —— 抽奖记录
 */
public class DialogLuckAdapter extends RecyclerView.Adapter<DialogLuckAdapter.ViewHolder> {

    private List<PrizeLogBean.DataBean> mList;
    private Context mContext;

    public DialogLuckAdapter(List<PrizeLogBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemLuckBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_luck,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.itemTitle.setText(mList.get(position).getPrizeName());
        viewHolder.mBinding.itemTime.setText(mList.get(position).getCreateTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemLuckBinding mBinding;

        public ViewHolder(@NonNull ItemLuckBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
