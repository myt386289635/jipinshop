package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.V2FreeListBean;
import com.example.administrator.jipinshop.databinding.ItemFreenewOneBinding;
import com.example.administrator.jipinshop.databinding.ItemFreenewTwoBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/8
 * @Describe
 */
public class FreeNewAdapter extends RecyclerView.Adapter {

    private static final int HEAD = 1;
    private static final int CONTENT = 2;
    private List<V2FreeListBean.DataBean> mList;
    private Context mContext;
    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public FreeNewAdapter(List<V2FreeListBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        } else {
            return CONTENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        RecyclerView.ViewHolder holder = null;
        switch (type) {
            case HEAD:
                ItemFreenewOneBinding oneBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_freenew_one, viewGroup, false);
                holder = new OneViewHolder(oneBinding);
                break;
            case CONTENT:
                ItemFreenewTwoBinding twoBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_freenew_two, viewGroup, false);
                holder = new TwoViewHolder(twoBinding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        int type = getItemViewType(pos);
        switch (type) {
            case HEAD:
                OneViewHolder oneViewHolder = (OneViewHolder) holder;
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.free_scale);
                oneViewHolder.mBinding.freeInvitation.startAnimation(animation);
                animation.start();

                oneViewHolder.mBinding.freeInvitation.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onInvation();
                });
                oneViewHolder.mBinding.freeRule.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onRule();
                });
                oneViewHolder.setIsRecyclable(false);//该Item不进行复用，避免动画消失问题
                break;
            case CONTENT:
                TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
                int position = pos - 1;
                twoViewHolder.itemView.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onItem(position);
                });
                twoViewHolder.mBinding.itemBuyContainer.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onBuy(position);
                });
                twoViewHolder.mBinding.itemPriceOld.setTv(true);
                twoViewHolder.mBinding.itemPriceOld.setColor(R.color.color_9D9D9D);
                twoViewHolder.mBinding.setDate(mList.get(position));
                twoViewHolder.mBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    class OneViewHolder extends RecyclerView.ViewHolder {

        ItemFreenewOneBinding mBinding;

        public OneViewHolder(@NonNull ItemFreenewOneBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder {

        ItemFreenewTwoBinding mBinding;

        public TwoViewHolder(@NonNull ItemFreenewTwoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public interface OnClickItem {
        void onItem(int position);

        void onBuy(int position);

        void onInvation();

        void onRule();
    }
}
