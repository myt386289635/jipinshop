package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.bean.TBSreachResultBean;
import com.example.administrator.jipinshop.databinding.ItemSreachOneBinding;
import com.example.administrator.jipinshop.databinding.ItemSreachTwoBinding;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/12/3
 * @Describe
 */
public class TBSreachResultAdapter extends RecyclerView.Adapter {

    private static final int CONTENT_1 = 1;
    private static final int CONTENT_2 = 2;
    private static final int HEAD = 3;

    private List<TBSreachResultBean.DataBean> mList;
    private Context mContext;
    private int layoutType = 1;//横线是1，网格是2  默认横线
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public TBSreachResultAdapter(List<TBSreachResultBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getGoodsType() == 1 || mList.get(position).getGoodsType() == 2){
            //正常布局
            return CONTENT_1;
        }else if (mList.get(position).getGoodsType() == 4){
            return HEAD;
        }else {//猜你喜欢
            return CONTENT_2;
        }
    }

    /**
     * 为RecyclerView添加头布局
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == CONTENT_2) {
                        return 1;
                    }else {
                        if (getItemViewType(position) == CONTENT_1){
                            if (layoutType == 1){
                                return gridLayoutManager.getSpanCount();
                            }else {
                                return 1;
                            }
                        }else {
                            return gridLayoutManager.getSpanCount();
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        RecyclerView.ViewHolder holder = null;
        switch (type){
            case CONTENT_1://正常布局
                ItemSreachOneBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sreach_one,viewGroup,false);
                holder = new OneViewHolder(binding1);
                break;
            case HEAD:
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_sreach_head,viewGroup,false);
                holder = new HeadViewHolder(view2);
                break;
            case CONTENT_2:
                ItemSreachTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sreach_two,viewGroup,false);
                holder = new TwoViewHolder(binding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case CONTENT_1:
                OneViewHolder oneViewHolder = (OneViewHolder) holder;
                oneViewHolder.binding.setDate(mList.get(position));
                oneViewHolder.binding.executePendingBindings();
                double coupon1 = new BigDecimal(mList.get(position).getCouponPrice()).doubleValue();
                double free1 = new BigDecimal(mList.get(position).getFee()).doubleValue();
                if (layoutType == 1){
                    oneViewHolder.binding.itemLine.setVisibility(View.VISIBLE);
                    oneViewHolder.binding.itemLineContainer.setVisibility(View.VISIBLE);
                    oneViewHolder.binding.itemGridContainer.setVisibility(View.GONE);
                    oneViewHolder.binding.detailOtherPrice.setTv(true);
                    oneViewHolder.binding.detailOtherPrice.setColor(R.color.color_9D9D9D);
                    if (coupon1 == 0) {//没有优惠券
                        oneViewHolder.binding.detailCoupon.setVisibility(View.GONE);
                    } else {
                        oneViewHolder.binding.detailCoupon.setVisibility(View.VISIBLE);
                    }
                    if (free1 == 0) {//没有补贴
                        oneViewHolder.binding.detailFee.setVisibility(View.GONE);
                    } else {
                        oneViewHolder.binding.detailFee.setVisibility(View.VISIBLE);
                    }
                    if (coupon1 == 0 && free1 == 0) {
                        oneViewHolder.binding.detailOtherPrice.setVisibility(View.GONE);
                    } else {
                        oneViewHolder.binding.detailOtherPrice.setVisibility(View.VISIBLE);
                    }
                    oneViewHolder.binding.itemShare.setOnClickListener(v -> {
                        if (mOnItem != null){
                            mOnItem.onItemShare(position);
                        }
                    });
                }else {
                    oneViewHolder.binding.itemLineContainer.setVisibility(View.GONE);
                    oneViewHolder.binding.itemGridContainer.setVisibility(View.VISIBLE);
                    if (position == 0 || position == 1) {
                        oneViewHolder.binding.itemLine.setVisibility(View.VISIBLE);
                    } else {
                        oneViewHolder.binding.itemLine.setVisibility(View.GONE);
                    }
                    oneViewHolder.binding.itemGridImage.post(() -> {
                        ViewGroup.LayoutParams layoutParams = oneViewHolder.binding.itemGridImage.getLayoutParams();
                        layoutParams.height = oneViewHolder.binding.itemGridImage.getWidth();
                        oneViewHolder.binding.itemGridImage.setLayoutParams(layoutParams);
                    });
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) oneViewHolder.binding.itemGridContainer1.getLayoutParams();
                    if (position % 2 != 0){
                        layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x10);
                        layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x20);
                    }else {
                        layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x20);
                        layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x10);
                    }
                    oneViewHolder.binding.itemGridContainer1.setLayoutParams(layoutParams);
                    oneViewHolder.binding.detailGirdOtherPrice.setTv(true);
                    oneViewHolder.binding.detailGirdOtherPrice.setColor(R.color.color_9D9D9D);
                    if (coupon1 == 0) {//没有优惠券
                        oneViewHolder.binding.detailGirdCoupon.setVisibility(View.GONE);
                    } else {
                        oneViewHolder.binding.detailGirdCoupon.setVisibility(View.VISIBLE);
                    }
                    if (free1 == 0) {//没有补贴
                        oneViewHolder.binding.detailGirdFee.setVisibility(View.GONE);
                    } else {
                        oneViewHolder.binding.detailGirdFee.setVisibility(View.VISIBLE);
                    }
                    if (coupon1 == 0 && free1 == 0) {
                        oneViewHolder.binding.detailGirdOtherPrice.setVisibility(View.GONE);
                    } else {
                        oneViewHolder.binding.detailGirdOtherPrice.setVisibility(View.VISIBLE);
                    }
                    oneViewHolder.binding.itemGridShare.setOnClickListener(v -> {
                        if (mOnItem != null){
                            mOnItem.onItemShare(position);
                        }
                    });
                }
                oneViewHolder.itemView.setOnClickListener(v -> {
                    if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        return;
                    }
                    mContext.startActivity(new Intent(mContext, TBShoppingDetailActivity.class)
                            .putExtra("otherGoodsId", mList.get(position).getOtherGoodsId())
                    );
                });
                break;
            case HEAD:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                if (position == 0){
                    headViewHolder.item_gridLine.setVisibility(View.GONE);
                    headViewHolder.item_line.setVisibility(View.GONE);
                    headViewHolder.mFrameLayout.setVisibility(View.VISIBLE);
                }else {
                    headViewHolder.mFrameLayout.setVisibility(View.GONE);
                    headViewHolder.item_line.setVisibility(View.VISIBLE);
                    if (layoutType == 1){
                        headViewHolder.item_gridLine.setVisibility(View.GONE);
                    }else {
                        headViewHolder.item_gridLine.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case CONTENT_2:
                TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
                int pos = position;
                twoViewHolder.binding.setDate(mList.get(pos));
                twoViewHolder.binding.executePendingBindings();
                twoViewHolder.binding.itemImage.post(() -> {
                    ViewGroup.LayoutParams layoutParams = twoViewHolder.binding.itemImage.getLayoutParams();
                    layoutParams.height = twoViewHolder.binding.itemImage.getWidth();
                    twoViewHolder.binding.itemImage.setLayoutParams(layoutParams);
                });
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) twoViewHolder.binding.itemContainer.getLayoutParams();
                if (pos % 2 != 0){//大多数情况都是左单数，右双数
                    layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x20);
                    layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x10);
                }else {
                    layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x10);
                    layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x20);
                }
                twoViewHolder.binding.itemContainer.setLayoutParams(layoutParams);
                twoViewHolder.binding.detailOtherPrice.setTv(true);
                twoViewHolder.binding.detailOtherPrice.setColor(R.color.color_9D9D9D);
                double coupon = new BigDecimal(mList.get(pos).getCouponPrice()).doubleValue();
                if (coupon == 0){//没有优惠券
                    twoViewHolder.binding.detailCoupon.setVisibility(View.GONE);
                }else {
                    twoViewHolder.binding.detailCoupon.setVisibility(View.VISIBLE);
                }
                double free = new BigDecimal(mList.get(pos).getFee()).doubleValue();
                if (free == 0){//没有补贴
                    twoViewHolder.binding.detailFee.setVisibility(View.GONE);
                }else {
                    twoViewHolder.binding.detailFee.setVisibility(View.VISIBLE);
                }
                if (coupon == 0 && free == 0){
                    twoViewHolder.binding.detailOtherPrice.setVisibility(View.GONE);
                }else {
                    twoViewHolder.binding.detailOtherPrice.setVisibility(View.VISIBLE);
                }
                twoViewHolder.itemView.setOnClickListener(v -> {
                    if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        return;
                    }
                    mContext.startActivity(new Intent(mContext, TBShoppingDetailActivity.class)
                            .putExtra("otherGoodsId", mList.get(pos).getOtherGoodsId())
                    );
                });
                twoViewHolder.binding.itemShare.setOnClickListener(v -> {
                    if (mOnItem != null){
                        mOnItem.onItemShare(pos);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class OneViewHolder extends RecyclerView.ViewHolder{

        ItemSreachOneBinding binding;

        public OneViewHolder(@NonNull ItemSreachOneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private FrameLayout mFrameLayout;
        private View item_gridLine,item_line;

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            mFrameLayout = itemView.findViewById(R.id.item_kong);
            item_gridLine = itemView.findViewById(R.id.item_gridLine);
            item_line = itemView.findViewById(R.id.item_line);
        }
    }

    class TwoViewHolder extends  RecyclerView.ViewHolder{

        ItemSreachTwoBinding binding;

        public TwoViewHolder(@NonNull ItemSreachTwoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItem{
        void onItemShare(int position);
    }
}
