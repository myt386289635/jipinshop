package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.databinding.ItemQualityBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/26
 * @Describe
 */
public class ShoppingQualityAdapter extends RecyclerView.Adapter<ShoppingQualityAdapter.ViewHolder> {

    private List<TBShoppingDetailBean.DataBean.ScoreOptionsListBean> mQualityList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public ShoppingQualityAdapter(List<TBShoppingDetailBean.DataBean.ScoreOptionsListBean> qualityList, Context context) {
        mQualityList = qualityList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemQualityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_quality,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.binding.itemName.setText(mQualityList.get(position).getName());
        viewHolder.binding.itemContent.setText(mQualityList.get(position).getScore() + "分");
        if (position == 0){
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.binding.itemContainer.getLayoutParams();
            layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x28);
            layoutParams.rightMargin = 0;
            viewHolder.binding.itemContainer.setLayoutParams(layoutParams);
            viewHolder.binding.itemContainer.setBackgroundResource(R.drawable.bg_d8d8d8_3);
            viewHolder.binding.itemName.setTextColor(mContext.getResources().getColor(R.color.color_565252));
            viewHolder.binding.itemContent.setTextColor(mContext.getResources().getColor(R.color.color_E25838));
        } else {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.binding.itemContainer.getLayoutParams();
            layoutParams.leftMargin = 0;
            if (position == mQualityList.size() - 1){
                layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x28);
            }else {
                layoutParams.rightMargin = 0;
            }
            viewHolder.binding.itemContainer.setLayoutParams(layoutParams);
            viewHolder.binding.itemContainer.setBackgroundResource(R.drawable.bg_d8d8d8_2);
            viewHolder.binding.itemName.setTextColor(mContext.getResources().getColor(R.color.color_9D9D9D));
            viewHolder.binding.itemContent.setTextColor(mContext.getResources().getColor(R.color.color_565252));
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnItem != null)
                mOnItem.onItemQuality();
        });
    }

    @Override
    public int getItemCount() {
        return mQualityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ItemQualityBinding binding;

        public ViewHolder(@NonNull ItemQualityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItem{
        void onItemQuality();
    }
}
