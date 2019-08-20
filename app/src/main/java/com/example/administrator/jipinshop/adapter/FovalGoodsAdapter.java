package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.databinding.ItemGoodsBinding;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public class FovalGoodsAdapter extends RecyclerView.Adapter<FovalGoodsAdapter.ViewHolder> {

    private List<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public FovalGoodsAdapter(List<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemGoodsBinding goodsBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_goods,viewGroup,false);
        ViewHolder holder = new ViewHolder(goodsBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener(v ->{
            if (mOnItem != null){
                mOnItem.onItem(position);
            }
        });
        viewHolder.initTags(viewHolder.binding.itemFlexLayout,position);
        viewHolder.binding.setDate(mList.get(position));
        viewHolder.binding.setPosition(position + 1);
        viewHolder.binding.itemSort.setVisibility(View.GONE);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemGoodsBinding binding;

        public ViewHolder(ItemGoodsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemGoodsBinding getBinding() {
            return binding;
        }

        public void initTags(FlexboxLayout flexboxLayout, int pos) {
            flexboxLayout.removeAllViews();
            if (mList.get(pos).getGoodsTagsList() != null){
                for (int i = 0; i < mList.get(pos).getGoodsTagsList().size(); i++) {
                    View itemTypeView = LayoutInflater.from(mContext).inflate(R.layout.item_goodstag, null);
                    TextView textView = itemTypeView.findViewById(R.id.item_tag);
                    textView.setText(mList.get(pos).getGoodsTagsList().get(i).getName());
                    flexboxLayout.addView(itemTypeView);
                }
            }
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}
