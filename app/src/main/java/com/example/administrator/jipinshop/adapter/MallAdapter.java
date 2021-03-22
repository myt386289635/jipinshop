package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.databinding.ItemMallBinding;
import com.example.administrator.jipinshop.databinding.ItemMallHeadBinding;
import com.example.administrator.jipinshop.databinding.ItemSignHead4Binding;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 极币商城
 */
public class MallAdapter extends RecyclerView.Adapter{

    private final static int HEAD1 = 1;
    private final static int HEAD2 = 3;
    private final static int CONTENT = 2;

    private Context mContext;
    private List<MallBean.DataBean> mList;
    private List<MallBean.DataBean> mHot;
    private OnItemListener OnItem;

    public void setOnItemListener(OnItemListener onItemListener) {
        OnItem = onItemListener;
    }

    public MallAdapter(Context context, List<MallBean.DataBean> list, List<MallBean.DataBean> hot) {
        mContext = context;
        mList = list;
        mHot = hot;
    }

    //为RecyclerView添加头布局
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == HEAD1 || getItemViewType(position) == HEAD2) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD1;
        } else if (position == 1){
            return HEAD2;
        } else {
            return CONTENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEAD1:
                ItemMallHeadBinding HeadView = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_mall_head, viewGroup, false);
                holder = new HeadViewHolder(HeadView);
                break;
            case HEAD2:
                ItemSignHead4Binding head4Binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sign_head4,viewGroup,false);
                holder  = new Head4ViewHolder(head4Binding);
                break;
            case CONTENT:
                ItemMallBinding contentView = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_mall,viewGroup,false);
                holder  = new ContentViewHolder(contentView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEAD1:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                headViewHolder.mBinding.itemGetCode.setOnClickListener(v -> {
                    if(OnItem != null){
                        OnItem.onHead();
                    }
                });
                headViewHolder.mBinding.itemCode.setText(
                        SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) + "");
                headViewHolder.mBinding.itemCodeDetail.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, IntegralDetailActivity.class));
                });
                break;
            case HEAD2:
                Head4ViewHolder head4ViewHolder = (Head4ViewHolder) holder;
                head4ViewHolder.list.clear();
                head4ViewHolder.list.addAll(mHot);
                head4ViewHolder.adapter.notifyDataSetChanged();
                head4ViewHolder.adapter.setOnItemListener(position1 -> {
                    if(OnItem != null){
                        OnItem.onHotDetail(position1);
                    }
                });
                break;
            case CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                int pos = position - 2;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) contentViewHolder.mBinding.itemContainer.getLayoutParams();
                RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) contentViewHolder.mBinding.itemImage.getLayoutParams();
                if( pos % 2 == 0){
                    //左边
                    layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
                    layoutParams.rightMargin = 0;
                    imageParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
                    imageParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x10);
                }else {
                    //右边
                    layoutParams.leftMargin = 0;
                    layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
                    imageParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x10);
                    imageParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
                }
                contentViewHolder.itemView.setOnClickListener(v -> {
                    if(OnItem != null){
                        OnItem.onMoreDetail(pos);
                    }
                });
                if (mList.get(pos).getType() == 1){
                    contentViewHolder.mBinding.itemTag.setVisibility(View.VISIBLE);
                }else {
                    contentViewHolder.mBinding.itemTag.setVisibility(View.GONE);
                }
                Glide.with(mContext).load(mList.get(pos).getImg()).into(contentViewHolder.mBinding.itemImage);
                contentViewHolder.mBinding.itemCode.setText(mList.get(pos).getExchangePoint() + "极币");
                contentViewHolder.mBinding.itemName.setText(mList.get(pos).getGoodsName());
                contentViewHolder.mBinding.itemPrice.setTv(true);
                contentViewHolder.mBinding.itemPrice.setColor(R.color.color_ACACAC);
                contentViewHolder.mBinding.itemPrice.setText(mList.get(pos).getOtherPrice() + "元");
                contentViewHolder.mBinding.itemSaled.setText("已售：" + mList.get(pos).getSoldTotal() + "件");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 2;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private ItemMallHeadBinding mBinding;

        public HeadViewHolder(@NonNull ItemMallHeadBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    class Head4ViewHolder extends RecyclerView.ViewHolder{

        private ItemSignHead4Binding mBinding;
        private List<MallBean.DataBean> list;
        private SignMallAdapter adapter;

        public Head4ViewHolder(@NonNull ItemSignHead4Binding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;

            list = new ArrayList<>();
            adapter = new SignMallAdapter(mContext,list);
            mBinding.itemHot.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            mBinding.itemHot.setFocusable(false);//拒绝首次进入页面时滑动
            mBinding.itemHot.setAdapter(adapter);
        }
    }

    class ContentViewHolder extends  RecyclerView.ViewHolder{

        private ItemMallBinding mBinding;

        public ContentViewHolder(ItemMallBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItemListener{
        void onMoreDetail(int position);
        void onHotDetail(int position);//热门商品跳转
        void onHead();
    }
}
