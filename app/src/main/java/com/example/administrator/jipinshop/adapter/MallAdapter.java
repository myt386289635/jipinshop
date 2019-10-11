package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 极币商城
 */
public class MallAdapter extends RecyclerView.Adapter{

    private final static int HEAD = 1;
    private final static int CONTENT = 2;

    private Context mContext;
    private List<MallBean.DataBean> mList;
    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }

    public MallAdapter(Context context, List<MallBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    /**
     * 为RecyclerView添加头布局
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
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
            return HEAD;
        } else {
            return CONTENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEAD:
                View HeadView = LayoutInflater.from(mContext).inflate(R.layout.item_mall_head, viewGroup, false);
                holder = new HeadViewHolder(HeadView);
                break;
            case CONTENT:
                View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_mall,viewGroup,false);
                holder  = new ContentViewHolder(contentView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEAD:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                headViewHolder.item_getCode.setOnClickListener(v -> {
                    if(mOnItemListener != null){
                        mOnItemListener.onHead();
                    }
                });
                headViewHolder.item_code.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) + "");
                break;
            case CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                int pos = position - 1;
                if( pos % 2 == 0){
                    //左边
                    GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) contentViewHolder.item_container.getLayoutParams();
                    layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x14);
                    layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x7);

                }else {
                    //右边
                    GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) contentViewHolder.item_container.getLayoutParams();
                    layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x7);
                    layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x14);
                }

                contentViewHolder.itemView.setOnClickListener(v -> {
                    if(mOnItemListener != null){
                        mOnItemListener.onItemIntegralDetail(pos);
                    }
                });

                if (mList.get(pos).getType() == 1){
                    contentViewHolder.item_tag.setVisibility(View.VISIBLE);
                }else {
                    contentViewHolder.item_tag.setVisibility(View.GONE);
                }

                Glide.with(mContext).load(mList.get(pos).getImg()).into(contentViewHolder.item_image);
                contentViewHolder.item_code.setText(mList.get(pos).getExchangePoint() + "极币");
                contentViewHolder.item_name.setText(mList.get(pos).getGoodsName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private TextView item_code,item_getCode;

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            item_code = itemView.findViewById(R.id.item_code);
            item_getCode = itemView.findViewById(R.id.item_getCode);
        }
    }

    class ContentViewHolder extends  RecyclerView.ViewHolder{

        private RelativeLayout item_container;
        private ImageView item_image,item_tag;
        private TextView item_name;
        private TextView item_code;

        public ContentViewHolder(View itemView) {
            super(itemView);
            item_container = itemView.findViewById(R.id.item_container);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_code = itemView.findViewById(R.id.item_code);
            item_tag = itemView.findViewById(R.id.item_tag);
        }
    }

    public interface OnItemListener{
        void onItemIntegralDetail(int position);
        void onHead();
    }
}
