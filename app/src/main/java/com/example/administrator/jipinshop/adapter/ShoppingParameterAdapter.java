package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.databinding.ItemQualityBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/26
 * @Describe 产品参数
 */
public class ShoppingParameterAdapter extends RecyclerView.Adapter<ShoppingParameterAdapter.ViewHolder>{

    private List<TBShoppingDetailBean.DataBean.ParametersListBean> mParameterList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public ShoppingParameterAdapter(List<TBShoppingDetailBean.DataBean.ParametersListBean> qualityList, Context context) {
        mParameterList = qualityList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemQualityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_quality,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.binding.itemName.setText(mParameterList.get(position).getName());
        viewHolder.binding.itemContent.setText(mParameterList.get(position).getValue());
        if (position == 0){
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.binding.itemContainer.getLayoutParams();
            layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x28);
            layoutParams.rightMargin = 0;
            viewHolder.binding.itemContainer.setLayoutParams(layoutParams);
            viewHolder.binding.itemContainer.setBackgroundResource(R.drawable.bg_d8d8d8_3);
        }else {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.binding.itemContainer.getLayoutParams();
            layoutParams.leftMargin = 0;
            if (position == mParameterList.size() - 1){
                layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x28);
            }else {
                layoutParams.rightMargin = 0;
            }
            viewHolder.binding.itemContainer.setLayoutParams(layoutParams);
            viewHolder.binding.itemContainer.setBackgroundResource(R.drawable.bg_d8d8d8_2);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnItem != null)
                mOnItem.onItemParameter();
        });
    }

    @Override
    public int getItemCount() {
        return mParameterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ItemQualityBinding binding;

        public ViewHolder(@NonNull ItemQualityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItem{
        void onItemParameter();
    }
}
