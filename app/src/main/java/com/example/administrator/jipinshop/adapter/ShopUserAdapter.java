package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
public class ShopUserAdapter extends RecyclerView.Adapter {

    private static final int HEAD = 0;
    private static final int CONTENT = 1;

    private List<FreeUserListBean.DataBean> mList;
    private Context mContext;
    private String fee;

    public ShopUserAdapter(List<FreeUserListBean.DataBean> list, Context context , String fee ) {
        mList = list;
        mContext = context;
        this.fee =  fee;
    }

    @Override
    public int getItemViewType(int position) {
        return CONTENT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        switch (i){
            case HEAD:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_free_four,viewGroup,false);
                holder = new HeadViewHolder(view);
                break;
            case CONTENT:
                ItemFreeThreeBinding  binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_free_three,viewGroup,false);
                holder = new ViewHolder(binding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        int type = getItemViewType(i);
        switch (type){
            case HEAD:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                break;
            case CONTENT:
                ViewHolder viewHolder = (ViewHolder) holder;
                int position = i;
                viewHolder.binding.setDate(mList.get(position));
                viewHolder.binding.executePendingBindings();
                viewHolder.binding.itemImage.setBorderWidth(0);
                viewHolder.binding.itemGoodNum.setText("到手¥" + mList.get(position).getBuyPrice());
                break;
        }

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

    class HeadViewHolder extends  RecyclerView.ViewHolder{

        private LinearLayout item_headContainer;

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            item_headContainer = itemView.findViewById(R.id.item_headContainer);
        }
    }
}
