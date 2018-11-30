package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.databinding.RecommendItemBinding;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.math.BigDecimal;

public class RecommendFragmentAdapter extends RecyclerView.Adapter {

    private final int HEAD = 1;
    private final int CONTENT = 2;

    private RecommendFragmentBean mList;
    private Context mContext;
    private OnItem mOnItem;
    private String image = "";

    public void setImage(String image) {
        this.image = image;
    }

    public void setList(RecommendFragmentBean list) {
        mList = list;
    }

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public RecommendFragmentAdapter(RecommendFragmentBean list, Context context) {
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
                viewHolder.getBinding().setDate(mList.getList().get(position));


                if(mList.getList().get(position).getGoodsScopeList() != null && mList.getList().get(position).getGoodsScopeList().size() >= 4){
                    viewHolder.getBinding().itemProgressContainer.setVisibility(View.VISIBLE);
                    viewHolder.getBinding().itemProgressbar1Text.setText(mList.getList().get(position).getGoodsScopeList().get(0).getName());
                    viewHolder.getBinding().itemProgressbar1.setTotalAndCurrentCount(10, Integer.valueOf(mList.getList().get(position).getGoodsScopeList().get(0).getScore()));

                    viewHolder.getBinding().itemProgressbar2Text.setText(mList.getList().get(position).getGoodsScopeList().get(1).getName());
                    viewHolder.getBinding().itemProgressbar2.setTotalAndCurrentCount(10,Integer.valueOf(mList.getList().get(position).getGoodsScopeList().get(1).getScore()));

                    viewHolder.getBinding().itemProgressbar3Text.setText(mList.getList().get(position).getGoodsScopeList().get(2).getName());
                    viewHolder.getBinding().itemProgressbar3.setTotalAndCurrentCount(10,Integer.valueOf(mList.getList().get(position).getGoodsScopeList().get(2).getScore()));

                    viewHolder.getBinding().itemProgressbar4Text.setText(mList.getList().get(position).getGoodsScopeList().get(3).getName());
                    viewHolder.getBinding().itemProgressbar4.setTotalAndCurrentCount(10,Integer.valueOf(mList.getList().get(position).getGoodsScopeList().get(3).getScore()));
                }else {
                    viewHolder.getBinding().itemProgressContainer.setVisibility(View.GONE);
                }

                viewHolder.itemView.setOnClickListener(view -> {
                    if (mOnItem != null) {
                        mOnItem.onItem(position);
                    }
                });

                if(!TextUtils.isEmpty(mList.getList().get(position).getRecommendReason())){
                    String str = "<font color='#E31436'>推荐理由：</font>" + mList.getList().get(position).getRecommendReason();
                    viewHolder.getBinding().itemReason.setText(Html.fromHtml(str));
                }else {
                    String str = "<font color='#E31436'>推荐理由：</font>" + "无";
                    viewHolder.getBinding().itemReason.setText(Html.fromHtml(str));
                }

                GlideApp.loderRoundImage(mContext,mList.getList().get(position).getRankGoodImg(),viewHolder.getBinding().itemImage,R.color.transparent,R.color.transparent);
//                ImageManager.displayRoundImage(mList.getList().get(position).getRankGoodImg(), viewHolder.getBinding().itemImage, R.color.transparent,  R.color.transparent, 10);

                if(mList.getList().get(position).getSourceStatus() == 1){
                    viewHolder.getBinding().itemGoodsFrom.setText("京东：");
                }else  if(mList.getList().get(position).getSourceStatus() == 2){
                    viewHolder.getBinding().itemGoodsFrom.setText("淘宝：");
                }else  if(mList.getList().get(position).getSourceStatus() == 3){
                    viewHolder.getBinding().itemGoodsFrom.setText("天猫：");
                }

                BigDecimal b = new BigDecimal(mList.getList().get(position).getGoodsGrade());
                //保留1位小数
                double result = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                viewHolder.getBinding().itemScore.setText(result + "");

                if(mList.getList().get(position).getGoodstypeList() != null && mList.getList().get(position).getGoodstypeList().size() != 0){
                    if(mList.getList().get(position).getGoodstypeList().size() >= 2){
                        viewHolder.getBinding().itemTag1.setVisibility(View.VISIBLE);
                        viewHolder.getBinding().itemTag2.setVisibility(View.VISIBLE);
                        viewHolder.getBinding().itemTag1.setText(mList.getList().get(position).getGoodstypeList().get(0).getName());
                        viewHolder.getBinding().itemTag2.setText(mList.getList().get(position).getGoodstypeList().get(1).getName());
                    }else {
                        viewHolder.getBinding().itemTag1.setVisibility(View.VISIBLE);
                        viewHolder.getBinding().itemTag2.setVisibility(View.GONE);
                        viewHolder.getBinding().itemTag1.setText(mList.getList().get(position).getGoodstypeList().get(0).getName());
                    }
                }else {
                    viewHolder.getBinding().itemTag1.setVisibility(View.GONE);
                    viewHolder.getBinding().itemTag2.setVisibility(View.GONE);
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
        } else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return mList.getList() == null || mList.getList().size() == 0 ? 0 : mList.getList().size() + 1;
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
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView recommend_image;

        public ContentViewHolder(View itemView) {
            super(itemView);
            recommend_image = itemView.findViewById(R.id.recommend_image);
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}
