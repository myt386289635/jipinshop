package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TrialCommonBean;
import com.example.administrator.jipinshop.databinding.ItemTrialBinding;


import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/4/2
 * @Describe
 */
public class TrialCommonAdapter extends RecyclerView.Adapter<TrialCommonAdapter.ViewHolder>{

    private List<TrialCommonBean.DataBean> mList;
    private Context mContext;
    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public TrialCommonAdapter(List<TrialCommonBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTrialBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_trial,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getBinding().setData(mList.get(position));

        viewHolder.getBinding().itemShare.setOnClickListener(v -> {
            if (mOnClickItem != null){
                mOnClickItem.onClickShareItem(position);
            }
        });
        viewHolder.getBinding().itemSure.setOnClickListener(v -> {
            if (mList.get(position).getStatus() == 1){
                if (mOnClickItem != null){
                    mOnClickItem.onClickSureItem(position);
                }
            }else {
                if (mOnClickItem != null){
                    mOnClickItem.onClickReport(position);
                }
            }
        });
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnClickItem != null){
                mOnClickItem.onItemClick(position);
            }
        });

        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null || mList.size() == 0 ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemTrialBinding mBinding;

        public ViewHolder(@NonNull ItemTrialBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public ItemTrialBinding getBinding() {
            return mBinding;
        }

        public void setBinding(ItemTrialBinding binding) {
            mBinding = binding;
        }
    }

    public interface OnClickItem{
        void onClickShareItem(int position);//分享拉赞
        void onClickSureItem(int position);//确认参与
        void onClickReport(int position);//撰写报告
        void onItemClick(int position);//点击进入报告详情
    }
}
