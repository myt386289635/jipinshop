package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MessageAllBean;
import com.example.administrator.jipinshop.databinding.ItemMessageActionBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/4/1
 * @Describe
 */
public class MessageActionAdapter extends RecyclerView.Adapter<MessageActionAdapter.ViewHolder> {

    private List<MessageAllBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public MessageActionAdapter(List<MessageAllBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMessageActionBinding view = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_message_action, viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setBean(mList.get(position));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.itemView.setOnClickListener(v -> {
            mOnItem.onItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemMessageActionBinding mBinding;

        public ViewHolder(@NonNull ItemMessageActionBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void  onItem(int position);
    }
}
