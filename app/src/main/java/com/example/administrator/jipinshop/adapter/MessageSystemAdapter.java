package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MessageAllBean;
import com.example.administrator.jipinshop.databinding.ItemMessageSystemBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/3/31
 * @Describe
 */
public class MessageSystemAdapter extends RecyclerView.Adapter<MessageSystemAdapter.ViewHolder> {

    private List<MessageAllBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public MessageSystemAdapter(List<MessageAllBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMessageSystemBinding view = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_message_system, viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mBinding.setBean(mList.get(i));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.mBinding.itemGo.setOnClickListener(v -> {
            mOnItem.onItem(i);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private ItemMessageSystemBinding mBinding;

        public ViewHolder(@NonNull ItemMessageSystemBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void  onItem(int position);
    }
}
