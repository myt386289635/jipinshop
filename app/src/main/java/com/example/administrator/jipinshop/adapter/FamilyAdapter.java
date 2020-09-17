package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FamilyBean;
import com.example.administrator.jipinshop.databinding.ItemFamilyBinding;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe
 */
public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {

    private List<FamilyBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public FamilyAdapter(List<FamilyBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemFamilyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_family, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (position == mList.size()) {
            viewHolder.mBinding.itemImage.setImageResource(R.mipmap.bg_family_2);
            viewHolder.mBinding.itemName.setText("邀请成员");
            viewHolder.mBinding.itemTag.setText("");
            viewHolder.mBinding.itemVip.setVisibility(View.GONE);
        } else {
            GlideApp.loderCircleImage(mContext,mList.get(position).getAvatar(),viewHolder.mBinding.itemImage,0,0);
            viewHolder.mBinding.itemName.setText(mList.get(position).getNickename());
            if (mList.get(position).getIsMe().equals("1")) {//0不是 1是
                viewHolder.mBinding.itemTag.setText("本人");
            } else if (mList.get(position).getStatus().equals("1")) {//0是待确认  1是已确认
                viewHolder.mBinding.itemTag.setText("已加入");
            } else {
                viewHolder.mBinding.itemTag.setText("待确认");
            }
            if (mList.get(position).getLevel().equals("0")){
                //普通会员
                viewHolder.mBinding.itemVip.setVisibility(View.GONE);
            }else {//VIP
                viewHolder.mBinding.itemVip.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnItem != null)
                mOnItem.onItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemFamilyBinding mBinding;

        public ViewHolder(@NonNull ItemFamilyBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem {
        void onItem(int position);
    }
}
