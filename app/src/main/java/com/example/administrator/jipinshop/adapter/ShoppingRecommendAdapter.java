package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.databinding.ItemRecommendDetailBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/2/2
 * @Describe
 */
public class ShoppingRecommendAdapter extends RecyclerView.Adapter<ShoppingRecommendAdapter.ViewHolder> {

    private List<SimilerGoodsBean.DataBean> mRecommendList;
    private Context mContext;

    public ShoppingRecommendAdapter(List<SimilerGoodsBean.DataBean> recommendList, Context context) {
        mRecommendList = recommendList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRecommendDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_recommend_detail,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setDate(mRecommendList.get(position));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.itemView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, TBShoppingDetailActivity.class)
                    .putExtra("otherGoodsId", mRecommendList.get(position).getOtherGoodsId())
                    .putExtra("source",mRecommendList.get(position).getSource())
            );
        });
    }

    @Override
    public int getItemCount() {
        return mRecommendList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemRecommendDetailBinding mBinding;

        public ViewHolder(@NonNull ItemRecommendDetailBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
