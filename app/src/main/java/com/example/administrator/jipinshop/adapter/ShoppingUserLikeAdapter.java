package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.databinding.ItemUserlikeBinding;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/28
 * @Describe
 */
public class ShoppingUserLikeAdapter extends RecyclerView.Adapter {

    private static final int foot = 1;
    private static final int content = 2;

    private List<SimilerGoodsBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public ShoppingUserLikeAdapter(List<SimilerGoodsBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()){
            return foot;
        }else {
            return content;
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == content) {
                        return 1;
                    }else {
                        return gridLayoutManager.getSpanCount();
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       RecyclerView.ViewHolder holder = null;
        switch (i){
            case content:
                ItemUserlikeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_userlike,viewGroup,false);
                holder = new ViewHolder(binding);
                break;
            case foot:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot,viewGroup,false);
                holder = new FootViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case content:
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.binding.setDate(mList.get(position));
                viewHolder.binding.executePendingBindings();
                viewHolder.binding.detailOtherPrice.setTv(true);
                viewHolder.binding.detailOtherPrice.setColor(R.color.color_9D9D9D);
                viewHolder.binding.itemImage.post(() -> {
                    ViewGroup.LayoutParams layoutParams = viewHolder.binding.itemImage.getLayoutParams();
                    layoutParams.height = viewHolder.binding.itemImage.getWidth();
                    viewHolder.binding.itemImage.setLayoutParams(layoutParams);
                });
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.binding.itemContainer.getLayoutParams();
                if (position % 2 != 0){
                    layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x10);
                    layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x20);
                }else {
                    layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x20);
                    layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x10);
                }
                viewHolder.binding.itemContainer.setLayoutParams(layoutParams);
                double coupon = new BigDecimal(mList.get(position).getCouponPrice()).doubleValue();
                if (coupon == 0){//没有优惠券
                    viewHolder.binding.detailCoupon.setVisibility(View.GONE);
                }else {
                    viewHolder.binding.detailCoupon.setVisibility(View.VISIBLE);
                }
                double free = new BigDecimal(mList.get(position).getFee()).doubleValue();
                if (free == 0){//没有补贴
                    viewHolder.binding.detailFee.setVisibility(View.GONE);
                }else {
                    viewHolder.binding.detailFee.setVisibility(View.VISIBLE);
                }
                if (coupon == 0 && free == 0){
                    viewHolder.binding.detailOtherPrice.setVisibility(View.GONE);
                }else {
                    viewHolder.binding.detailOtherPrice.setVisibility(View.VISIBLE);
                }
                viewHolder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, TBShoppingDetailActivity.class)
                            .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                            .putExtra("source",mList.get(position).getSource())
                    );
                });
                viewHolder.binding.itemShare.setOnClickListener(v -> {
                    if (mOnItem != null){
                        mOnItem.onItemShare(position);
                    }
                });
                break;
            case foot:
                FootViewHolder footViewHolder = (FootViewHolder) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemUserlikeBinding binding;

        public ViewHolder(@NonNull ItemUserlikeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

   public interface OnItem{
        void onItemShare(int position);
    }
}
