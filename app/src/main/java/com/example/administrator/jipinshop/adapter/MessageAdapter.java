package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MessageBean;
import com.example.administrator.jipinshop.databinding.ItemMessageBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe 消息
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private Context mContext;
    private List<MessageBean.DataBean> mList;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public MessageAdapter(Context context, List<MessageBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMessageBinding view = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_message,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mBinding.setBean(mList.get(position));
        holder.mBinding.executePendingBindings();
        holder.itemView.setOnClickListener(v -> {
            mOnItem.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemMessageBinding mBinding;

        public ViewHolder(ItemMessageBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void onItemClick(int position);
    }
}
