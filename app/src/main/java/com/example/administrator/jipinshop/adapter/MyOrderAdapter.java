package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.databinding.ItemOrderBinding;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/11
 * @Describe
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>{

    private Context mContext;
    private List<MyOrderBean.DataBean> mList;
    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public MyOrderAdapter(Context context, List<MyOrderBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         ItemOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_order,viewGroup,false);
         ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getBinding().setDate(mList.get(position));
        GlideApp.loderRoundImage(mContext,mList.get(position).getImg(),viewHolder.getBinding().itemImage,R.color.transparent,R.color.transparent);
        if(mList.get(position).getStatus() == 1){
            viewHolder.getBinding().itemState.setText("待发货");
            viewHolder.getBinding().itemTime.setVisibility(View.INVISIBLE);
        }else if(mList.get(position).getStatus() == 2){
            viewHolder.getBinding().itemState.setText("待收货");
            viewHolder.getBinding().itemTime.setVisibility(View.VISIBLE);
        }else {
            viewHolder.getBinding().itemState.setText("已完成");
            viewHolder.getBinding().itemTime.setVisibility(View.VISIBLE);
        }
        viewHolder.getBinding().itemSure.setOnClickListener(v -> {
            if(mOnClickItem != null){
                mOnClickItem.onClickItem(position);
            }
        });
        // 立刻刷新界面
        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemOrderBinding mBinding;

        public ViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public ItemOrderBinding getBinding() {
            return mBinding;
        }
    }

    public interface OnClickItem{
        void onClickItem(int position);
    }
}
