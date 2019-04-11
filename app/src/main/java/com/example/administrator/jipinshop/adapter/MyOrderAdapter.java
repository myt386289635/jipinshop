package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.databinding.ItemOrderBinding;

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
        viewHolder.getBinding().itemSure.setOnClickListener(v -> {
            if(mOnClickItem != null){
                mOnClickItem.onClickItem(position);
            }
        });
        viewHolder.itemView.setOnClickListener(v -> {
            if(mOnClickItem != null){
                mOnClickItem.onClickDetailItem(position);
            }
        });
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
        void onClickItem(int position);//确认收货
        void onClickDetailItem(int position);//订单详情页面
    }
}
