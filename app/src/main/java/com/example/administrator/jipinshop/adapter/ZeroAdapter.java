package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.databinding.ItemNewFree1Binding;
import com.example.administrator.jipinshop.databinding.ItemNewFree2Binding;
import com.example.administrator.jipinshop.databinding.ItemZeroBinding;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe
 */
public class ZeroAdapter extends RecyclerView.Adapter {

    private static final int HEAD1 = 1;
    private static final int HEAD2 = 2;
    private static final int CONTENT1 = 3;

    private Context mContext;
    private List<NewFreeBean.DataBean> mList;
    private OnClickItem mOnClickItem;
    private String title = "";
    private NewFreeBean.Ad1Bean mAd1Bean;
    private NewFreeBean.Ad2Bean mAd2Bean;

    public void setAd1Bean(NewFreeBean.Ad1Bean ad1Bean) {
        mAd1Bean = ad1Bean;
    }

    public void setAd2Bean(NewFreeBean.Ad2Bean ad2Bean) {
        mAd2Bean = ad2Bean;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZeroAdapter(Context context, List<NewFreeBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
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
                    }else {
                        return 1;
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEAD1:
                ItemNewFree1Binding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_new_free1,viewGroup,false);
                holder = new HeadViewHolder(binding);
                break;
            case HEAD2:
                ItemNewFree2Binding binding2 = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_new_free2,viewGroup,false);
                holder = new Head2ViewHolder(binding2);
                break;
            case CONTENT1:
                ItemZeroBinding binding3 = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_zero,viewGroup,false);
                holder = new ContentViewHolder(binding3);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEAD1:
                HeadViewHolder holder1 = (HeadViewHolder) holder;
                holder1.binding.itemCopy.setOnClickListener(v -> mOnClickItem.onCopy());
                holder1.binding.itemCountDown.setVisibility(View.GONE);
                holder1.binding.itemEndText.setVisibility(View.VISIBLE);
                holder1.binding.itemEndText.setText(title);
                RelativeLayout.LayoutParams  layoutParams1 = (RelativeLayout.LayoutParams) holder1.binding.itemEndText.getLayoutParams();
                layoutParams1.topMargin = (int) mContext.getResources().getDimension(R.dimen.y239);
                holder1.binding.itemEndText.setLayoutParams(layoutParams1);
                holder1.binding.itemBg.setImageResource(R.mipmap.zero_bg);
                break;
            case HEAD2:
                Head2ViewHolder holder2 = (Head2ViewHolder) holder;
                holder2.binding.itemBottom.setVisibility(View.GONE);
                holder2.binding.itemCopy.setOnClickListener(v -> mOnClickItem.onCopy());
                holder2.binding.itemOne.setOnClickListener(v -> mOnClickItem.onLeft(mAd1Bean));
                holder2.binding.itemTwo.setOnClickListener(v -> mOnClickItem.onRight(mAd2Bean));
                GlideApp.loderImage(mContext,mAd1Bean.getImg(),holder2.binding.itemOne,0,0);
                GlideApp.loderImage(mContext,mAd2Bean.getImg(),holder2.binding.itemTwo,0,0);
                break;
            case CONTENT1:
                ContentViewHolder holder3 = (ContentViewHolder) holder;
                int pos = position - 1;
                holder3.binding.setDate(mList.get(pos));
                holder3.binding.executePendingBindings();
                if (mList.get(pos).getIsBuy().equals("1")){
                    holder3.binding.itemTag.setImageResource(R.mipmap.new_purchased);
                }else {
                    holder3.binding.itemTag.setImageResource(R.mipmap.zero_bg3);
                }
                holder3.binding.itemImage.post(() -> {
                    ViewGroup.LayoutParams layoutParams = holder3.binding.itemImage.getLayoutParams();
                    layoutParams.height = holder3.binding.itemImage.getWidth();
                    holder3.binding.itemImage.setLayoutParams(layoutParams);
                });
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder3.binding.itemContainer.getLayoutParams();
                if (pos % 2 != 0){//右
                    layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x5);
                    layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x30);
                }else {//左
                    layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x30);
                    layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x5);
                }
                holder3.binding.itemContainer.setLayoutParams(layoutParams);
                holder3.binding.itemContainer.setOnClickListener(v -> {
                    mOnClickItem.onBuy(pos);
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEAD1;
        }else if (position == mList.size() + 1){
            return HEAD2;
        }else {
            return CONTENT1;
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() == 0){
            return 0;
        }else {
            return  mList.size() + 2;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private ItemNewFree1Binding binding;

        public HeadViewHolder(@NonNull ItemNewFree1Binding binding ) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class Head2ViewHolder extends RecyclerView.ViewHolder{

        private ItemNewFree2Binding binding;

        public Head2ViewHolder(@NonNull ItemNewFree2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private ItemZeroBinding binding;

        public ContentViewHolder(@NonNull ItemZeroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClickItem {
        void onBuy(int position);
        void onCopy();
        void onLeft(NewFreeBean.Ad1Bean bean);
        void onRight(NewFreeBean.Ad2Bean bean);
    }
}
