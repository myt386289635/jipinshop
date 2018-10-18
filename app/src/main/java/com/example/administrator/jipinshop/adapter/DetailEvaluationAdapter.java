package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;

import com.example.administrator.jipinshop.databinding.ItemShopEvaluationBottonBinding;
import com.example.administrator.jipinshop.view.goodview.GoodView;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/21
 * @Describe
 */
public class DetailEvaluationAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mList;

    public DetailEvaluationAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        switch (viewType){
            case 1:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_evaluation_head,parent,false);
                holder = new HeadViewHolder(view);
                break;
            case 2:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation,parent,false);
                holder = new ContentViewHolder(view1);
                break;
            case 3:
                ItemShopEvaluationBottonBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_shop_evaluation_botton, parent, false);
                holder = new BottonViewHolder(mBinding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case 1:

                break;
            case 2:

                break;
            case 3:
                BottonViewHolder bottonViewHolder = (BottonViewHolder) holder;

                bottonViewHolder.getBinding().detailCommonTotle.setVisibility(View.GONE);
                bottonViewHolder.getBinding().detailGood.setOnClickListener(v -> {
                    bottonViewHolder.mGoodView.setText("+1");
                    bottonViewHolder.mGoodView.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
                    bottonViewHolder.mGoodView.show(v);
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return  1;
        }else if(position == getItemCount() -1){
            return  3;
        }else {
            return  2;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        public ContentViewHolder(View itemView) {
            super(itemView);
        }
    }

    class BottonViewHolder extends RecyclerView.ViewHolder{

        private ItemShopEvaluationBottonBinding mBinding;
        private GoodView mGoodView;

        public BottonViewHolder(ItemShopEvaluationBottonBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            mGoodView = new GoodView(mContext);
        }

        public ItemShopEvaluationBottonBinding getBinding() {
            return mBinding;
        }

        public void setBinding(ItemShopEvaluationBottonBinding binding) {
            mBinding = binding;
        }
    }
}
