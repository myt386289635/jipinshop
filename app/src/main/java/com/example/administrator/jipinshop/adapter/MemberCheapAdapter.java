package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ItemCheapMemberBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/8
 * @Describe 会员页购物补贴
 */
public class MemberCheapAdapter extends RecyclerView.Adapter<MemberCheapAdapter.ViewHolder> {

    private List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> mList;
    private Context mContext;
    private int number = 0;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public MemberCheapAdapter(List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemCheapMemberBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_cheap_member,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        int position = number  + pos;
        viewHolder.mBinding.setBean(mList.get(position));
        viewHolder.mBinding.executePendingBindings();
        if (mList.get(position).getStatus() == -1){
            //已失效
            viewHolder.mBinding.itemButton.setText("已\n失\n效");
            viewHolder.mBinding.itemButton.setBackgroundResource(R.drawable.bg_cheap1);
            viewHolder.mBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_774A12));
        }else if (mList.get(position).getStatus() == 0){
            //可领取
            viewHolder.mBinding.itemButton.setText("可\n领\n取");
            viewHolder.mBinding.itemButton.setBackgroundResource(R.drawable.bg_cheap3);
            viewHolder.mBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_774A12));
        }else if (mList.get(position).getStatus() == 1){
            //已领取
            viewHolder.mBinding.itemButton.setText("已\n领\n取");
            viewHolder.mBinding.itemButton.setBackgroundResource(R.drawable.bg_cheap2);
            viewHolder.mBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_774A12));
        }else {
            //待生效
            viewHolder.mBinding.itemButton.setText("待\n生\n效");
            viewHolder.mBinding.itemButton.setBackgroundResource(R.drawable.bg_cheap4);
            viewHolder.mBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
        }
        viewHolder.itemView.setOnClickListener(v -> {
            mOnItem.onItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemCheapMemberBinding mBinding;

        public ViewHolder(@NonNull ItemCheapMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void onItem(int position);
    }
}
