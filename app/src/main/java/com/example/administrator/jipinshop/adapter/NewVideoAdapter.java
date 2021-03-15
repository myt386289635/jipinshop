package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.databinding.ItemRechargeBinding;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/3/10
 * @Describe
 */
public class NewVideoAdapter extends RecyclerView.Adapter {

    private static final int head = 1;
    private static final int content = 2;
    private List<EvaluationTabBean.DataBean.AdListBean> mList;
    private Context mContext;

    public NewVideoAdapter(List<EvaluationTabBean.DataBean.AdListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return head;
        }else {
            return content;
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == content) {
                        return 1;
                    }else {
                        return gridLayoutManager.getSpanCount();
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        switch (i){
            case content:
                ItemRechargeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_recharge,viewGroup,false);
                holder = new ContentViewHolder(binding);
                break;
            case head:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_head,viewGroup,false);
                holder = new HeadViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        int type = getItemViewType(i);
        switch (type){
            case content:
                ContentViewHolder viewHolder = (ContentViewHolder) holder;
                int position = i - 1;
                GlideApp.loderImage(mContext,mList.get(position).getImg(),viewHolder.mBinding.itemImage,0,0);
                viewHolder.mBinding.itemImage.setOnClickListener(v -> {
                    ShopJumpUtil.openBanner(mContext,mList.get(position).getType() + "",
                            mList.get(position).getObjectId(),mList.get(position).getName(),mList.get(position).getSource()
                            ,mList.get(position).getRemark() );
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private ItemRechargeBinding mBinding;

        public ContentViewHolder(@NonNull ItemRechargeBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
