package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.ItemFamilyBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe
 */
public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {

    private List<String> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public FamilyAdapter(List<String> list, Context context) {
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
        } else {
            //模拟数据
            viewHolder.mBinding.itemImage.setImageResource(R.mipmap.rlogo);
            viewHolder.mBinding.itemName.setText("测试人员" + position);
            if (position == 0) {
                viewHolder.mBinding.itemTag.setText("本人");
            } else if (position == 1) {
                viewHolder.mBinding.itemTag.setText("已加入");
            } else {
                viewHolder.mBinding.itemTag.setText("待确认");
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
