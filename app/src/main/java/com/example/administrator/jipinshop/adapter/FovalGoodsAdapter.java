package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TBSreachResultBean;
import com.example.administrator.jipinshop.databinding.ItemSreachOneBinding;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public class FovalGoodsAdapter extends RecyclerView.Adapter<FovalGoodsAdapter.ViewHolder> {

    private List<TBSreachResultBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public FovalGoodsAdapter(List<TBSreachResultBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSreachOneBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sreach_one,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding1);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener(v ->{
            if (mOnItem != null){
                mOnItem.onItem(position);
            }
        });
        viewHolder.binding.setDate(mList.get(position));
        viewHolder.binding.executePendingBindings();
        viewHolder.binding.itemLineContainer.setVisibility(View.VISIBLE);
        viewHolder.binding.itemGridContainer.setVisibility(View.GONE);
        viewHolder.binding.itemVolume.setVisibility(View.GONE);
        viewHolder.binding.itemShare.setVisibility(View.GONE);
        viewHolder.binding.detailOtherPrice.setTv(true);
        viewHolder.binding.detailOtherPrice.setColor(R.color.color_9D9D9D);
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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemSreachOneBinding binding;

        public ViewHolder(ItemSreachOneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}
