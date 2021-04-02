package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MessageAllBean;
import com.example.administrator.jipinshop.databinding.ItemMessageDailyBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/4/1
 * @Describe
 */
public class MessageDailyAdapter extends RecyclerView.Adapter<MessageDailyAdapter.ViewHolder>{

    private List<MessageAllBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public MessageDailyAdapter(List<MessageAllBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMessageDailyBinding view = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_message_daily, viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setBean(mList.get(position));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.mBinding.itemContainer.setOnClickListener(v -> {
            mOnItem.onItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemMessageDailyBinding mBinding;

        public ViewHolder(@NonNull ItemMessageDailyBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void  onItem(int position);
    }
}
