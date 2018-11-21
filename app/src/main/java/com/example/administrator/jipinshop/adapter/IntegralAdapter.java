package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.IntegralShopBean;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.TextViewDel;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class IntegralAdapter extends RecyclerView.Adapter{

    private final int HEAD = 1;
    private final int CONTENT  = 2;

    private Context mContext;
    private List<IntegralShopBean.ListBean> mList;
    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }

    public IntegralAdapter(Context context, List<IntegralShopBean.ListBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEAD;
        }else {
            return CONTENT;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case HEAD:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_intergral_head,parent,false);
                holder  = new HeadViewHolder(view);
                break;
            case CONTENT:
                View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_intergral_content,parent,false);
                holder  = new ContentViewHolder(contentView);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int type =  getItemViewType(position);
        switch (type){
            case HEAD:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                headViewHolder.item_intergral.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint) + "");
                headViewHolder.item_detail.setOnClickListener(v -> {
                    if(mOnItemListener != null){
                        mOnItemListener.onItemIntegralDetail();
                    }
                });
                break;
            case CONTENT:

                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;

                if( (position - 1) % 2 == 0){
                    //左边
                    GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) contentViewHolder.item_container.getLayoutParams();
                    layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x28);
                    layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x14);

                }else {
                    //右边
                    GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) contentViewHolder.item_container.getLayoutParams();
                    layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x14);
                    layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x28);
                }
                contentViewHolder.item_exchange.setOnClickListener(v -> {
                    if(mOnItemListener != null){
                        mOnItemListener.onItemExchange(position);
                    }
                });

                Glide.with(mContext).load(mList.get(position - 1).getRankGoodImg()).into(contentViewHolder.item_image);
                contentViewHolder.item_code.setText(mList.get(position -1).getGoodsPoint() + "积分");
                contentViewHolder.item_price.setColor(R.color.color_ACACAC);
                contentViewHolder.item_price.setTv(true);
                contentViewHolder.item_price.setText("¥"+mList.get(position -1).getOtherPrice());
                contentViewHolder.item_name.setText(mList.get(position - 1).getGoodsName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 1 : mList.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position == 0){
                        return  gridLayoutManager.getSpanCount();
                    }else {
                        return 1;
                    }
                }
            });
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private TextView item_intergral;
        private TextView item_detail;

        public HeadViewHolder(View itemView) {
            super(itemView);
            item_detail = itemView.findViewById(R.id.item_detail);
            item_intergral = itemView.findViewById(R.id.item_intergral);
        }
    }

    class ContentViewHolder extends  RecyclerView.ViewHolder{

        private RelativeLayout item_container;
        private ImageView item_image;
        private TextView item_name;
        private TextView item_code;
        private TextViewDel item_price;
        private TextView item_exchange;

        public ContentViewHolder(View itemView) {
            super(itemView);
            item_container = itemView.findViewById(R.id.item_container);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_code = itemView.findViewById(R.id.item_code);
            item_price = itemView.findViewById(R.id.item_price);
            item_exchange = itemView.findViewById(R.id.item_exchange);
        }
    }

    public interface OnItemListener{
        void onItemExchange(int pos);
        void onItemIntegralDetail();
    }
}
