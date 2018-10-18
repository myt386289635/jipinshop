package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.databinding.RecommendItemBinding;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

public class RecommendFragmentAdapter extends RecyclerView.Adapter {

    private final int HEAD = 1;
    private final int CONTENT = 2;

    private RecommendFragmentBean mList;
    private Context mContext;
    private OnItem mOnItem;

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
                ImageManager.displayRoundImage(mList.getLogoimg(), contentViewHolder.recommend_image, 0, 0, 10);
                break;
            case CONTENT:
                HeadViewHolder viewHolder = (HeadViewHolder) holder;

                final int position = pos - 1;
                viewHolder.getBinding().setPosition(pos + "");
                viewHolder.getBinding().setDate(mList.getRankListData().get(position));

                String[] type1 = mList.getRankListData().get(position).getGradeWays().get(0).split("\\$");
                int code1 = Integer.parseInt(type1[1].replace("value_",""));
                viewHolder.getBinding().itemProgressbar1.setTotalAndCurrentCount(100,code1);

                String[] type2 = mList.getRankListData().get(position).getGradeWays().get(1).split("\\$");
                int code2 =Integer.parseInt(type2[1].replace("value_",""));
                viewHolder.getBinding().itemProgressbar2.setTotalAndCurrentCount(100,code2);

                String[] type3 = mList.getRankListData().get(position).getGradeWays().get(2).split("\\$");
                int code3 = Integer.parseInt(type3[1].replace("value_",""));
                viewHolder.getBinding().itemProgressbar3.setTotalAndCurrentCount(100,code3);

                String[] type4 = mList.getRankListData().get(position).getGradeWays().get(3).split("\\$");
                int code4 = Integer.parseInt(type4[1].replace("value_",""));
                viewHolder.getBinding().itemProgressbar4.setTotalAndCurrentCount(100,code4);

                viewHolder.itemView.setOnClickListener(view -> {
                    if (mOnItem != null) {
                        mOnItem.onItem(position);
                    }
                });

                String str = "<font color='#E31436'>推荐理由：</font>" + mList.getRankListData().get(position).getRecommendReason();
                viewHolder.getBinding().itemReason.setText(Html.fromHtml(str));
                ImageManager.displayRoundImage(mList.getRankListData().get(position).getGoodsImgPath(), viewHolder.getBinding().itemImage, R.color.transparent,  R.color.transparent, 10);

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
        return mList.getRankListData() == null || mList.getRankListData().size() == 0 ? 0 : mList.getRankListData().size() + 1;
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
