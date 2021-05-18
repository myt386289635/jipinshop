package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.CommenBean;
import com.example.administrator.jipinshop.databinding.ItemCommenBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/5/17
 * @Describe 淘宝详情——评论列表
 */
public class ShoppingCommentAdapter extends RecyclerView.Adapter<ShoppingCommentAdapter.ViewHolder> {

    private List<CommenBean.DataBean> mCommentList;
    private Context mContext;

    public ShoppingCommentAdapter(List<CommenBean.DataBean> commentList, Context context) {
        mCommentList = commentList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemCommenBinding view = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_commen, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setBean(mCommentList.get(position));
        viewHolder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemCommenBinding mBinding;

         public ViewHolder(@NonNull ItemCommenBinding itemView) {
             super(itemView.getRoot());
             mBinding = itemView;
         }
     }
}
