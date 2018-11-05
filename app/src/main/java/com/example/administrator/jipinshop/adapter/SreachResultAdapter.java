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

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SreachResultBean;
import com.example.administrator.jipinshop.databinding.HomeItemBinding;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public class SreachResultAdapter extends RecyclerView.Adapter<SreachResultAdapter.ViewHolder> {

    private List<SreachResultBean.ListBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SreachResultAdapter(List<SreachResultBean.ListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.home_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.getBinding().setPosition(position + 1 + "");
        viewHolder.getBinding().setDate(mList.get(position));

        viewHolder.itemView.setOnClickListener(view -> {
            if (mOnItem != null) {
                mOnItem.onItem(position);
            }
        });

        if(mList.get(position).getGoodsScopeList() != null && mList.get(position).getGoodsScopeList().size() != 0){
            viewHolder.getBinding().itemProgressContainer.setVisibility(View.VISIBLE);
            viewHolder.getBinding().itemProgressbar1Text.setText(mList.get(position).getGoodsScopeList().get(0).getName());
            viewHolder.getBinding().itemProgressbar1.setTotalAndCurrentCount(10, Integer.valueOf(mList.get(position).getGoodsScopeList().get(0).getScore()));

            viewHolder.getBinding().itemProgressbar2Text.setText(mList.get(position).getGoodsScopeList().get(1).getName());
            viewHolder.getBinding().itemProgressbar2.setTotalAndCurrentCount(10,Integer.valueOf(mList.get(position).getGoodsScopeList().get(1).getScore()));

            viewHolder.getBinding().itemProgressbar3Text.setText(mList.get(position).getGoodsScopeList().get(2).getName());
            viewHolder.getBinding().itemProgressbar3.setTotalAndCurrentCount(10,Integer.valueOf(mList.get(position).getGoodsScopeList().get(2).getScore()));

            viewHolder.getBinding().itemProgressbar4Text.setText(mList.get(position).getGoodsScopeList().get(3).getName());
            viewHolder.getBinding().itemProgressbar4.setTotalAndCurrentCount(10,Integer.valueOf(mList.get(position).getGoodsScopeList().get(3).getScore()));
        }else {
            viewHolder.getBinding().itemProgressContainer.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(mList.get(position).getRecommendReason())){
            String str = "<font color='#E31436'>推荐理由：</font>" + mList.get(position).getRecommendReason();
            viewHolder.getBinding().itemReason.setText(Html.fromHtml(str));
        }else {
            String str = "<font color='#E31436'>推荐理由：</font>" + "无";
            viewHolder.getBinding().itemReason.setText(Html.fromHtml(str));
        }

        ImageManager.displayRoundImage(mList.get(position).getRankGoodImg(), viewHolder.getBinding().itemImage, R.color.transparent,  R.color.transparent, 10);

        if(mList.get(position).getSourceStatus() == 1){
            viewHolder.getBinding().itemGoodsFrom.setText("京东：");
        }else  if(mList.get(position).getSourceStatus() == 2){
            viewHolder.getBinding().itemGoodsFrom.setText("淘宝：");
        }else  if(mList.get(position).getSourceStatus() == 3){
            viewHolder.getBinding().itemGoodsFrom.setText("天猫：");
        }

        BigDecimal b = new BigDecimal(mList.get(position).getGoodsGrade());
        //保留1位小数
        double result = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        viewHolder.getBinding().itemScore.setText(result + "");

        if(mList.get(position).getGoodstypeList() != null && mList.get(position).getGoodstypeList().size() != 0){
            if(mList.get(position).getGoodstypeList().size() >= 2){
                viewHolder.getBinding().itemTag1.setVisibility(View.VISIBLE);
                viewHolder.getBinding().itemTag2.setVisibility(View.VISIBLE);
                viewHolder.getBinding().itemTag1.setText(mList.get(position).getGoodstypeList().get(0).getName());
                viewHolder.getBinding().itemTag2.setText(mList.get(position).getGoodstypeList().get(1).getName());
            }else {
                viewHolder.getBinding().itemTag1.setVisibility(View.VISIBLE);
                viewHolder.getBinding().itemTag2.setVisibility(View.GONE);
                viewHolder.getBinding().itemTag1.setText(mList.get(position).getGoodstypeList().get(0).getName());
            }
        }else {
            viewHolder.getBinding().itemTag1.setVisibility(View.GONE);
            viewHolder.getBinding().itemTag2.setVisibility(View.GONE);
        }

        viewHolder.getBinding().itemPriceOld.setTv(true);
        viewHolder.getBinding().itemPriceOld.setColor(R.color.color_ACACAC);

        // 立刻刷新界面
        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if(mList != null && mList.size() == 0){
            return 0;
        }else if(mList != null && mList.size() >= 10){
            return 10;
        }else {
            return mList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private  HomeItemBinding mBinding;

        public ViewHolder(HomeItemBinding binding) {
            super(binding.getRoot());
           this.mBinding = binding;
        }

        public HomeItemBinding getBinding() {
            return mBinding;
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}
