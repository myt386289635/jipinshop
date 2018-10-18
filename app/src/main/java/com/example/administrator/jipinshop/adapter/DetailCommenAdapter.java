package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.databinding.ItemCommonBinding;
import com.example.administrator.jipinshop.databinding.ItemShopCommenHeadBinding;
import com.example.administrator.jipinshop.databinding.ItemShopEvaluationBottonBinding;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.example.administrator.jipinshop.view.goodview.GoodView;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/21
 * @Describe
 */
public class DetailCommenAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mList;
    private OnItemReply mOnItemReply;

    public void setOnItemReply(OnItemReply onItemReply) {
        mOnItemReply = onItemReply;
    }

    public DetailCommenAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case 1:
                ItemShopCommenHeadBinding commenHeadBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_shop_commen_head, parent, false);
                holder = new HeadViewHolder(commenHeadBinding);
                break;
            case 2:
                ItemCommonBinding commonBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_common, parent, false);
                holder = new ContentViewHolder(commonBinding);
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
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;

                headViewHolder.getCommenHeadBinding().detailCommonTotle.setOnClickListener(v -> {
                    //跳转到评论列表
                    mContext.startActivity(new Intent(mContext, CommenListActivity.class));
                });
                headViewHolder.getCommenHeadBinding().executePendingBindings();
                break;
            case 2:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                contentViewHolder.getCommonBinding().itemReply.setOnClickListener(v -> {
                    if(mOnItemReply != null){
                        mOnItemReply.onItemReply(position - 1,contentViewHolder.getCommonBinding().itemReply);
                    }
                });
                if(position != 0){
                    contentViewHolder.getCommonBinding().recyclerView.setVisibility(View.VISIBLE);
                    contentViewHolder.mAdapter.setList(mList);
                    //二级评论的回复
                    contentViewHolder.mAdapter.setOnReplyLisenter(pos -> {
                        if(mOnItemReply != null){
                            mOnItemReply.onItemTwoReply(pos);
                        }
                    });
                    contentViewHolder.getCommonBinding().recyclerView.setAdapter(contentViewHolder.mAdapter);
                }
                ImageManager.displayCircleImage(MyApplication.imag,contentViewHolder.getCommonBinding().itemImage,0,0);
                contentViewHolder.getCommonBinding().executePendingBindings();
                break;
            case 3:
                BottonViewHolder bottonViewHolder = (BottonViewHolder) holder;
                bottonViewHolder.getBinding().detailCommonTotle.setOnClickListener(v -> {
                    //跳转到评论列表
                    mContext.startActivity(new Intent(mContext, CommenListActivity.class));
                });
                bottonViewHolder.getBinding().detailGood.setOnClickListener(v -> {
                    bottonViewHolder.mGoodView.setText("+1");
                    bottonViewHolder.mGoodView.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
                    bottonViewHolder.mGoodView.show(v);
                });
                bottonViewHolder.getBinding().executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : 3 + 1;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return 1;
//        } else
        if (position == getItemCount() - 1) {
            return 3;
        } else {
            return 2;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        private ItemShopCommenHeadBinding commenHeadBinding;

        public HeadViewHolder(ItemShopCommenHeadBinding commenHeadBinding) {
            super(commenHeadBinding.getRoot());
            this.commenHeadBinding = commenHeadBinding;
        }

        public ItemShopCommenHeadBinding getCommenHeadBinding() {
            return commenHeadBinding;
        }

        public void setCommenHeadBinding(ItemShopCommenHeadBinding commenHeadBinding) {
            this.commenHeadBinding = commenHeadBinding;
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        private ItemCommonBinding commonBinding;
        private ShoppingCommon2Adapter mAdapter;

        public ContentViewHolder(ItemCommonBinding commonBinding) {
            super(commonBinding.getRoot());
            this.commonBinding = commonBinding;

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    // 直接禁止垂直滑动
                    return false;
                }
            };
            commonBinding.recyclerView.setLayoutManager(layoutManager);
            mAdapter = new ShoppingCommon2Adapter(mContext);
        }

        public ItemCommonBinding getCommonBinding() {
            return commonBinding;
        }

        public void setCommonBinding(ItemCommonBinding commonBinding) {
            this.commonBinding = commonBinding;
        }
    }

    class BottonViewHolder extends RecyclerView.ViewHolder {

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

    public interface OnItemReply{
        void onItemReply(int pos,TextView textView);
        void onItemTwoReply(int pos);
    }

}
