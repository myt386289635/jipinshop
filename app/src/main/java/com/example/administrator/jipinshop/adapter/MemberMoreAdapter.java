package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ItemMoreMemberBinding;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/8/26
 * @Describe 会员权益
 */
public class MemberMoreAdapter extends RecyclerView.Adapter<MemberMoreAdapter.ViewHolder> {

    private List<MemberNewBean.DataBean.VipBoxListBean> mList;
    private Context mContext;

    public MemberMoreAdapter(List<MemberNewBean.DataBean.VipBoxListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        ItemMoreMemberBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_more_member, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.itemName.setText(mList.get(position).getTitle());
        GlideApp.loderImage(mContext,mList.get(position).getIconUrl(),viewHolder.mBinding.itemImage,0,0);
        int zWidth;
        viewHolder.mBinding.itemName.setTextColor(mContext.getResources().getColor(R.color.color_D5A460));
        zWidth = DistanceHelper.getAndroiodScreenwidthPixels(mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.mBinding.itemContainer.getLayoutParams();
        layoutParams.width = zWidth / 4;
        viewHolder.mBinding.itemContainer.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemMoreMemberBinding mBinding;

        public ViewHolder(@NonNull ItemMoreMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
