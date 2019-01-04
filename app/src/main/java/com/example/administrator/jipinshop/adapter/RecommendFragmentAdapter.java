package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.databinding.RecommendItemBinding;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class RecommendFragmentAdapter extends RecyclerView.Adapter {

    private final int HEAD = 1;
    private final int CONTENT = 2;
    private final int FOOT = 3;

    private List<RecommendFragmentBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;
    private String image = "";

    public void setHeadImage(String image) {
        this.image = image;
    }

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public RecommendFragmentAdapter(List<RecommendFragmentBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        switch (i) {
            case HEAD:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_recommend, viewGroup, false);
                holder = new ContentViewHolder(view1);
                break;
            case CONTENT:
                RecommendItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.recommend_item, viewGroup, false);
                holder = new HeadViewHolder(binding);
                break;
            case FOOT:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, viewGroup, false);
                holder = new FootViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int pos) {
        int type = getItemViewType(pos);
        switch (type) {
            case HEAD:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                if(TextUtils.isEmpty(image)){
                    contentViewHolder.recommend_image.setBackgroundResource(R.mipmap.remmonent_banner);
                }else {
                    GlideApp.loderImage(mContext,image,contentViewHolder.recommend_image,0,0);
                }
                break;
            case CONTENT:
                HeadViewHolder viewHolder = (HeadViewHolder) holder;

                final int position = pos - 1;
                viewHolder.getBinding().setPosition(pos + "");
                viewHolder.getBinding().setDate(mList.get(position));

                viewHolder.itemView.setOnClickListener(view -> {
                    if (mOnItem != null) {
                        mOnItem.onItem(position);
                    }
                });

                GlideApp.loderRoundImage(mContext,mList.get(position).getImg(),viewHolder.getBinding().itemImage,R.color.transparent,R.color.transparent);
                viewHolder.initTags(viewHolder.getBinding().itemFlex,position);

                if(mList.get(position).getSource() == 1){
                    viewHolder.getBinding().itemGoodsFrom.setText("京东：");
                }else  if(mList.get(position).getSource() == 2){
                    viewHolder.getBinding().itemGoodsFrom.setText("淘宝：");
                }else  if(mList.get(position).getSource() == 3){
                    viewHolder.getBinding().itemGoodsFrom.setText("天猫：");
                }
                viewHolder.getBinding().itemPriceOld.setTv(true);
                viewHolder.getBinding().itemPriceOld.setColor(R.color.color_ACACAC);

                // 立刻刷新界面
                viewHolder.getBinding().executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        }else if (position == getItemCount() - 1){
            return FOOT;
        } else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return mList == null || mList.size() == 0 ? 0 : mList.size() + 2;
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {

        private RecommendItemBinding binding;

        public HeadViewHolder(RecommendItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public RecommendItemBinding getBinding() {
            return binding;
        }

        public void setBinding(RecommendItemBinding binding) {
            this.binding = binding;
        }

        public void initTags(FlexboxLayout flexboxLayout, int pos) {
            flexboxLayout.removeAllViews();
            for (int i = 0; i < mList.get(pos).getGoodsTagsList().size(); i++) {
                View itemTypeView = LayoutInflater.from(mContext).inflate(R.layout.item_goodstag, null);
                TextView textView = itemTypeView.findViewById(R.id.item_tag);
                textView.setText(mList.get(pos).getGoodsTagsList().get(i).getName());
                flexboxLayout.addView(itemTypeView);
            }
        }
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView recommend_image;

        public ContentViewHolder(View itemView) {
            super(itemView);
            recommend_image = itemView.findViewById(R.id.recommend_image);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}
