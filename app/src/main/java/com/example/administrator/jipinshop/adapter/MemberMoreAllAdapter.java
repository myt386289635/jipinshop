package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ItemMoreallMemberBinding;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/10/20
 * @Describe 会员更多权益
 */
public class MemberMoreAllAdapter extends RecyclerView.Adapter<MemberMoreAllAdapter.ViewHolder> {

    private List<MemberNewBean.DataBean.VipBoxListBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public MemberMoreAllAdapter(List<MemberNewBean.DataBean.VipBoxListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMoreallMemberBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_moreall_member, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mBinding.itemName.setText(mList.get(i).getTitle());
        GlideApp.loderImage(mContext,mList.get(i).getIconUrl(),viewHolder.mBinding.itemImage,0,0);
        viewHolder.mBinding.itemDesc.setText(mList.get(i).getSubTitle());
        viewHolder.mBinding.itemContainer.setOnClickListener(v -> {
            if (mOnItem != null) {
                mOnItem.onItemSlide(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemMoreallMemberBinding mBinding;

        public ViewHolder(@NonNull ItemMoreallMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

   public interface OnItem{
        void onItemSlide(int position);
    }
}
