package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ConvertUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FreeUserListBean;
import com.example.administrator.jipinshop.databinding.ItemFreeThreeBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe
 */
public class ShopUserAdapter extends RecyclerView.Adapter<ShopUserAdapter.ViewHolder> {

    private List<FreeUserListBean.DataBean> mList;
    private Context mContext;

    public ShopUserAdapter(List<FreeUserListBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemFreeThreeBinding  binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_free_three,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.binding.setDate(mList.get(i));
        viewHolder.binding.setPos( (i+1) + "");
        viewHolder.binding.setPosition((i+1));
        if(i == 0){
            viewHolder.binding.itemTopSort.setBackgroundResource(R.drawable.bg_sort1);
        }else if (i == 1){
            viewHolder.binding.itemTopSort.setBackgroundResource(R.drawable.bg_sort2);
        }else if (i == 2){
            viewHolder.binding.itemTopSort.setBackgroundResource(R.drawable.bg_sort3);
        }
        if (mList.get(i).getIsmyself() == 1){
            viewHolder.binding.itemImage.setBorderWidth(ConvertUtils.dp2px(2));
            viewHolder.binding.itemGoodNum.setTextColor(mContext.getResources().getColor(R.color.color_E31B3C));
        }else {
            viewHolder.binding.itemImage.setBorderWidth(0);
            viewHolder.binding.itemGoodNum.setTextColor(mContext.getResources().getColor(R.color.color_DE151515));
        }
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemFreeThreeBinding  binding;

        public ViewHolder(@NonNull ItemFreeThreeBinding  binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
