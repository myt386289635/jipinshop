package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SreachBean;
import com.example.administrator.jipinshop.databinding.ItemSreachClassifyBinding;
import com.example.administrator.jipinshop.databinding.ItemSreachWaresBinding;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/8/15
 * @Describe
 */
public class SreachGoodsAdapter2 extends RecyclerView.Adapter {

    private static final int head = 1;
    private static final int content = 2;

    private Context mContext;
    private List<SreachBean.DataBean> mList;
    private List<SreachBean.GoodsCategoryListBean> goodsCategoryList;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SreachGoodsAdapter2(Context context, List<SreachBean.DataBean> list, List<SreachBean.GoodsCategoryListBean> goodsCategoryList) {
        mContext = context;
        mList = list;
        this.goodsCategoryList = goodsCategoryList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return head;
        }else {
            return content;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        RecyclerView.ViewHolder holder = null;
        switch (type) {
            case head:
                ItemSreachClassifyBinding classifyBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_sreach_classify, viewGroup, false);
                holder = new OneViewHolder(classifyBinding);
                break;
            case content:
                ItemSreachWaresBinding goodsBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_sreach_wares, viewGroup, false);
                holder = new TwoViewHolder(goodsBinding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case head:
                OneViewHolder oneViewHolder = (OneViewHolder) viewHolder;
                if (goodsCategoryList.size() != 0){
                    oneViewHolder.mBinding.recyclerView.setVisibility(View.VISIBLE);
                    oneViewHolder.mBinding.nodate.setVisibility(View.GONE);
                    oneViewHolder.list.clear();
                    oneViewHolder.list.addAll(goodsCategoryList);
                    oneViewHolder.mAdapter.notifyDataSetChanged();
                }else {
                    oneViewHolder.mBinding.recyclerView.setVisibility(View.GONE);
                    oneViewHolder.mBinding.nodate.setVisibility(View.VISIBLE);
                }
                if (mList.size() == 0){
                    oneViewHolder.mBinding.title.setVisibility(View.GONE);
                }else{
                    oneViewHolder.mBinding.title.setVisibility(View.VISIBLE);
                }
                break;
            case content:
                TwoViewHolder twoViewHolder = (TwoViewHolder) viewHolder;
                int pos = position - 1;
                twoViewHolder.mBinding.setData(mList.get(pos));
                twoViewHolder.initTags(twoViewHolder.mBinding.itemFlexLayout,pos);
                twoViewHolder.itemView.setOnClickListener(v -> {
                    if (mOnItem != null){
                        mOnItem.onItem(pos);
                    }
                });
                twoViewHolder.mBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class OneViewHolder extends RecyclerView.ViewHolder{

        private ItemSreachClassifyBinding mBinding;
        private List<SreachBean.GoodsCategoryListBean> list;
        private SreachPagerAdapter mAdapter;

        public OneViewHolder(@NonNull ItemSreachClassifyBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
            list = new ArrayList<>();
            mAdapter = new SreachPagerAdapter(list,mContext);
            mBinding.recyclerView.setAdapter(mAdapter);
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder{

        private ItemSreachWaresBinding mBinding;

        public TwoViewHolder(@NonNull ItemSreachWaresBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
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
