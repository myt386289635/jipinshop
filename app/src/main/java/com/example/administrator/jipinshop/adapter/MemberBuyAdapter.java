package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberBuyBean;
import com.example.administrator.jipinshop.databinding.ItemBuyMemberBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/4/15
 * @Describe
 */
public class MemberBuyAdapter extends RecyclerView.Adapter<MemberBuyAdapter.ViewHolder> {

    private List<MemberBuyBean.DataBean> mList;
    private Context mContext;
    private int set = 0;//选中的是哪个位置  默认选中的是0
    private OnItem mOnItem;

    public MemberBuyAdapter(List<MemberBuyBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemBuyMemberBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_buy_member,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.itemTitle.setText(mList.get(position).getLevelname());
        viewHolder.mBinding.itemPrice.setText(mList.get(position).getPrice());
        if (mList.get(position).getLevel() == 3){
            //周卡
            viewHolder.mBinding.itemTag.setText("极币");
            viewHolder.mBinding.itemOtherPrice.setText("原价:" + mList.get(position).getPriceBefore() + "极币");
        }else {
            viewHolder.mBinding.itemTag.setText("￥");
            viewHolder.mBinding.itemOtherPrice.setText("原价:￥" + mList.get(position).getPriceBefore());
        }
        viewHolder.mBinding.itemOtherPrice.setTv(true);
        if (set == position){
            //选中
            viewHolder.mBinding.itemCheckBox.setChecked(true);
            viewHolder.mBinding.itemContainer.setBackgroundResource(R.drawable.bg_member_buy);
            viewHolder.mBinding.itemOtherPrice.setTextColor(mContext.getResources().getColor(R.color.color_996A31));
            viewHolder.mBinding.itemOtherPrice.setColor(R.color.color_996A31);
        }else {
            //未选中
            viewHolder.mBinding.itemCheckBox.setChecked(false);
            viewHolder.mBinding.itemContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
            viewHolder.mBinding.itemOtherPrice.setTextColor(mContext.getResources().getColor(R.color.color_9D9D9D));
            viewHolder.mBinding.itemOtherPrice.setColor(R.color.color_9D9D9D);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            mOnItem.onItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemBuyMemberBinding mBinding;

        public ViewHolder(@NonNull ItemBuyMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

   public interface OnItem{
        void  onItem(int position);
    }
}
