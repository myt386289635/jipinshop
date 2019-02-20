package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.databinding.ItemSreachgoodsBinding;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public class SreachGoodsAdapter extends RecyclerView.Adapter<SreachGoodsAdapter.ViewHolder> {

    private List<SreachResultGoodsBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SreachGoodsAdapter(List<SreachResultGoodsBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSreachgoodsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_sreachgoods, viewGroup, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final int pos = position + 1;

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

        String str = "<font color='#151515' >推荐理由：</font>" + mList.get(position).getRecommendReason();
        viewHolder.getBinding().itemReason.setText(Html.fromHtml(str));

        // 立刻刷新界面
        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemSreachgoodsBinding binding;

        public ViewHolder(ItemSreachgoodsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemSreachgoodsBinding getBinding() {
            return binding;
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

    public interface OnItem {
        void onItem(int pos);
    }
}
