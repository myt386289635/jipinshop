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
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.databinding.HomeCommenItemBinding;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public class HomeCommenAdapter extends RecyclerView.Adapter{

    private List<HomeCommenBean.DataBean> mCommenBeans;
    private Context mContext;
    private OnItem mOnItem;

    private final int FOOT = 3;
    private final int CONTENT = 2;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public HomeCommenAdapter(List<HomeCommenBean.DataBean> commenBeans, Context context) {
        mCommenBeans = commenBeans;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
       if (position == getItemCount() - 1){
            return FOOT;
        } else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return mCommenBeans.size() == 0 ? 0 : mCommenBeans.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        switch (i) {
            case CONTENT:
                HomeCommenItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.home_commen_item,viewGroup, false);
                holder = new ViewHolder(binding);
                break;
            case FOOT:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, viewGroup, false);
                holder = new FootViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        int type = getItemViewType(pos);
        switch (type) {
            case CONTENT:
                ViewHolder viewHolder = (ViewHolder) holder;

                final int position = pos;
                viewHolder.getBinding().setPosition((pos + 1) + "");
                viewHolder.getBinding().setDate(mCommenBeans.get(position));

                viewHolder.itemView.setOnClickListener(view -> {
                    if (mOnItem != null) {
                        mOnItem.onItemclick(position);
                    }
                });

                GlideApp.loderRoundImage(mContext,mCommenBeans.get(position).getImg(),viewHolder.getBinding().itemImage,R.color.transparent,R.color.transparent);
                viewHolder.initTags(viewHolder.getBinding().itemFlex,position);

                if(mCommenBeans.get(position).getSource() == 1){
                    viewHolder.getBinding().itemGoodsFrom.setText("京东：");
                }else  if(mCommenBeans.get(position).getSource() == 2){
                    viewHolder.getBinding().itemGoodsFrom.setText("淘宝：");
                }else  if(mCommenBeans.get(position).getSource() == 3){
                    viewHolder.getBinding().itemGoodsFrom.setText("天猫：");
                }
                viewHolder.getBinding().itemPriceOld.setTv(true);
                viewHolder.getBinding().itemPriceOld.setColor(R.color.color_ACACAC);

                String str = "<font color='#151515' >推荐理由：</font>" + mCommenBeans.get(position).getRecommendReason();
                viewHolder.getBinding().itemReason.setText(Html.fromHtml(str));

                // 立刻刷新界面
                viewHolder.getBinding().executePendingBindings();
                break;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private HomeCommenItemBinding mBinding;

        public ViewHolder(HomeCommenItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public HomeCommenItemBinding getBinding() {
            return mBinding;
        }

        public void initTags(FlexboxLayout flexboxLayout, int pos) {
            flexboxLayout.removeAllViews();
            for (int i = 0; i < mCommenBeans.get(pos).getGoodsTagsList().size(); i++) {
                View itemTypeView = LayoutInflater.from(mContext).inflate(R.layout.item_goodstag, null);
                TextView textView = itemTypeView.findViewById(R.id.item_tag);
                textView.setText(mCommenBeans.get(pos).getGoodsTagsList().get(i).getName());
                flexboxLayout.addView(itemTypeView);
            }
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItem{
        void onItemclick(int pos);
    }
}
