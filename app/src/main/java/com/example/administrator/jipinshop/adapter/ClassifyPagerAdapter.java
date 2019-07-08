package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.databinding.ItemClassifyPageBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/1
 * @Describe 小分类榜单————横向推荐榜
 */
public class ClassifyPagerAdapter extends RecyclerView.Adapter<ClassifyPagerAdapter.ViewHolder> {

    private Context mContext;
    private List<TopCategoryDetailBean.DataBean.RelatedItemCategotyListBean.GoodsListBean> mList;

    public ClassifyPagerAdapter(Context context, List<TopCategoryDetailBean.DataBean.RelatedItemCategotyListBean.GoodsListBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemClassifyPageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_classify_page,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.binding.itemContainer.getLayoutParams();
        if (position == 0){
            layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x30);
            layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x15);
        }else if (position == mList.size() - 1){
            layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x15);
            layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x30);
        } else {
            layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x15);
            layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x15);
        }
        viewHolder.binding.itemContainer.setLayoutParams(layoutParams);
        viewHolder.itemView.setOnClickListener(v ->{
            if (ClickUtil.isFastDoubleClick(800)) {
                return;
            }else{
                mContext.startActivity(new Intent(mContext, ShoppingDetailActivity.class)
                        .putExtra("goodsId",mList.get(position).getGoodsId())
                );
            }
        });

        viewHolder.binding.setPosition((position + 1) + "");
        viewHolder.binding.setDate(mList.get(position));
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemClassifyPageBinding binding;

        public ViewHolder(@NonNull ItemClassifyPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
