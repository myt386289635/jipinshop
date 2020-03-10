package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.NewPeopleBean;
import com.example.administrator.jipinshop.bean.V2FreeListBean;
import com.example.administrator.jipinshop.databinding.ItemNewForeBinding;
import com.example.administrator.jipinshop.databinding.ItemNewOneBinding;
import com.example.administrator.jipinshop.databinding.ItemNewThreeBinding;
import com.example.administrator.jipinshop.databinding.ItemNewTwoBinding;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/14
 * @Describe
 */
public class NewPeopleAdapter extends RecyclerView.Adapter{

    private static final int HEAD1 = 1;
    private static final int HEAD2 = 2;
    private static final int CONTENT1 = 3;
    private static final int CONTENT2 = 4;
    private List<NewPeopleBean.DataBean.NewAllowanceGoodsListBean> mList;//零元购商品
    private List<NewPeopleBean.DataBean.AllowanceGoodsListBean> mList2;//特惠购商品
    private Context mContext;
    private OnClickItem mOnClickItem;
    private String allowance = "0";

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public NewPeopleAdapter(List<NewPeopleBean.DataBean.NewAllowanceGoodsListBean> list, List<NewPeopleBean.DataBean.AllowanceGoodsListBean> list2, Context context) {
        mList = list;
        mList2 = list2;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD1;
        } else if (position - 1 < mList.size()){
            return CONTENT1;
        } else if (position - 1 == mList.size()){
            return HEAD2;
        }else {
            return CONTENT2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        RecyclerView.ViewHolder holder = null;
        switch (type) {
            case HEAD1:
                ItemNewOneBinding oneBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_new_one, viewGroup, false);
                holder = new OneViewHolder(oneBinding);
                break;
            case CONTENT1:
                ItemNewTwoBinding twoBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_new_two, viewGroup, false);
                holder = new TwoViewHolder(twoBinding);
                break;
            case HEAD2:
                ItemNewThreeBinding threeBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_new_three, viewGroup, false);
                holder = new TreeViewHolder(threeBinding);
                break;
            case CONTENT2:
                ItemNewForeBinding foreBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_new_fore, viewGroup, false);
                holder = new ForeViewHolder(foreBinding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        int type = getItemViewType(pos);
        switch (type) {
            case HEAD1:
                OneViewHolder oneViewHolder = (OneViewHolder) holder;
                oneViewHolder.mBinding.freeRule.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onRule();
                });
                oneViewHolder.mBinding.itemCopy.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onCopy();
                });
                break;
            case CONTENT1:
                TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
                int position = pos - 1;
                twoViewHolder.itemView.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onBuy(position);
                });
                if (position == 0){
                    twoViewHolder.mBinding.itemContainer.setBackgroundResource(R.drawable.bg_balance_one);
                }else {
                    twoViewHolder.mBinding.itemContainer.setBackgroundResource(R.drawable.bg_balance_other);
                }
                twoViewHolder.mBinding.itemProgressbar.setTotalAndCurrentCount(100,mList.get(position).getCount());
                twoViewHolder.mBinding.itemPriceOld.setTv(true);
                twoViewHolder.mBinding.itemPriceOld.setColor(R.color.color_9D9D9D);
                twoViewHolder.mBinding.setData(mList.get(position));
                twoViewHolder.mBinding.executePendingBindings();
                break;
            case HEAD2:
                TreeViewHolder treeViewHolder = (TreeViewHolder) holder;
                Drawable drawable;
                if (mList.size() <= 4){
                    treeViewHolder.mBinding.itemMore.setText("展开更多");
                    drawable= mContext.getResources().getDrawable(R.mipmap.right_down);
                }else {
                    treeViewHolder.mBinding.itemMore.setText("点击收起");
                    drawable= mContext.getResources().getDrawable(R.mipmap.right_up);
                }
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, mContext.getResources().getColor(R.color.color_F44E12));
                treeViewHolder.mBinding.itemMore.setCompoundDrawables(null,null,drawable,null);
                treeViewHolder.mBinding.itemMore.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onMore();
                });
                treeViewHolder.mBinding.itemPrice.setText(allowance);
                break;
            case CONTENT2:
                ForeViewHolder foreViewHolder = (ForeViewHolder) holder;
                int zposition = pos - (mList.size() + 2);
                foreViewHolder.mBinding.itemBuy.setOnClickListener(v -> {
                    if (mOnClickItem != null)
                        mOnClickItem.onzBuy(zposition);
                });
                double coupon = new BigDecimal(mList2.get(zposition).getCouponPrice()).doubleValue();
                if (coupon == 0){
                    foreViewHolder.mBinding.itemCoupon.setVisibility(View.GONE);
                }else {
                    foreViewHolder.mBinding.itemCoupon.setVisibility(View.VISIBLE);
                }
                double allowance = new BigDecimal(mList2.get(zposition).getUseAllowancePrice()).doubleValue();
                if (allowance == 0){
                    foreViewHolder.mBinding.itemAllowance.setVisibility(View.GONE);
                }else {
                    foreViewHolder.mBinding.itemAllowance.setVisibility(View.VISIBLE);
                }
                foreViewHolder.mBinding.itemPriceOld.setColor(R.color.color_9D9D9D);
                foreViewHolder.mBinding.itemPriceOld.setTv(true);
                foreViewHolder.mBinding.setData(mList2.get(zposition));
                foreViewHolder.mBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mList2.size() == 0 || mList.size() == 0){
            return 0;
        }else {
            return  mList.size() + mList2.size() + 2;
        }
    }

    class OneViewHolder extends RecyclerView.ViewHolder {

        ItemNewOneBinding mBinding;

        public OneViewHolder(@NonNull ItemNewOneBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder {

        ItemNewTwoBinding mBinding;

        public TwoViewHolder(@NonNull ItemNewTwoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    class TreeViewHolder extends RecyclerView.ViewHolder {

        ItemNewThreeBinding mBinding;

        public TreeViewHolder(@NonNull ItemNewThreeBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    class ForeViewHolder extends RecyclerView.ViewHolder {

        ItemNewForeBinding mBinding;

        public ForeViewHolder(@NonNull ItemNewForeBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public interface OnClickItem {

        void onBuy(int position);

        void onCopy();

        void onRule();

        void onMore();

        void onzBuy(int position);
    }
}
