package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ItemShopMemberBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/8/26
 * @Describe
 */
public class MemberShopAdapter extends RecyclerView.Adapter<MemberShopAdapter.ViewHolder> {

    private List<MemberNewBean.DataBean.OrderLevelDataBean.OrderListBean> mList;
    private Context mContext;

    public MemberShopAdapter(List<MemberNewBean.DataBean.OrderLevelDataBean.OrderListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        ItemShopMemberBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_shop_member, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setBean(mList.get(position));
        viewHolder.mBinding.executePendingBindings();
        if (TextUtils.isEmpty(mList.get(position).getTag())){
            viewHolder.mBinding.itemTag.setVisibility(View.GONE);
        }else {
            viewHolder.mBinding.itemTag.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemShopMemberBinding mBinding;

        public ViewHolder(@NonNull ItemShopMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
