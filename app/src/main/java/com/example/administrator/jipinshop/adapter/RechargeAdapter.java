package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.databinding.ItemRechargeBinding;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/1/19
 * @Describe
 */
public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.ViewHolder> {

    private List<EvaluationTabBean.DataBean.AdListBean> mList;
    private Context mContext;

    public RechargeAdapter(List<EvaluationTabBean.DataBean.AdListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRechargeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_recharge,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.mBinding.itemImage.getLayoutParams();
        if (position == 0 || position == 1){
            layoutParams.topMargin = (int) mContext.getResources().getDimension(R.dimen.y261);
        }else {
            layoutParams.topMargin = 0;
        }
        viewHolder.mBinding.itemImage.setLayoutParams(layoutParams);
        GlideApp.loderImage(mContext,mList.get(position).getImg(),viewHolder.mBinding.itemImage,0,0);
        viewHolder.mBinding.itemImage.setOnClickListener(v -> {
            ShopJumpUtil.openBanner(mContext,mList.get(position).getType() + "",
                    mList.get(position).getObjectId(),mList.get(position).getName(),mList.get(position).getSource()
                    ,mList.get(position).getRemark() );
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemRechargeBinding mBinding;

        public ViewHolder(@NonNull ItemRechargeBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
